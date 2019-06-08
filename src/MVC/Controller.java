package MVC;

import java.util.List;

import com.google.gson.Gson;

import Constants.ErrorCodes;
import Constants.API;

import DB_classes.AccountUser;
import DB_classes.AccountWorker;
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
	  	
	  	// Users
	  	
		case API.ADD_USER:

			System.out.println(API.ADD_USER);

			{
				
				AccountUser accountUser = gson.fromJson(gson.toJson(request.object), AccountUser.class);
				
				AccountCheckResponse accountCheckResponse = operations.AddUser(accountUser);
		
				Response response = new Response(API.ADD_USER, accountCheckResponse);
		
				jsonString = gson.toJson(response);
				
			}

			break;

		case API.GET_USER:

			System.out.println(API.GET_USER);
			
		{

			AccountCheckRequest accountCheck = gson.fromJson(gson.toJson(request.object), AccountCheckRequest.class);

			AccountCheckResponse accountCheckResponse = operations.GetUser(accountCheck.username, accountCheck.password);

	    	Response response = new Response(API.GET_USER, accountCheckResponse);

	    	jsonString = gson.toJson(response);

		}

		break;

		case API.UPDATE_USER:

			System.out.println(API.UPDATE_USER);

		{
			
			AccountUser accountUser = gson.fromJson(gson.toJson(request.object), AccountUser.class);
			
			AccountCheckResponse accountCheckResponse = operations.UpdateUser(accountUser);

			
			Response response = new Response(API.UPDATE_USER, accountCheckResponse);
	
			jsonString = gson.toJson(response);
			
		}

		break;

	  	// Workers

		case API.GET_WORKER:

			System.out.println(API.GET_WORKER);

			{

				AccountCheckRequest accountCheck = gson.fromJson(gson.toJson(request.object), AccountCheckRequest.class);

				AccountCheckResponse accountCheckResponse = operations.GetWorker(accountCheck.username, accountCheck.password);

		    	Response response = new Response(API.GET_WORKER, accountCheckResponse);

		    	jsonString = gson.toJson(response);

			}

			break;

		case API.GET_USER_PURCHASES:

			System.out.println(API.GET_USER_PURCHASES);

			{

				AccountCheckRequest accountCheck = gson.fromJson(gson.toJson(request.object), AccountCheckRequest.class);

				AccountCheckResponse accountCheckResponse = operations.GetWorker(accountCheck.username, accountCheck.password);
				
				Response response;

				if (accountCheckResponse.mErrorCode == ErrorCodes.SUCCESS) {

					if ( ((AccountWorker)accountCheckResponse.mAccount).mType.equals("Manager") || ((AccountWorker)accountCheckResponse.mAccount).mType.equals("ManagerContent") ) {

						accountCheckResponse = operations.GetUsersPurchases();

					} else {

						accountCheckResponse.mErrorCode = ErrorCodes.WORKER_NOT_MANAGER;

					}
										
				}

		    	response = new Response(API.GET_USER_PURCHASES, accountCheckResponse);

		    	jsonString = gson.toJson(response);

			}

			break;

		case "MapSearch_city_key":

			System.out.println("MapSearch_city_key");

			String cityName = gson.fromJson(gson.toJson(request.object), String.class);

			List<Map> mapsListCityName = operations.MapsByCity(cityName);

	    	Response responseMapSearchCityName = new Response("MapSearch_city_key", mapsListCityName);

	    	jsonString = gson.toJson(responseMapSearchCityName);

			break;

		case "MapSearch_place_key":

			System.out.println("MapSearch_place_key");

			String placeName = gson.fromJson(gson.toJson(request.object), String.class);

			List<Map> mapsListPlaceName = operations.MapsByPlace(placeName);

	    	Response responseMapSearchPalceName = new Response("MapSearch_place_key", mapsListPlaceName);

	    	jsonString = gson.toJson(responseMapSearchPalceName);

			break;

		default:

			System.out.println("Invalid Request");

			jsonString = "Invalid Request";

		}

		return jsonString;

	}

}
