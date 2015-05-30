/**
 * 
 */
package iiw.db;

import iiw.manager.AppManagerImpl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * @author sri
 *
 */
public class DBManagerImplementation implements DBManager{

	final static Logger logger = Logger.getLogger(DBManager.class);

	/**
	 * 
	 * @param createCache
	 * @param columns
	 * @return
	 * @throws Exception
	 */
	public boolean createNewCache(String createQuery) throws Exception {
		Statement statement = null;
		try{
			statement = AppManagerImpl.getApplicationManager().getApplicationData().getConnection().createStatement();
			logger.debug("Query run is : "+createQuery+"");
			statement.execute(createQuery);
		}catch(Exception e){
			logger.info("Failed to create the collection.");
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Read all the data from the tables. Join the data if needed.
	 * @param collectionName - the collection that needs to be queried
	 * @return A hashmap of key-value pairs
	 */
	public ResultSet readAll(String selectTableSQL){
		Statement statement=null;
		ResultSet rs=null;
		try {
			logger.debug("Query run : "+selectTableSQL);
			statement = AppManagerImpl.getApplicationManager().getApplicationData().getConnection().createStatement();
			rs = statement.executeQuery(selectTableSQL);
		} catch (SQLException e) {
			logger.info("Failed to retrieve the data.");
			logger.error(e.getMessage());
		}
		return rs;
	}

	/**
	 * This function would insert the data into the given collection
	 * @param dataToBeInserted - A Map that has the "column name" as the "key" and the corresponding value 
	 * @param collectionName - Name of the collection in which the data has to be inserted
	 * @returns Number of data successfully inserted into the cache
	 */
	public Boolean insertIntoCache(String queryToInsert) {
		Statement statement = null;
		try{
			statement = AppManagerImpl.getApplicationManager().getApplicationData().getConnection().createStatement();
			statement.executeUpdate(queryToInsert);
		}catch(Exception e){
			logger.warn("Failed to insert.The record already exists in the file.");
			return false;
		}
		return true;
	}

	

	/**
	 * Purges the data.
	 * @param collectionName
	 * @return boolean - true - Successfully purged 
	 * 					 false - not purged
	 */
	public boolean purgeCache(String deleteTableQuery){
		Statement statement = null;
		try {
			statement = AppManagerImpl.getApplicationManager().getApplicationData().getConnection().createStatement();
			statement.executeUpdate(deleteTableQuery);
		} catch (Exception e) {
			logger.error("Collection has not been purged.");
			logger.error(e.getMessage());
			return false;
		}
		logger.info("Collection has been successfully purged.");
		return true;
	}

	/**
	 * This function removes the data from the cache
	 * @param dataToBeDeleted
	 * @param collectionName
	 * @return
	 */
	public boolean removeFromCache(String deleteTupleQuery){
		Statement statement = null;
		try {
			statement = AppManagerImpl.getApplicationManager().getApplicationData().getConnection().createStatement();
			statement.executeUpdate(deleteTupleQuery);
		}catch (Exception e) {
			logger.error("Problem in deletion.");
			return false;
		}
		logger.info("Deleted Successfully.");
		return true;
	}

}