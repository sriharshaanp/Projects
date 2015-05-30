/**
 * 
 */
package iiw.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

/**
 * @author Sri Harsha
 *
 */
@Path("/sparqlCache")
public class ServiceProvider {
	AppManager appManager;

	public ServiceProvider(){
		appManager = AppManagerImpl.getApplicationManager();
	}

	@POST
	@Path("/post")
	@Consumes("text/plain")
	public Response printMessage(String queryData) {
		Gson gson = new Gson();
		//QueryData qD = gson.fromJson(queryData, QueryData.class);
		List<String> finalResult = AppManagerImpl.getApplicationManager().getData(queryData);
		return Response.status(200).entity(gson.toJson(finalResult)).build();
	}

	@GET
	@Path("/get")
	@Produces("application/json")
	public String getDynamicFilters() {
		String JsonStr=null;
/*		JSONObject json=new JSONObject();
		JSONObject tempjson=new JSONObject();
		tempjson.put("number", 200);
		json.put("response", tempjson);
		JsonStr=json.toString();*/
		System.out.println("inputJson : "+JsonStr);
		Response.ok().header("Access-Control-Allow-Origin", "*").build();
		return "successCallback(" + "Hi" + ");";
	}

}
