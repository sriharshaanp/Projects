/**
 * 
 */
package iiw.sparql;

import iiw.manager.AppManagerImpl;
import iiw.utilities.CacheConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;

import com.google.gson.Gson;

/**
 * @author Hari
 *
 */
public class SparQLManagerImpl implements SparQLManager{

	public static int globalQueryCount;

	final static Logger logger = Logger.getLogger(SparQLManager.class);
	
	public SparQLManagerImpl(int globalQueryCount){
		this.globalQueryCount = globalQueryCount;
	}

	/**
	 * 
	 */
	public String convertQuery(String sparqlQuery) {
		List < String > tokens = Arrays.asList(sparqlQuery.split("\\s+"));
		List < String > tokensSmall = Arrays.asList(sparqlQuery.toLowerCase()
				.split("\\s+"));
		globalQueryCount+=1;
		String transformedQuery = "VQ" + globalQueryCount + "(";
		List < String > RHSSources = new ArrayList < String > ();
		String formatError = "Incorrect Format !";
		if (tokensSmall.indexOf("select") > -1) {
			int curr = tokensSmall.indexOf("select") + 1;
			while (!tokensSmall.get(curr).equals("where")) {
				if (tokens.get(curr).indexOf("?") == -1) {
					// System.out.println(transformedQuery);
					return formatError;
				}
				transformedQuery += tokens.get(curr).substring(1) + ",";
				curr++;
			}
			transformedQuery = transformedQuery.substring(0,
					transformedQuery.length() - 1);
			transformedQuery += "):-";
			if (tokensSmall.indexOf("{") == -1) {
				// System.out.println(transformedQuery);
				return formatError;
			}
			curr = tokensSmall.indexOf("{");
			if (curr >= tokens.size()) {
				// System.out.println(transformedQuery);
				return formatError;
			}
			while (!tokens.get(curr).equals("}")) {
				curr++;
				if (curr >= tokens.size()) {
					// System.out.println(curr);
					return formatError;
				}
				if (tokens.get(curr).indexOf("?") == -1) {
					// System.out.println(curr);
					return formatError;
				}
				String var1 = tokens.get(curr).substring(1);
				do {
					if (!(tokens.get(curr).substring(1).equals(var1) || tokens.get(curr).equals(";"))) {
						// System.out.println(curr);
						return formatError;
					}
					curr++;
					if (curr >= tokens.size())
						return formatError;
					String functionName = tokens.get(curr++);
					if (curr >= tokens.size())
						return formatError;
					if (tokens.get(curr).indexOf("?") == -1) {
						return formatError;
					}
					String var2 = tokens.get(curr++).substring(1);
					RHSSources.add(functionName + "(" + var1 + "," + var2 + ")");
				}
				while (!(tokens.get(curr).equals(".") || tokens.get(curr)
						.equals("}")));
			}
			if (RHSSources.size() != 0) {
				if (RHSSources.get(0).indexOf("a(") > -1 || RHSSources.get(0).indexOf("rdf:type(") > -1) {
					String fname = RHSSources.get(0).substring(
							RHSSources.get(0).indexOf(',') + 1,
							RHSSources.get(0).indexOf(')'));
					String varName = RHSSources.get(0).substring(
							RHSSources.get(0).indexOf('(') + 1,
							RHSSources.get(0).indexOf(','));
					transformedQuery += fname + "(" + varName + ")";
				} else transformedQuery += RHSSources.get(0);
			}
			for (int i = 1; i < RHSSources.size(); i++) {
				// System.out.println(RHSSources.get(i));
				if (RHSSources.get(i).indexOf("a(") > -1 || RHSSources.get(i).indexOf("rdf:type(") > -1) {
					String fname = RHSSources.get(i).substring(
							RHSSources.get(i).indexOf(',') + 1,
							RHSSources.get(i).indexOf(')'));
					String varName = RHSSources.get(i).substring(
							RHSSources.get(i).indexOf('(') + 1,
							RHSSources.get(i).indexOf(','));
					transformedQuery += "," + fname + "(" + varName + ")";
				} else transformedQuery += "," + RHSSources.get(i);
			}
			return transformedQuery;
		} else {
			return "Incorrect Format !";

		}

	}

	/**
	 * 
	 */
	public List<String> performSparqlQuery(String convertedQuery,String userQuery) throws RepositoryException{
		Gson gson = new Gson();
		//endpointUrl should be initially asked from the user and saved as a global parameter or user-settings-parameter
		String endpointUrl = "http://dbpedia.org/sparql"; 
		Repository repo = new SPARQLRepository(endpointUrl);
		repo.initialize();
		RepositoryConnection con = repo.getConnection();
		List<String> finalResult = new ArrayList<String>();
		try {
			String queryString = convertedQuery;
			/* Getting the tableName */ 
			String tableName = queryString.split(CacheConstants.QUERY_SEPERATOR)[0];
			Matcher m2 = Pattern.compile(CacheConstants.QUERY_REGEX).matcher(tableName);
			while(m2.find()){
				tableName = m2.group(1);
			}
			TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, userQuery);
			TupleQueryResult result = tupleQuery.evaluate();
			List<String> columnNames = result.getBindingNames();
			AppManagerImpl.getApplicationManager().getWrapper().createNewCache(tableName, columnNames);
			
			try {
				while (result.hasNext()) { // iterate over the result
					//insert code to create a new table in this new query for the given columns
					HashMap<String,String> columnInsertion = new HashMap<String,String>();
					BindingSet bindingSet = result.next();
					for(String s : columnNames){
						Value val = bindingSet.getValue(s);
						//System.out.println(" | " + val.stringValue() +" | ");	
						//save this value in db table
						columnInsertion.put(s, val.stringValue());
					}
					finalResult.add(gson.toJson(columnInsertion));
					//AppManagerImpl.getApplicationManager().getWrapper().insertIntoCache(columnInsertion, tableName);
				}
			} finally {
				result.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return finalResult;
	}
}
