/**
 * 
 */
package iiw.manager;

import gqr.CompRewriting;
import gqr.GQR;
import gqr.IndexViews;
import gqr.NonAnswerableQueryException;
import iiw.db.DBManager;
import iiw.pojo.ApplicationData;
import iiw.sparql.SparQLManager;
import iiw.utilities.ApplicationConstants;
import iiw.utilities.Utilities;
import iiw.wrapper.CacheWrapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openrdf.repository.RepositoryException;

import com.google.gson.Gson;

/**
 * @author sri
 *
 */
public class AppManagerImpl implements AppManager{

	final static Logger logger = Logger.getLogger(AppManager.class);

	private static AppManager appManager = new AppManagerImpl();

	private AppManagerImpl(){
		Boolean success=initialize();
		if(success){
			logger.info("Successfully initialized the application");
		}else{
			logger.info("Unsuccessful during initializing the application");
		}
	}

	/**
	 * Singleton Factory Pattern
	 */
	public static AppManager getApplicationManager(){
		return appManager;
	}

	public DBManager getDbManager() {
		return dbManager;
	}

	public CacheWrapper getWrapper(){
		return cacheWrapper;
	}
	
	public SparQLManager getSparQLManager(){
		return sparQLManager;
	}
	
	/**
	 * 
	 * @return ApplicationData
	 */
	public ApplicationData getApplicationData(){
		return applicationData;
	}
	/**
	 * Initialize the data 
	 * @return Initialize the data
	 */
	public boolean initialize(){
		/* Reading the config file */
		InputStream inputStream;
		Properties prop = new Properties();
		Connection connection = null;
		try{
			inputStream = getClass().getClassLoader().getResourceAsStream(ApplicationConstants.CONFIG_FILE);
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + ApplicationConstants.CONFIG_FILE + "' not found in the classpath");
			}
			Class.forName(prop.getProperty(ApplicationConstants.DRIVER_NAME));
			String connectionUrl = prop.getProperty(ApplicationConstants.DB_STRING)+prop.getProperty(ApplicationConstants.LOCATION)+":"+prop.getProperty(ApplicationConstants.PORT)+"/"+prop.getProperty(ApplicationConstants.DB_NAME);
			String userName = prop.getProperty(ApplicationConstants.USER_NAME);
			String password = prop.getProperty(ApplicationConstants.PASSWORD);
			connection = DriverManager.getConnection(connectionUrl,userName,password);
			applicationData.setConnection(connection);
			applicationData.setMetaDataTable(prop.getProperty(ApplicationConstants.META_TABLE));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public List<String> getData(String userQuery) {
		//Initialize the global query count from the table count for queries from database
		//for testing initialized to 1
		SparQLManager sm = AppManagerImpl.getApplicationManager().getSparQLManager();

		//read user query
		//String userQuery = "SELECT ?x ?y WHERE { ?x rdf:type ?y }";//read.nextLine();

		//to convert the SPARQL query to schema mappings form
		//String convertedQuery = sm.convertQuery(userQuery);

		//the query is saved in a file to further use GQR for containment check
		//because the function GQR can only take query input from a file
		/*PrintWriter write = null;
		try {
			write = new PrintWriter("tempQuery1.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		write.println(convertedQuery);
		write.close();*/

/*		File data = new File(System.getProperty("user.dir"));*/

		//Get all saved query-definitions from the DB and write it to a file and 
		//get filename in viewFileName (and number of views) in following variables
/*		String viewFileName = null;
		int numberOfViews = 20;
		IndexViews viewsIndex = new IndexViews(new File(data+"/files/views_for_q_0.txt"), numberOfViews);
		GQR g = new GQR(new File(data+"/files/tempQuery1.txt"),viewsIndex);*/

		//get rewritten results
		Boolean flag = true;
/*		List<CompRewriting> res = new ArrayList<CompRewriting>();
		try {
			res = g.reformulate(g.getQuery());

		} catch (NonAnswerableQueryException e) {
			System.out.println(" No rewritings ");
			flag = false;
		}*/
		List<String> returnedResult = null;
		flag = false;
		String convertedQuery = "Hi";
		if(flag) // indicates that query is contained
		{
			//use rewritings stores in res to get the unioned data from DB
			//add a new function call to do the above, and pass rewritings stored in 
			/*for(CompRewriting r:res){
				Map<String,Map<Integer,String>> refactoredQuery =Utilities.refactoringQueries(res.toString().split(":-")[0],res.toString().split(":-")[1]);
				Map<String,String> commonVariables = Utilities.findAllCommonVariables(refactoredQuery);
				List<String> finalVariableList = Utilities.getFinalVariableList(res.toString().split(":-")[0]);
				HashMap<String,List<String>> mapOfVals = new HashMap<String,List<String>>();
				for(String tableName : refactoredQuery.keySet()){
					List<String> listOfVariables = Utilities.getUncommonVariables(tableName,refactoredQuery);
					mapOfVals.put(tableName, listOfVariables);
					try {
						returnedResult = AppManagerImpl.getApplicationManager().getWrapper().readAll(mapOfVals,commonVariables,finalVariableList);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				write.close();
				break;
			}*/

		}
		else // query not contained therefore perform query from SPARQL endpoint and save results
		{
			try {
				returnedResult = sm.performSparqlQuery(convertedQuery,userQuery);
			} catch (RepositoryException e) {
				e.printStackTrace();
			}
		}
		/* The generated query is now appended to the file */
		/*BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(data+"/files/views_for_q_0.txt",true));
			bw.append(convertedQuery+"\n");
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return returnedResult;
	}

}
