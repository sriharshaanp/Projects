/**
 * 
 */
package iiw.db;

import iiw.pojo.ApplicationData;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sri
 *
 */
public interface DBManager {
	
	/* Crud functions */
	/* Creation */
	public boolean createNewCache(String createQuery) throws Exception;
	
	/* Read */
	public ResultSet readAll(String selectTableSQL);
	
	/* Insertion */
	public Boolean insertIntoCache(String queryToInsert);
	
	/* Deletion */
	public boolean purgeCache(String deleteTableQuery);
	
}
 