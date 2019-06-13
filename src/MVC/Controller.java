package MVC;

import java.util.List;

import com.google.gson.Gson;

import DB_classes.AccountUser;
import DB_classes.AccountWorker;
import DB_classes.CityMap;
import DB_classes.Place;
import DB_classes.Route;
import Defines.API;
import Defines.ErrorCodes;
import MVC.Model;

import Requests.Request;
import Requests.GeneralRequest;
import Requests.GetRoutesRequest;
import Requests.ProposeNewPriceRequest;
import Requests.BuyRequest;

import Responses.ResponseController;
import Responses.ResponseModel;

public class Controller {

	public String Run(String msg) {

    	Gson gson = new Gson();

    	Request request = gson.fromJson(msg.toString(), Request.class);

	  	Model model = new Model();

	  	String jsonString = "";

		System.out.println("Controller: " + request.mType);

	  	switch (request.mType) {
	  	
	  	// Users
	  	
		case API.ADD_USER:

			{
				
				AccountUser accountUser = gson.fromJson(gson.toJson(request.mObject), AccountUser.class);
				
				ResponseModel responseModel = model.AddUser(accountUser);
		
				ResponseController responseController = new ResponseController(request.mType, responseModel);
		
				jsonString = gson.toJson(responseController);
				
			}

			break;

		case API.GET_USER:
	
			{
	
				GeneralRequest accountCheck = gson.fromJson(gson.toJson(request.mObject), GeneralRequest.class);
	
				ResponseModel responseModel = model.GetUser(accountCheck.mUserName, accountCheck.mPassword);
	
		    	ResponseController responseController = new ResponseController(API.GET_USER, responseModel);
	
		    	jsonString = gson.toJson(responseController);
	
			}

			break;

		case API.UPDATE_USER:

			{
				
				AccountUser accountUser = gson.fromJson(gson.toJson(request.mObject), AccountUser.class);
				
				ResponseModel responseModel = model.UpdateUser(accountUser);
		
				ResponseController responseController = new ResponseController(request.mType, responseModel);
		
				jsonString = gson.toJson(responseController);
				
			}

			break;

		// Purchases

		case API.BUY:

			{

				BuyRequest buyRequest = gson.fromJson(gson.toJson(request.mObject), BuyRequest.class);

				ResponseModel responseModel = model.Buy(buyRequest.mUserName, buyRequest.mCityName, buyRequest.mType);

		    	ResponseController responseController = new ResponseController(API.BUY, responseModel);

		    	jsonString = gson.toJson(responseController);

			}

			break;

	  	// Workers

		case API.GET_WORKER:

			{

				GeneralRequest accountCheck = gson.fromJson(gson.toJson(request.mObject), GeneralRequest.class);

				ResponseModel responseModel = model.GetWorker(accountCheck.mUserName, accountCheck.mPassword);

		    	ResponseController responseController = new ResponseController(API.GET_WORKER, responseModel);

		    	jsonString = gson.toJson(responseController);

			}

			break;

		case API.GET_USERS_PURCHASES:

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

				responseController = new ResponseController(API.GET_USERS_PURCHASES, responseModel);

		    	jsonString = gson.toJson(responseController);

			}

			break;

		// Propose New price for a map

		case API.PROPOSE_NEW_PRICE:

			{

				ProposeNewPriceRequest proposeNewPriceRequest = gson.fromJson(gson.toJson(request.mObject), ProposeNewPriceRequest.class);

				ResponseModel responseModel = model.ProposeNewPrice(proposeNewPriceRequest.mMapName, proposeNewPriceRequest.mProposedPrice);
				
				ResponseController responseController;

				responseController = new ResponseController(API.PROPOSE_NEW_PRICE, responseModel);

		    	jsonString = gson.toJson(responseController);

			}

			break;

			// Approve proposed price

			case API.APPROVE_PROPOSED_PRICE:

				{

					ProposeNewPriceRequest proposeNewPriceRequest = gson.fromJson(gson.toJson(request.mObject), ProposeNewPriceRequest.class);

					ResponseModel responseModel = model.ApproveProposePrice(proposeNewPriceRequest.mMapName, proposeNewPriceRequest.mProposedPrice);
					
					ResponseController responseController;

					responseController = new ResponseController(API.APPROVE_PROPOSED_PRICE, responseModel);

			    	jsonString = gson.toJson(responseController);

				}

				break;

		// Maps search

		case "MapSearch_city_key":

			{

				String cityName = gson.fromJson(gson.toJson(request.mObject), String.class);

				ResponseModel responseModel = model.MapsByCity(cityName);
		
		    	ResponseController responseController = new ResponseController("MapSearch_city_key", responseModel);
		
		    	jsonString = gson.toJson(responseController);

			}

			break;

		case "MapSearch_place_key":

			{
				
				String placeName = gson.fromJson(gson.toJson(request.mObject), String.class);
		
				List<CityMap> mapsListPlaceName = model.MapsByPlace(placeName);
		
		    	ResponseController responseController = new ResponseController("MapSearch_place_key", mapsListPlaceName);
		
		    	jsonString = gson.toJson(responseController);
				
			}

			break;

		// Routes

		case API.GET_ROUTES:

			{

				GetRoutesRequest getRoutesRequest = gson.fromJson(gson.toJson(request.mObject), GetRoutesRequest.class);

				List<Route> routes = model.GetRoutes(getRoutesRequest.mCityName);
		
		    	ResponseController responseController = new ResponseController(API.GET_ROUTES, routes);
		
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
