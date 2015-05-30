/**
 * 
 */
package iiw.wrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sri
 *
 */
public interface CacheWrapper {

	/* Wrapper functions */
	/* Create a new cache */	
	public boolean createNewCache(String createCache,List<String> columns) throws Exception;
	
	/* Reading the data */
	public List<String> readAll(Map<String,List<String>> selectionData,Map<String,String> commonVariableList,List<String> finalVariableList);
	
	/* Purge all the caches that are listed*/
	public boolean purgeCache(List<String> tablesToPurge);
	
	/* Insert all the required informaton into the cache */
	public Boolean insertIntoCache(HashMap<String,String> dataToBeInserted,	String tableName);

}
