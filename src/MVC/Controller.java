package MVC;

import java.util.List;

import com.google.gson.Gson;

import DB_classes.AccountUser;
import DB_classes.Map;

import MVC.Model;

import Requests.Request;
import Requests.AccountCheckRequest;

import Responses.Response;
import Responses.AccountCheckResponse;

public class Controller {

	public String control(String msg) {

    	Gson gson = new Gson();

    	Request request = gson.fromJson(msg.toString(), Request.class);

	  	Model operations = new Model();

	  	String jsonString = "";

	  	switch (request.type) {

		case "AddUser":
			
			{
				
				AccountUser accountUser = gson.fromJson(gson.toJson(request.object), AccountUser.class);
				
				AccountCheckResponse accountCheckResponse = operations.AddUser(accountUser);
		
				Response response = new Response("AddUser", accountCheckResponse);
		
				jsonString = gson.toJson(response);
				
			}

			break;

		case "GetUser":
			
		{

			AccountCheckRequest accountCheck = gson.fromJson(gson.toJson(request.object), AccountCheckRequest.class);

			AccountCheckResponse accountCheckResponse = operations.GetUser(accountCheck.username, accountCheck.password);

	    	Response response = new Response("GetUser", accountCheckResponse);

	    	jsonString = gson.toJson(response);

		}

		break;

		case "UpdateUser":
			
		{
			
			AccountUser accountUser = gson.fromJson(gson.toJson(request.object), AccountUser.class);
			
			AccountCheckResponse accountCheckResponse = operations.UpdateUser(accountUser);

			
			Response response = new Response("UpdateUser", accountCheckResponse);
	
			jsonString = gson.toJson(response);
			
		}

		break;

		case "WorkerCheck":

			{

				AccountCheckRequest accountCheck = gson.fromJson(gson.toJson(request.object), AccountCheckRequest.class);

				AccountCheckResponse accountCheckResponse = operations.GetWorker(accountCheck.username, accountCheck.password);

		    	Response response = new Response("WorkerCheck", accountCheckResponse);

		    	jsonString = gson.toJson(response);

			}

			break;

		case "MapSearch_city_key":

			String cityName = gson.fromJson(gson.toJson(request.object), String.class);

			List<Map> mapsListCityName = operations.MapsByCity(cityName);

	    	Response responseMapSearchCityName = new Response("MapSearch_city_key", mapsListCityName);

	    	jsonString = gson.toJson(responseMapSearchCityName);

			break;

		case "MapSearch_place_key":

			String placeName = gson.fromJson(gson.toJson(request.object), String.class);

			List<Map> mapsListPlaceName = operations.MapsByPlace(placeName);

	    	Response responseMapSearchPalceName = new Response("MapSearch_place_key", mapsListPlaceName);

	    	jsonString = gson.toJson(responseMapSearchPalceName);

			break;

		default:

			jsonString = "Invalid Request";

		}

		return jsonString;

	}

}
