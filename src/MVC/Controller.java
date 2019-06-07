package MVC;

import java.util.List;

import com.google.gson.Gson;

import DB_classes.Map;

import MVC.Model;

import Requests.Register;
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

		case "Register":
			
			{
				
				Register register = gson.fromJson(gson.toJson(request.object), Register.class);
				
				AccountCheckResponse accountCheckResponse = operations.AddUser(register.name, register.password, register.email, register.creditCard);
		
				Response response = new Response("Register", accountCheckResponse);
		
				jsonString = gson.toJson(response);
				
			}

			break;

		case "UserCheck":
			
			{
				
				AccountCheckRequest accountCheck = gson.fromJson(gson.toJson(request.object), AccountCheckRequest.class);
	
				AccountCheckResponse accountCheckResponse = operations.isValidAccount("Users", accountCheck.username, accountCheck.password);
	
		    	Response response = new Response("UserCheck", accountCheckResponse);
	
		    	jsonString = gson.toJson(response);
				
			}

			break;

		case "WorkerCheck":

			{

				AccountCheckRequest accountCheck = gson.fromJson(gson.toJson(request.object), AccountCheckRequest.class);

				AccountCheckResponse accountCheckResponse = operations.isValidAccount("Workers", accountCheck.username, accountCheck.password);

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
