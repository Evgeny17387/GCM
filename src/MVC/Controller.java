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
import Requests.GeneralRequest;
import Requests.ProposeNewPriceRequest;
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
	
				GeneralRequest accountCheck = gson.fromJson(gson.toJson(request.mObject), GeneralRequest.class);
	
				ResponseModel responseModel = model.GetUser(accountCheck.mUserName, accountCheck.mPassword);
	
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

		// Subscription

		case API.BUY_SUBSCRITION:

			System.out.println(API.BUY_SUBSCRITION);

			{

				GeneralRequest generalRequest = gson.fromJson(gson.toJson(request.mObject), GeneralRequest.class);

				ResponseModel responseModel = model.BuySubscription(generalRequest.mUserName);

				ResponseController responseController = new ResponseController(API.BUY_SUBSCRITION, responseModel);

				jsonString = gson.toJson(responseController);

			}

			break;

		case API.DELETE_SUBSCRITION:

			System.out.println(API.DELETE_SUBSCRITION);

			{

				GeneralRequest generalRequest = gson.fromJson(gson.toJson(request.mObject), GeneralRequest.class);

				ResponseModel responseModel = model.DeleteSubscription(generalRequest.mUserName);

				ResponseController responseController = new ResponseController(API.DELETE_SUBSCRITION, responseModel);

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

				GeneralRequest accountCheck = gson.fromJson(gson.toJson(request.mObject), GeneralRequest.class);

				ResponseModel responseModel = model.GetWorker(accountCheck.mUserName, accountCheck.mPassword);

		    	ResponseController responseController = new ResponseController(API.GET_WORKER, responseModel);

		    	jsonString = gson.toJson(responseController);

			}

			break;

		case API.GET_USER_PURCHASES:

			System.out.println(API.GET_USER_PURCHASES);

			{

				GeneralRequest accountCheck = gson.fromJson(gson.toJson(request.mObject), GeneralRequest.class);

				ResponseModel responseModel = model.GetWorker(accountCheck.mUserName, accountCheck.mPassword);
				
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
			
		// Propose New price for a map

		case API.PROPOSE_NEW_PRICE:

			System.out.println(API.PROPOSE_NEW_PRICE);

			{

				ProposeNewPriceRequest proposeNewPriceRequest = gson.fromJson(gson.toJson(request.mObject), ProposeNewPriceRequest.class);

				ResponseModel responseModel = model.ProposeNewPrice(proposeNewPriceRequest.mMapName, proposeNewPriceRequest.mProposedPrice);
				
				ResponseController responseController;

				responseController = new ResponseController(API.PROPOSE_NEW_PRICE, responseModel);

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
