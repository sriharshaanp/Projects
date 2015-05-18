package iiw.test;

import iiw.pojo.QueryData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;

public class RestServiceTester {

	/*public void queryConverterTest() {
		//Testing the checkTableForData Functionality
		String resQuery = "q0(X1,X4,X2,X0,X7,X13,X13,X7,X15,X12) :- "
				+ "vq(Z0ATvqDOTm140041D2D3D4DCiD0DiCUR0, X4, X2, Z3ATvqDOTm140041D2D3D4DCiD0DiCUR0, Z4ATvqDOTm140041D2D3D4DCiD0DiCUR0, Z5ATvqDOTm140041D2D3D4DCiD0DiCUR0, Z6ATvqDOTm140041D2D3D4DCiD0DiCUR0, Z7ATvqDOTm140041D2D3D4DCiD0DiCUR0, X15, X12) , "
				+ "v8(Z5ATv8DOTm70041D2D3D4DCiD0DiCUR0, Z1ATv8DOTm70041D2D3D4DCiD0DiCUR0, Z2ATv8DOTm70041D2D3D4DCiD0DiCUR0, Z3ATv8DOTm70041D2D3D4DCiD0DiCUR0, Z4ATv8DOTm70041D2D3D4DCiD0DiCUR0, Z5ATv8DOTm70041D2D3D4DCiD0DiCUR0, Z6ATv8DOTm70041D2D3D4DCiD0DiCUR0, X7, Z8ATv8DOTm70041D2D3D4DCiD0DiCUR0, Z9ATv8DOTm70041D2D3D4DCiD0DiCUR0) , "
				+ "v11(Z0ATv11DOTm60041D2D3D4DCiD0DiCUR0, Z1ATv11DOTm60041D2D3D4DCiD0DiCUR0, Z2ATv11DOTm60041D2D3D4DCiD0DiCUR0, Z3ATv11DOTm60041D2D3D4DCiD0DiCUR0, Z4ATv11DOTm60041D2D3D4DCiD0DiCUR0, Z5ATv8DOTm70041D2D3D4DCiD0DiCUR0, X1, X7, Z8ATv11DOTm60041D2D3D4DCiD0DiCUR0, Z9ATv11DOTm60041D2D3D4DCiD0DiCUR0) , "
				+ "vq(Z0ATvqDOTm160041D2D3D4DCiD0DiCUR0, Z1ATvqDOTm160041D2D3D4DCiD0DiCUR0, X2, X0, Z4ATvqDOTm160041D2D3D4DCiD0DiCUR0, Z5ATvqDOTm160041D2D3D4DCiD0DiCUR0, Z6ATvqDOTm160041D2D3D4DCiD0DiCUR0, X7, X15, Z9ATvqDOTm160041D2D3D4DCiD0DiCUR0) , "
				+ "v7(Z0ATv7DOTm30041D2D3D4DCiD0DiCUR0, Z5ATv8DOTm70041D2D3D4DCiD0DiCUR0, X7, Z3ATv7DOTm30041D2D3D4DCiD0DiCUR0, Z4ATv7DOTm30041D2D3D4DCiD0DiCUR0, X1, X0, Z7ATv7DOTm30041D2D3D4DCiD0DiCUR0, Z8ATv7DOTm30041D2D3D4DCiD0DiCUR0, Z9ATv7DOTm30041D2D3D4DCiD0DiCUR0) , "
				+ "v8(Z0ATv8DOTm80041D2D3E4DCiD0DiCUR0, Z1ATv8DOTm80041D2D3E4DCiD0DiCUR0, Z2ATv8DOTm80041D2D3E4DCiD0DiCUR0, Z3ATv8DOTm80041D2D3E4DCiD0DiCUR0, X13, X4, Z6ATv8DOTm80041D2D3E4DCiD0DiCUR0, Z7ATv8DOTm80041D2D3E4DCiD0DiCUR0, Z8ATv8DOTm80041D2D3E4DCiD0DiCUR0, X7) , "
				+ "v7(Z0ATv7DOTm120041D2D3D4DCiD0DiCUR0, Z1ATv7DOTm120041D2D3D4DCiD0DiCUR0, Z2ATv7DOTm120041D2D3D4DCiD0DiCUR0, Z3ATv7DOTm120041D2D3D4DCiD0DiCUR0, Z4ATv7DOTm120041D2D3D4DCiD0DiCUR0, X0, X13, Z7ATv7DOTm120041D2D3D4DCiD0DiCUR0, X13, Z9ATv7DOTm120041D2D3D4DCiD0DiCUR0) , "
				+ "v11(Z0ATv11DOTm20041D2D3D4DCiD0DiCUR1, Z1ATv11DOTm20041D2D3D4DCiD0DiCUR1, Z2ATv11DOTm20041D2D3D4DCiD0DiCUR1, X4, X2, Z5ATv11DOTm20041D2D3D4DCiD0DiCUR1, Z6ATv11DOTm20041D2D3D4DCiD0DiCUR1, X1, X1, Z9ATv11DOTm20041D2D3D4DCiD0DiCUR1)";
		//String resQuery = "1=(1-2,3-4)";
		Map<String, Map<Integer, String>> resQueries = Utilities.refactoringQueries(resQuery.split(":-")[0],resQuery.split(":-")[1]);
		Iterator it = resQueries.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			Map<Integer, String> m =  (Map<Integer, String>) pair.getValue();
			Iterator it1 = m.entrySet().iterator();
			while (it1.hasNext()) {
				Map.Entry pair1 = (Map.Entry)it1.next();
				System.out.println(pair.getKey() + " = " + pair1.getValue());
			}
			it.remove(); // avoids a ConcurrentModificationException
		}
		Map<String,String> commonVariables = Utilities.findAllCommonVariables(resQueries);
		Iterator it1 = commonVariables.entrySet().iterator();
		while (it1.hasNext()) {
			Map.Entry pair = (Map.Entry)it1.next();
			System.out.println(pair.getKey()+","+pair.getValue());
			it1.remove(); // avoids a ConcurrentModificationException
		} 
	}*/
	
