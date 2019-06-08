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
import Requests.BuyMapRequest;

import Responses.Response;
import Responses.AccountCheckResponse;

public class Controller {

	public String Run(String msg) {

    	Gson gson = new Gson();

    	Request request = gson.fromJson(msg.toString(), Request.class);

	  	Model model = new Model();

	  	String jsonString = "";

	  	switch (request.type) {
	  	
	  	// Users
	  	
		case API.ADD_USER:

			System.out.println(API.ADD_USER);

			{
				
				AccountUser accountUser = gson.fromJson(gson.toJson(request.object), AccountUser.class);
				
				AccountCheckResponse accountCheckResponse = model.AddUser(accountUser);
		
				Response response = new Response(API.ADD_USER, accountCheckResponse);
		
				jsonString = gson.toJson(response);
				
			}

			break;

		case API.GET_USER:

			System.out.println(API.GET_USER);
				
			{
	
				AccountCheckRequest accountCheck = gson.fromJson(gson.toJson(request.object), AccountCheckRequest.class);
	
				AccountCheckResponse accountCheckResponse = model.GetUser(accountCheck.username, accountCheck.password);
	
		    	Response response = new Response(API.GET_USER, accountCheckResponse);
	
		    	jsonString = gson.toJson(response);
	
			}

			break;

		case API.UPDATE_USER:

			System.out.println(API.UPDATE_USER);

			{
				
				AccountUser accountUser = gson.fromJson(gson.toJson(request.object), AccountUser.class);
				
				AccountCheckResponse accountCheckResponse = model.UpdateUser(accountUser);
	
				
				Response response = new Response(API.UPDATE_USER, accountCheckResponse);
		
				jsonString = gson.toJson(response);
				
			}
	
			break;

		// Purchases
			
		case API.BUY_MAP:

			System.out.println(API.BUY_MAP);

			{

				BuyMapRequest buyMapRequest = gson.fromJson(gson.toJson(request.object), BuyMapRequest.class);

				AccountCheckResponse accountCheckResponse = model.BuyMap(buyMapRequest.mUserName, buyMapRequest.mCityName, buyMapRequest.mType);

		    	Response response = new Response(API.BUY_MAP, accountCheckResponse);

		    	jsonString = gson.toJson(response);

			}

			break;
			

	  	// Workers

		case API.GET_WORKER:

			System.out.println(API.GET_WORKER);

			{

				AccountCheckRequest accountCheck = gson.fromJson(gson.toJson(request.object), AccountCheckRequest.class);

				AccountCheckResponse accountCheckResponse = model.GetWorker(accountCheck.username, accountCheck.password);

		    	Response response = new Response(API.GET_WORKER, accountCheckResponse);

		    	jsonString = gson.toJson(response);

			}

			break;

		case API.GET_USER_PURCHASES:

			System.out.println(API.GET_USER_PURCHASES);

			{

				AccountCheckRequest accountCheck = gson.fromJson(gson.toJson(request.object), AccountCheckRequest.class);

				AccountCheckResponse accountCheckResponse = model.GetWorker(accountCheck.username, accountCheck.password);
				
				Response response;

				if (accountCheckResponse.mErrorCode == ErrorCodes.SUCCESS) {

					if ( ((AccountWorker)accountCheckResponse.mAccount).mType.equals("Manager") || ((AccountWorker)accountCheckResponse.mAccount).mType.equals("ManagerContent") ) {

						accountCheckResponse = model.GetUsersPurchases();

					} else {

						accountCheckResponse.mErrorCode = ErrorCodes.WORKER_NOT_MANAGER;

					}
										
				}

		    	response = new Response(API.GET_USER_PURCHASES, accountCheckResponse);

		    	jsonString = gson.toJson(response);

			}

			break;

		// Maps search

		case "MapSearch_city_key":

			System.out.println("MapSearch_city_key");

			String cityName = gson.fromJson(gson.toJson(request.object), String.class);

			List<Map> mapsListCityName = model.MapsByCity(cityName);

	    	Response responseMapSearchCityName = new Response("MapSearch_city_key", mapsListCityName);

	    	jsonString = gson.toJson(responseMapSearchCityName);

			break;

		case "MapSearch_place_key":

			System.out.println("MapSearch_place_key");

			String placeName = gson.fromJson(gson.toJson(request.object), String.class);

			List<Map> mapsListPlaceName = model.MapsByPlace(placeName);

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
