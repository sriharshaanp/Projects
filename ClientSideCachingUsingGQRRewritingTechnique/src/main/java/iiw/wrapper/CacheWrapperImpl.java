/**
 * 
 */
package iiw.wrapper;

import iiw.manager.AppManagerImpl;
import iiw.utilities.CacheConstants;
import iiw.utilities.Utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * @author sri
 *
 */
public class CacheWrapperImpl implements CacheWrapper{

	final static Logger logger = Logger.getLogger(CacheWrapper.class);

	/**
	 * Wrapper function would create a new Cache in the given DB Name 
	 * @return true  - created successfully, false - not created successfully 
	 * 
	 */
	public boolean createNewCache(String createCache,List<String> columns) throws Exception {
		Boolean result = false;
		try{
			Statement statement = null;
			String createQuery = "Create table \""+createCache +"\" (ID VARCHAR(250) PRIMARY KEY NOT NULL, ";
			int counter = 1;
			for(String column : columns){
				if(counter < columns.size()){
					createQuery += column+" VARCHAR(250),";
					counter+=1;
				}else{
					createQuery += column+" VARCHAR(250));";
				}
			}
			result = AppManagerImpl.getApplicationManager().getDbManager().createNewCache(createQuery);
		}catch(Exception e){
			logger.info("Failed to create the collection.");
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * Wrapper function that creates the selection SQL and calls the function.
	 * @param selectionData
	 * @return
	 */
	public List<String> readAll(Map<String,List<String>> selectionData,Map<String,String> commonVariableList,List<String> finalQueryList){
		Gson gson = new Gson();
		String selectQuery = "Select ";
		Map<String,String> mapOfCommonVariables = new HashMap<String,String>();
		StringBuffer temp = new StringBuffer();
		StringBuffer joinConditions = new StringBuffer();
		if(!commonVariableList.isEmpty()){
			for(String commonVariable : commonVariableList.keySet()){
				String t1 = commonVariable.split("-")[0];
				String t2 = commonVariable.split("-")[1];
				temp.append(t1+"."+commonVariableList.get(commonVariable)+",");
				mapOfCommonVariables.put(commonVariableList.get(commonVariable), commonVariableList.get(commonVariable));
				// Tables to join
				if(selectionData.size()>1){
					joinConditions.append(" \""+t1+"\" INNER JOIN \""+t2+"\" ON \""+t1+"\"."+commonVariableList.get(commonVariable)+"= \""+t2+"\"."+commonVariableList.get(commonVariable));
				}else{
					joinConditions.append(t1);
				}
			}
		}else{
			for(String tableNames : selectionData.keySet()){
				joinConditions.append(" \""+tableNames+"\" ");
			}
		}
		// List of variables
		for(String tableName : selectionData.keySet()){
			List<String> setOfVariables = selectionData.get(tableName);
			for(String t :setOfVariables){
				if(!t.matches(CacheConstants.SKOLEM_CONSTANT_REGEX) && mapOfCommonVariables.get(setOfVariables)==null){
					temp.append("\""+tableName+"\"."+t+",");
				}
			}
		}
		String finalVariableList = temp.substring(0, temp.toString().lastIndexOf(","));
		String finalQuery = selectQuery+" "+finalVariableList+" FROM "+joinConditions;
		ResultSet rs = AppManagerImpl.getApplicationManager().getDbManager().readAll(finalQuery);
		//Need to create a Map of all the data that has been read here
		Map<String,String> mp = new HashMap<String,String>();
		List<String> finalReturnedResult = new ArrayList<String>();
		try{
		while(rs.next()){
			for(String column : finalQueryList){
				try{
					mp.put(column, rs.getString(column));
				} catch (SQLException e) {
					logger.error(e.getMessage());
				}
			}
			finalReturnedResult.add(gson.toJson(mp));
		}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return finalReturnedResult;
	}

	/**
	 * Wrapper function that creates the selection SQL and calls the function to insert the data.
	 * @param queryToInsert
	 * @return
	 */
	public Boolean insertIntoCache(HashMap<String,String> dataToBeInserted,
			String tableName){
		String insert = "insert into \""+tableName + "\" (";
		StringBuffer columnsToInsert = new StringBuffer();
		StringBuffer valuesToInsert = new StringBuffer();
		Integer value = 0;
		Boolean result = false;
		try{
			int counter = 1;
			StringBuffer hashValue = new StringBuffer();
			columnsToInsert.append("id,");
			for (String column : dataToBeInserted.keySet()) {
				hashValue.append(dataToBeInserted.get(column));
				if(counter < dataToBeInserted.size()){
					columnsToInsert.append(column+",");
					valuesToInsert.append("\'"+dataToBeInserted.get(column)+"\',");
				}else{
					columnsToInsert.append(column+") values ");
					valuesToInsert.append("\'"+dataToBeInserted.get(column)+"\');");
				}
				counter++;
			}
			String id = Utilities.calculateHashValue(hashValue.toString());
			result = AppManagerImpl.getApplicationManager().getDbManager().insertIntoCache(insert+columnsToInsert.toString()+" ( \'"+id+"\',"+valuesToInsert.toString());
		}catch(Exception e){
			logger.warn("Failed to insert.The record already exists in the file.");
		}
		return result;
	}

	/**
	 * 
	 * @param deleteTableQuery
	 * @return
	 */
	public boolean purgeCache(List<String> tablesToPurge){
		Boolean result = false;
		try {
			for(String tableName : tablesToPurge){
				String dropTableCommand = "drop table "+tableName;
				result = AppManagerImpl.getApplicationManager().getDbManager().purgeCache(dropTableCommand);
			}
		} catch (Exception e) {
			logger.error("Collection has not been purged.");
			logger.error(e.getMessage());
		}
		logger.info("Collection has been successfully purged.");
		return result;
	}

}
