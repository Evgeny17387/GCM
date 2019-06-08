package MVC;

import java.util.List;

import com.google.gson.Gson;

import DB_classes.AccountUser;
import DB_classes.AccountWorker;
import DB_classes.Map;

import MVC.Model;

import Requests.Request;
import Requests.AccountCheckRequest;

import Responses.Response;
import Responses.AccountCheckResponse;

import Utils.ErrorCodes;

public class Controller {

	public String control(String msg) {

    	Gson gson = new Gson();

    	Request request = gson.fromJson(msg.toString(), Request.class);

	  	Model operations = new Model();

	  	String jsonString = "";

	  	switch (request.type) {
	  	
	  	// Users

		case "AddUser":

			System.out.println("AddUser");

			{
				
				AccountUser accountUser = gson.fromJson(gson.toJson(request.object), AccountUser.class);
				
				AccountCheckResponse accountCheckResponse = operations.AddUser(accountUser);
		
				Response response = new Response("AddUser", accountCheckResponse);
		
				jsonString = gson.toJson(response);
				
			}

			break;

		case "GetUser":

			System.out.println("GetUser");
			
		{

			AccountCheckRequest accountCheck = gson.fromJson(gson.toJson(request.object), AccountCheckRequest.class);

			AccountCheckResponse accountCheckResponse = operations.GetUser(accountCheck.username, accountCheck.password);

	    	Response response = new Response("GetUser", accountCheckResponse);

	    	jsonString = gson.toJson(response);

		}

		break;

		case "UpdateUser":

			System.out.println("UpdateUser");

		{
			
			AccountUser accountUser = gson.fromJson(gson.toJson(request.object), AccountUser.class);
			
			AccountCheckResponse accountCheckResponse = operations.UpdateUser(accountUser);

			
			Response response = new Response("UpdateUser", accountCheckResponse);
	
			jsonString = gson.toJson(response);
			
		}

		break;

	  	// Workers

		case "GetWorker":

			System.out.println("GetWorker");

			{

				AccountCheckRequest accountCheck = gson.fromJson(gson.toJson(request.object), AccountCheckRequest.class);

				AccountCheckResponse accountCheckResponse = operations.GetWorker(accountCheck.username, accountCheck.password);

		    	Response response = new Response("GetWorker", accountCheckResponse);

		    	jsonString = gson.toJson(response);

			}

			break;

		case "GetUsersPurchases":

			System.out.println("GetUsersPurchases");

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

		    	response = new Response("GetUsersPurchases", accountCheckResponse);

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
