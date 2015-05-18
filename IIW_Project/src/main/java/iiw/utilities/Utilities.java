/**
 * 
 */
package iiw.utilities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sri
 *
 */
public class Utilities {
	/**
	 * 
	 * @param string
	 * @return
	 * @author Sri
	 */
	public static List<String> getFinalVariableList(String query) {
		List<String> val1 = new ArrayList<String>();
		Matcher m = Pattern.compile(CacheConstants.QUERY_REGEX).matcher(query);
		while(m.find()) {
			String variables = m.group(2).replace("(", "");
			variables = variables.replace(")", "");
			for(String val :variables.split(",")){
				val1.add(val);
			}
		}
		return val1;
	}
	/**
	 * 
	 * @param tableName
	 * @param refactoredQuery
	 * @param commonVariables
	 * @return
	 */
	public static List<String> getUncommonVariables (String tableName,Map<String,Map<Integer,String>> refactoredQuery){
		List<String> temp = new ArrayList<String>();
		for(Integer value :refactoredQuery.get(tableName).keySet()){
			temp.add(refactoredQuery.get(tableName).get(value));
		}
		return temp;
	}
	
	/**
	 * 
	 * @param queryPredicate
	 * @param query
	 * @return
	 */
	public static Map<String,Map<Integer,String>> refactoringQueries(String queryPredicate, String query) {
		Map<String,Map<Integer,String>> resQueries = new HashMap<String,Map<Integer,String>>();
		Matcher m = Pattern.compile(CacheConstants.QUERY_REGEX).matcher(query);
		while(m.find()) {
			String viewQuery = m.group(2);
			String[] viewQueryList = viewQuery.substring(1, viewQuery.length()-1).split(",");
			/* Getting the different query variables for each predicate and if there are any duplicate entires we combine them*/
			int counter = 0;
			Map<Integer,String> temp = new HashMap<Integer, String>();
			if(resQueries.get(m.group(1))==null){
				for(String value : viewQueryList){
					counter +=1;
					if(!value.trim().matches("Z\\d+AT\\w+DOT\\w+CUR\\d+")){
						temp.put(counter, value.trim());
					}
				}
			}else{
				for(String value : viewQueryList){
					counter +=1;
					temp = resQueries.get(m.group(1));
					if(temp.get(counter).matches(CacheConstants.SKOLEM_CONSTANT_REGEX)){
						temp.put(counter,value.trim());
					}
				}
			}
			resQueries.put(m.group(1),temp);
		}
		return resQueries;
	}

	/**
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 */
	/* This part of the code helps in finding the common variables in the predicates */
	private static String equalMaps(Map<Integer,String> m1, Map<Integer,String> m2) {
		for (Integer key: m1.keySet())
			for(Integer key1: m2.keySet())
				if (!m2.get(key1).matches(CacheConstants.SKOLEM_CONSTANT_REGEX) && (m1.get(key).equals(m2.get(key1))))
					return m1.get(key);
		return "none";
	}

	/**
	 * 
	 * @param queryList
	 * @return
	 */
	public static Map<String,String> findAllCommonVariables(Map<String,Map<Integer,String>> queryList){
		Map<String,String> commonVariableList = new HashMap<String,String>();
		Iterator it = queryList.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			Map<Integer, String> m =  (Map<Integer, String>) pair.getValue();
			Iterator it1 = queryList.entrySet().iterator();
			while (it1.hasNext()) {
				Map.Entry pair1 = (Map.Entry)it1.next();
				if(!pair1.getKey().equals(pair.getKey())){
					String commonVariable = equalMaps((Map<Integer,String>)pair.getValue(),(Map<Integer,String>)pair1.getValue());
					if(!commonVariable.equals("none")){
						commonVariableList.put(pair.getKey()+"-"+pair1.getKey(), commonVariable);
						break;
					}
				}
			}
		}
		return commonVariableList;
	}
	
	/**
	 * Converts a string into its HashValue
	 * @param text
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String calculateHashValue(String text) 
			throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		byte[] md5hash = new byte[32];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		md5hash = md.digest();
		return convertToHex(md5hash);
	} 

	/**
	 * Convert into Hash Value
	 * @param data
	 * @return
	 */
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
}
