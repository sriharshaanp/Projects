/**
 * 
 *//*
package iiw.module2;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

*//**
 * @author sri
 *
 *//*
public class CacheManagerImplementation implements CacheManager{
	
	final static Logger logger = Logger.getLogger(CacheManager.class);
	
	*//**
	 * Initialize the data 
	 * @return Initialize the data
	 *//*
	public boolean initialize() throws Exception {
		 Reading the config file 
		InputStream inputStream;
		Properties prop = new Properties();
		String propFileName = "config.properties";
		try{
			inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			applicationData.setCacheLocation(prop.getProperty("location"));
			applicationData.setCachePort(Integer.parseInt(prop.getProperty("port")));
			applicationData.setDbName(prop.getProperty("dbName"));
			applicationData.setMetaDataCollection(prop.getProperty("metaData"));
			if(Boolean.parseBoolean(prop.getProperty("batchMode"))){
				applicationData.setBatchInsertNumber(Integer.parseInt(prop.getProperty("batchInsertValue"))); 
			}else{
				applicationData.setBatchInsertNumber(1);
			}
			applicationData.setMongoClient(new MongoClient(new ServerAddress(applicationData.getCacheLocation(),applicationData.getCachePort())));
			applicationData.setDataBase(applicationData.getMongoClient().getDB(applicationData.getDbName()));
			applicationData.setCollection(applicationData.getDataBase().getCollection(applicationData.getMetaDataCollection()));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return true;
	}

	*//**
	 * This function would create a new Cache in the given DB Name 
	 * @return true  - created successfully, false - not created successfully 
	 * 
	 *//*
	public boolean createNewCache(String collectionName) throws Exception {
		try{
			if (applicationData.getDataBase().collectionExists(collectionName)) {
				applicationData.getDataBase().getCollection(collectionName);
			} else {
				DBObject options = BasicDBObjectBuilder.start().get();
				applicationData.getDataBase().createCollection(collectionName, options);
			}
			logger.info("The collection "+collectionName+" is created.");
		}catch(Exception e){
			logger.info("Failed to create the collection.");
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	*//**
	 * Reads all the data in the collection.
	 * @param collectionName - the collection that needs to be queried
	 * @return A hashmap of key-value pairs
	 *//*
	public ArrayList<BasicDBObject> readAll(String collectionName){
		DBCursor cursor = applicationData.getDataBase().getCollection(collectionName).find();
		ArrayList<BasicDBObject> finalList = new ArrayList<BasicDBObject>();
		while(cursor.hasNext()) {
			finalList.add((BasicDBObject) cursor.next());
		}
		return finalList;
	}
	
	
	*//**
	 * Reads all the data in the collection.
	 * @param collectionName - the collection that needs to be queried
	 * @return A hashmap of key-value pairs
	 *//*
	@Deprecated
	public ArrayList<HashMap<String,String>> readAllHashMap(String collectionName){
		DBCursor cursor = applicationData.getDataBase().getCollection(collectionName).find();
		ArrayList<HashMap<String,String>> finalList = new ArrayList<HashMap<String,String>>();
		while(cursor.hasNext()) {
			HashMap<String,String> hash = new HashMap<String,String>();
			BasicDBObject document3 = new BasicDBObject();
			document3 = (BasicDBObject) cursor.next();
			Iterator it = document3.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				hash.put(pair.getKey().toString(),pair.getValue().toString());
				it.remove();
			}
			finalList.add(hash);
		}
		return finalList;
	}
	
	*//**
	 * Reads all the data in the collection.
	 * @param collectionName - the collection that needs to be queried
	 * @param  numberOfDocuments - the number of lines you want to read
	 * @return A hashmap of key-value pairs
	 *//*
	public ArrayList<BasicDBObject> read(String collectionName,Integer numberOfDocuments){
		DBCursor cursor = applicationData.getDataBase().getCollection(collectionName).find();
		ArrayList<BasicDBObject> finalList = new ArrayList<BasicDBObject>();
		int counter = 0;
		while(cursor.hasNext()&&counter<numberOfDocuments) {
			finalList.add((BasicDBObject) cursor.next());
			counter++;
		}
		return finalList;
	}
	
	*//**
	 * Reads all the data in the collection.
	 * @param collectionName - the collection that needs to be queried
	 * @param  numberOfDocuments - the number of lines you want to read
	 * @return A hashmap of key-value pairs
	 *//*
	@Deprecated
	public List<HashMap<String,String>> readHashMap(String collectionName,Integer numberOfDocuments){
		DBCursor cursor = applicationData.getDataBase().getCollection(collectionName).find();
		ArrayList<HashMap<String,String>> finalList = new ArrayList<HashMap<String,String>>();
		int counter = 0;
		while(cursor.hasNext()&&counter<numberOfDocuments) {
			HashMap<String,String> hash = new HashMap<String,String>();
			BasicDBObject document3 = new BasicDBObject();
			document3 = (BasicDBObject) cursor.next();
			Iterator it = document3.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				hash.put(pair.getKey().toString(),pair.getValue().toString());
				it.remove();
			}
			finalList.add(hash);
			counter++;
		}
		return finalList;
	}
	
	*//**
	 * Find a specific set of values in the collection of your choice
	 * @param conditions - The conditions that have to be met
	 * @param collectionName - the collection that needs to be queried
	 * @return A hashmap of key-value pairs
	 *//*
	public ArrayList<BasicDBObject> find(String collectionName,BasicDBObject conditions){
		DBCursor cursor = applicationData.getDataBase().getCollection(collectionName).find(conditions);
		ArrayList<BasicDBObject> finalList = new ArrayList<BasicDBObject>();
		while(cursor.hasNext()) {
			finalList.add((BasicDBObject) cursor.next());
		}
		return finalList;
	}
	
	*//**
	 * Find a specific set of values in the collection of your choice
	 * @param conditions - The conditions that have to be met
	 * @param collectionName - the collection that needs to be queried
	 * @return A hashmap of key-value pairs
	 *//*
	@Deprecated
	public List<HashMap<String,String>> findHashMap(String collectionName,HashMap<String,String> conditions){
		Iterator it = conditions.entrySet().iterator();
		BasicDBObject document = new BasicDBObject();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			document.put(pair.getKey().toString(),pair.getValue().toString());
			it.remove();
		}
		DBCursor cursor = applicationData.getDataBase().getCollection(collectionName).find(document);
		ArrayList<HashMap<String,String>> finalList = new ArrayList<HashMap<String,String>>();
		int counter = 0;
		while(cursor.hasNext()) {
			HashMap<String,String> hash = new HashMap<String,String>();
			BasicDBObject document3 = new BasicDBObject();
			document3 = (BasicDBObject) cursor.next();
			Iterator it1 = document3.entrySet().iterator();
			while (it1.hasNext()) {
				Map.Entry pair = (Map.Entry)it1.next();
				hash.put(pair.getKey().toString(),pair.getValue().toString());
				it1.remove();
			}
			finalList.add(hash);
			counter++;
		}
		return finalList;
	}
	
	*//**
	 * This function would insert the data into the given collection
	 * @param dataToBeInserted - A Map that has the "column name" as the "key" and the corresponding value 
	 * @param collectionName - Name of the collection in which the data has to be inserted
	 * @returns Number of data successfully inserted into the cache
	 *//*
	public Integer insertIntoCache(BasicDBObject dataToBeInserted,
			String collectionName) {
		Integer value = 0;
		try{
			Iterator it = dataToBeInserted.entrySet().iterator();
			StringBuffer hashValue = new StringBuffer();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				hashValue.append(pair.getValue().toString());
				it.remove();
			}
			dataToBeInserted.put("_id",calculateHashValue(hashValue.toString()));
			applicationData.getDataBase().getCollection(collectionName).insert(dataToBeInserted);
			value = getLineNumber(collectionName);
		}catch(Exception e){
			logger.warn("Failed to insert.The record already exists in the file.");
			return value;
		}
		return value;
	}

	*//**
	 * Get the number of Lines in the collection
	 * @param collectionName
	 * @return
	 *//*
	private Integer getLineNumber(String collectionName) {
		//Code to get the exact number of records present in our database
		BasicDBObject whereQuery = new BasicDBObject();
		Integer value = 0;
		try{
			whereQuery.put("collectionName", collectionName);
			DBObject currentDoc = applicationData.getDataBase().getCollection(applicationData.getMetaDataCollection()).findOne(whereQuery);
			value = Integer.parseInt(currentDoc.get("numberOfLines").toString());
			value = value+1;
			currentDoc.put("numberOfLines", value);
			applicationData.getDataBase().getCollection(applicationData.getMetaDataCollection()).remove(currentDoc);
			applicationData.getDataBase().getCollection(applicationData.getMetaDataCollection()).insert(currentDoc);
		}catch(Exception e){
			logger.info(e.getMessage());
			return 0;
		}
		return value;
	}

	*//**
	 * Purges the data.
	 * @param collectionName
	 * @return boolean - true - Successfully purged 
	 * 					 false - not purged
	 *//*
	public boolean purgeCache(String collectionName){
		try {
			applicationData.getDataBase().getCollection(collectionName).drop();
			createNewCache(collectionName);
		} catch (Exception e) {
			logger.error("Collection has not been purged.");
			logger.error(e.getMessage());
			return false;
		}
		logger.info("Collection has been successfully purged.");
		return true;
	}

	*//**
	 * This function removes the data from the cache
	 * @param dataToBeDeleted
	 * @param collectionName
	 * @return
	 *//*
	public boolean removeByIDFromCache(String dataToBeDeleted,String collectionName){
		try {
			applicationData.getDataBase().getCollection(collectionName).remove(new BasicDBObject().append("_id", calculateHashValue(dataToBeDeleted)));
		} catch (NoSuchAlgorithmException e) {
			logger.error("Problem in deletion.");
			return false;
		} catch (UnsupportedEncodingException e) {
			logger.error("Problem in deletion.");
			return false;
		}
		logger.info("Deleted Successfully.");
		return true;
	}

	*//**
	 * This function removes all the data from the cache which match the condition
	 * @param dataToBeDeleted
	 * @param collectionName
	 * @return
	 *//*
	public boolean removeFromCache(BasicDBObject dataToBeDeleted,String collectionName){
		try {
			applicationData.getDataBase().getCollection(collectionName).remove(dataToBeDeleted);
		} catch (Exception e) {
			logger.error("Problem in deletion.");
			return false;
		}
		logger.info("Deleted Successfully.");
		return true;
	}
	*//**
	 * Converts a string into its HashValue
	 * @param text
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 *//*
	public static String calculateHashValue(String text) 
			throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		byte[] md5hash = new byte[32];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		md5hash = md.digest();
		return convertToHex(md5hash);
	} 

	*//**
	 * Convert into Hash Value
	 * @param data
	 * @return
	 *//*
	private static String convertToHex(byte[] data) { 
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) { 
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do { 
				if ((0 <= halfbyte) && (halfbyte <= 9)) 
					buf.append((char) ('0' + halfbyte));
				else 
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while(two_halfs++ < 1);
		} 
		return buf.toString();
	}

}*/