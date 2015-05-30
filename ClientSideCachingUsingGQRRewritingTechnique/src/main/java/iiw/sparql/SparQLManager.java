/**
 * 
 */
package iiw.sparql;

import java.util.HashMap;
import java.util.List;

import org.openrdf.repository.RepositoryException;

/**
 * @author sri
 *
 */
public interface SparQLManager {
	
	SparQLData sparQLData = new SparQLData();
	
	/* Query the sparql endpoint */
	public List<String> performSparqlQuery(String convertedQuery,String userQuery) throws RepositoryException;
	
	/* Convert the query into the required format */
	public String convertQuery(String sparqlQuery);
}
