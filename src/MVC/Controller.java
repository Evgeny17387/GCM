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

import Responses.ResponseController;
import Responses.ResponseModel;

public class Controller {

	public String Run(String msg) {

    	Gson gson = new Gson();

    	Request request = gson.fromJson(msg.toString(), Request.class);

	  	Model model = new Model();

	  	String jsonString = "";

	  	switch (request.mType) {
	  	
	  	// Users
	  	
		case API.ADD_USER:

			System.out.println(API.ADD_USER);

			{
				
				AccountUser accountUser = gson.fromJson(gson.toJson(request.mObject), AccountUser.class);
				
				ResponseModel responseModel = model.AddUser(accountUser);
		
				ResponseController responseController = new ResponseController(API.ADD_USER, responseModel);
		
				jsonString = gson.toJson(responseController);
				
			}

			break;

		case API.GET_USER:

			System.out.println(API.GET_USER);
				
			{
	
				AccountCheckRequest accountCheck = gson.fromJson(gson.toJson(request.mObject), AccountCheckRequest.class);
	
				ResponseModel responseModel = model.GetUser(accountCheck.username, accountCheck.password);
	
		    	ResponseController responseController = new ResponseController(API.GET_USER, responseModel);
	
		    	jsonString = gson.toJson(responseController);
	
			}

			break;

		case API.UPDATE_USER:

			System.out.println(API.UPDATE_USER);

			{
				
				AccountUser accountUser = gson.fromJson(gson.toJson(request.mObject), AccountUser.class);
				
				ResponseModel responseModel = model.UpdateUser(accountUser);
				
				ResponseController responseController = new ResponseController(API.UPDATE_USER, responseModel);
		
				jsonString = gson.toJson(responseController);
				
			}
	
			break;

		// Purchases
			
		case API.BUY_MAP:

			System.out.println(API.BUY_MAP);

			{

				BuyMapRequest buyMapRequest = gson.fromJson(gson.toJson(request.mObject), BuyMapRequest.class);

				ResponseModel responseModel = model.BuyMap(buyMapRequest.mUserName, buyMapRequest.mCityName, buyMapRequest.mType);

		    	ResponseController responseController = new ResponseController(API.BUY_MAP, responseModel);

		    	jsonString = gson.toJson(responseController);

			}

			break;
			

	  	// Workers

		case API.GET_WORKER:

			System.out.println(API.GET_WORKER);

			{

				AccountCheckRequest accountCheck = gson.fromJson(gson.toJson(request.mObject), AccountCheckRequest.class);

				ResponseModel responseModel = model.GetWorker(accountCheck.username, accountCheck.password);

		    	ResponseController responseController = new ResponseController(API.GET_WORKER, responseModel);

		    	jsonString = gson.toJson(responseController);

			}

			break;

		case API.GET_USER_PURCHASES:

			System.out.println(API.GET_USER_PURCHASES);

			{

				AccountCheckRequest accountCheck = gson.fromJson(gson.toJson(request.mObject), AccountCheckRequest.class);

				ResponseModel responseModel = model.GetWorker(accountCheck.username, accountCheck.password);
				
				ResponseController responseController;

				if (responseModel.mErrorCode == ErrorCodes.SUCCESS) {

					if ( ((AccountWorker)responseModel.mObject).mType.equals("Manager") || ((AccountWorker)responseModel.mObject).mType.equals("ManagerContent") ) {

						responseModel = model.GetUsersPurchases();

					} else {

						responseModel.mErrorCode = ErrorCodes.WORKER_NOT_MANAGER;

					}
										
				}

				responseController = new ResponseController(API.GET_USER_PURCHASES, responseModel);

		    	jsonString = gson.toJson(responseController);

			}

			break;

		// Maps search

		case "MapSearch_city_key":

			System.out.println("MapSearch_city_key");

			{

				String cityName = gson.fromJson(gson.toJson(request.mObject), String.class);

				List<Map> mapsListCityName = model.MapsByCity(cityName);
		
		    	ResponseController responseController = new ResponseController("MapSearch_city_key", mapsListCityName);
		
		    	jsonString = gson.toJson(responseController);

			}

			break;

		case "MapSearch_place_key":

			System.out.println("MapSearch_place_key");

			{
				
				String placeName = gson.fromJson(gson.toJson(request.mObject), String.class);
		
				List<Map> mapsListPlaceName = model.MapsByPlace(placeName);
		
		    	ResponseController responseController = new ResponseController("MapSearch_place_key", mapsListPlaceName);
		
		    	jsonString = gson.toJson(responseController);
				
			}

			break;

		default:

			System.out.println("Invalid Request");

			jsonString = "Invalid Request";

		}

		return jsonString;

	}

}
