/**
 * 
 */
package iiw.module2;

import iiw.pojo.ApplicationData;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;

/**
 * @author sri
 *
 */
public interface CacheManager {
	/* Singleton Initialization of the ApplicationData */
	ApplicationData applicationData = new ApplicationData();
	/* Initialization */
	boolean initialize() throws Exception;
	
	/* Cache creation */
	boolean createNewCache(String collectionName) throws Exception;
	
	/* Cache Data insertion */
	Integer insertIntoCache(BasicDBObject dataToBeInserted,
			String collectionName);

	/* Purge the entire collection */
	public boolean purgeCache(String collectionName);
	
	/* Remove a particular record from the cache based on the conditions*/
	public boolean removeFromCache(BasicDBObject dataToBeDeleted,String collectionName);
	
	/* Remove the particular record from the cache based on the ID number */
	public boolean removeByIDFromCache(String dataToBeDeleted,String collectionName);
	
	/*Find a specific set of values in the collection of your choice */
	public ArrayList<BasicDBObject> find(String collectionName,BasicDBObject conditions);
	
	/* Reads all the data in the collection. */
	public ArrayList<BasicDBObject> read(String collectionName,Integer numberOfDocuments);
	
	/* Reads all the data in the collection. */
	public ArrayList<BasicDBObject> readAll(String collectionName);
}