	@Test
	public void webServiceClient(){
		Gson gson = new Gson();
		try{
		URL url = new URL("http://localhost:8081/RESTfulExample/rest/sparqlCache/post");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "text/plain");
		
		//String input = "{\"qty\":100,\"name\":\"iPad 4\"}";
		//QueryData product = new QueryData();
		String query  = "SELECT ?seriesName , ?date , ?number , ?season , ?director , ?abstract , ?photographer , ?previousWork , ?releaseDate , ?subsequentWork , ?wikiPageID WHERE { "+
      " ?e <http://dbpedia.org/ontology/series>  ?seriesName ; "+
      "<http://dbpedia.org/ontology/releaseDate> ?date ; "+
      "<http://dbpedia.org/ontology/episodeNumber> ?number ; "+
      "<http://dbpedia.org/ontology/seasonNumber> ?season ; "+
      "<http://dbpedia.org/ontology/director> ?director ; "+
      "<http://dbpedia.org/ontology/abstract> ?abstract ; "+
      "<http://dbpedia.org/ontology/photographer> ?photographer ; "+
      "<http://dbpedia.org/ontology/previousWork> ?previousWork ; "+
      "<http://dbpedia.org/ontology/releaseDate> ?releaseDate ; "+
      "<http://dbpedia.org/ontology/subsequentWork> ?subsequentWork ; "+
      "<http://dbpedia.org/ontology/wikiPageID> ?wikiPageID . "+
      "}";
		//product.setMiscData("bye");
		//String convertedProduct = gson.toJson(product);
		/*conn.setRequestProperty("product", convertedProduct);
		*/
		OutputStream os = conn.getOutputStream();
		os.write(query.getBytes());
		os.flush();

		/*if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}*/

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output= br.readLine();
		System.out.println("Output from Server .... \n");
		List<String> prduct = gson.fromJson(output, List.class);
		for(String value : prduct){
			System.out.println(value);
		}
		System.out.println("Product Name : "+prduct);

		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();
	  } catch (IOException e) {

		e.printStackTrace();
	 }
	}

}
