package iiw.test;
import iiw.manager.AppManagerImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 */

/**
 * @author sri
 *
 */
public class CrudTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String value = "11";
		try {
			AppManagerImpl.getApplicationManager().initialize();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Testing the creation part 
		List<String> listOfCols = new ArrayList<String>();
		listOfCols.add("name");
		listOfCols.add("address");
		try {
			AppManagerImpl.getApplicationManager().getWrapper().createNewCache("harsha"+value, listOfCols);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Testing the Insertion part
		HashMap<String,String> mapOfVals = new HashMap<String,String>();
		mapOfVals.put("name","Sri");
		mapOfVals.put("address","hosur");
		try {
			AppManagerImpl.getApplicationManager().getWrapper().insertIntoCache(mapOfVals,"harsha"+value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Testing the reading part
		Map<String,String> commonVariables = new HashMap<String,String>();
		commonVariables.put("harsha-"+"harsha"+value, "name");
		HashMap<String,List<String>> mapOfVals1 = new HashMap<String,List<String>>();
		List<String> listOfVariables = new ArrayList<String>();
		listOfVariables.add("address");
		List<String> listOfVariables2 = new ArrayList<String>();
		listOfVariables2.add("address1");
		mapOfVals1.put("harsha", listOfVariables);
		mapOfVals1.put("harsha"+value, listOfVariables2);
		try {
			List<String> finalVariableList = new ArrayList<String>();
			finalVariableList.add("name");
			finalVariableList.add("address");
			finalVariableList.add("address1");
			List<String> mp = AppManagerImpl.getApplicationManager().getWrapper().readAll(mapOfVals1,commonVariables,finalVariableList);
			for(String data :mp){
				System.out.println(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
