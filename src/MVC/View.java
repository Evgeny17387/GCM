package MVC;

import java.util.List;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Constants.ErrorCodes;
import Constants.API;

import DB_classes.Map;
import DB_classes.Place;
import DB_classes.Purchase;
import DB_classes.AccountUser;
import DB_classes.AccountWorker;

import Responses.ResponseController;
import Responses.ResponseModel;

import GUI.UI_server_communicate;

public class View {

	public void Run(String message) {

	    try {

	    	Gson gson = new Gson();

	    	ResponseController responseController = gson.fromJson(message, ResponseController.class);

	      	switch (responseController.mType) {

      		// Users
      	
	    	case API.ADD_USER:

	    	    System.out.println(API.ADD_USER);

		    	{
		
		    		ResponseModel responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);

		    	    System.out.println(responseModel.mErrorCode);

		    		if (responseModel.mErrorCode == ErrorCodes.SUCCESS) {

			    		AccountUser accountUser = gson.fromJson(gson.toJson(responseModel.mObject), AccountUser.class);

						System.out.format(accountUser.toString());

		    		}else if (responseModel.mErrorCode == ErrorCodes.USER_ALREADY_EXISTS) {
		    			
			    	    System.out.println("User with this userName already exists");

		    		}else if (responseModel.mErrorCode == ErrorCodes.USER_DETAILS_MISSING) {
	    			
		    			System.out.println("One or more registration details are missing");
	    			
		    		}

		    	}

    	    	break;

	    	case API.GET_USER:

	    	    System.out.println(API.GET_USER);

		    	{
		    				    		
		    		ResponseModel responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);

		    	    System.out.println(responseModel.mErrorCode);

		    		if (responseModel.mErrorCode == ErrorCodes.SUCCESS) {
		    			
			    		AccountUser accountUser = gson.fromJson(gson.toJson(responseModel.mObject), AccountUser.class);

						System.out.println(accountUser.toString());

		    		} else if (responseModel.mErrorCode == ErrorCodes.USER_NOT_FOUND) {

		    			System.out.println("User not found");
		    			
		    		} else if (responseModel.mErrorCode == ErrorCodes.USER_DETAILS_MISSING) {
		    			
		    			System.out.println("UserName or Password are missing");
	    			
		    		}
			    		
		    	}

	    	    break;

	    	case API.UPDATE_USER:
	    		
	    	    System.out.println(API.UPDATE_USER);

		    	{
		
		    		ResponseModel responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);

		    	    System.out.println(responseModel.mErrorCode);

		    		if (responseModel.mErrorCode == ErrorCodes.SUCCESS) {

			    		AccountUser accountUser = gson.fromJson(gson.toJson(responseModel.mObject), AccountUser.class);

						System.out.format(accountUser.toString());

		    		}else if (responseModel.mErrorCode == ErrorCodes.USER_DETAILS_MISSING) {
	    			
		    			System.out.println("One or more user details are missing");
	    			
		    		}

		    	}

    	    	break;

        	// Purchases

	    	case API.BUY_MAP:

	    	    System.out.println(API.BUY_MAP);

		    	{
		    		
		    		ResponseModel responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);
	
		    	    System.out.println(responseModel.mErrorCode);

		    		if (responseModel.mErrorCode == ErrorCodes.SUCCESS) {
		    			
		    			
		    			
		    		}else if (responseModel.mErrorCode == ErrorCodes.PURCHASE_DETAILS_MISSING) {

			    	    System.out.println("Purchase details are missing");

			    	}

		    	}

	    	    break;

    	    // Workers

	    	case API.GET_WORKER:

	    	    System.out.println(API.GET_WORKER);

		    	{
		    		
		    		ResponseModel responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);
	
		    	    System.out.println(responseModel.mErrorCode);

		    		if (responseModel.mErrorCode == ErrorCodes.SUCCESS) {
		    			
		    			AccountWorker accountWorker = gson.fromJson(gson.toJson(responseModel.mObject), AccountWorker.class);

						System.out.format(accountWorker.toString());

		    		} else if (responseModel.mErrorCode == ErrorCodes.USER_NOT_FOUND) {

		    			System.out.println("Worker not found");
		    			
		    		}

		    	}

	    	    break;

	    	case API.GET_USER_PURCHASES:

	    	    System.out.println(API.GET_USER_PURCHASES);

		    	{

		    		ResponseModel responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);

		    	    System.out.println(responseModel.mErrorCode);

		    		if (responseModel.mErrorCode == ErrorCodes.SUCCESS) {

			    	    Type type = new TypeToken<List<Purchase>>(){}.getType();

			    	    List<Purchase> purchaseList = new Gson().fromJson(gson.toJson(responseModel.mObject), type);

			    	    for (int i = 0; i < purchaseList.size(); i++) {
			    	    	Purchase purchase = purchaseList.get(i);
							System.out.println(purchase.toString());
			    	    }
		    			
		    		} else if (responseModel.mErrorCode == ErrorCodes.USER_NOT_FOUND) {

		    			System.out.println("Worker is not found");

		    		} else if (responseModel.mErrorCode == ErrorCodes.WORKER_NOT_MANAGER) {

		    			System.out.println("Worker is not manager");

		    		}

		    	}

	    	    break;

	    	case "MapSearch_city_key":

	    	    System.out.println("MapSearch_city_key");

		    	{
		    		
		    	    Type typeMap = new TypeToken<List<Map>>(){}.getType();
		    	    List<Map> mapList = new Gson().fromJson(gson.toJson(responseController.mObject), typeMap);
		    	    for (int i = 0; i < mapList.size(); i++) {
		    	    	Map map = mapList.get(i);
						System.out.format("%s - %s - %d - %s\n", map.mName, map.mCity, map.mVersion, map.mDescription);
						for (Place place : map.mPlaces) {
							System.out.format("%s - %s\n", place.mName, place.mClassification);
						}
		    	    }
		    		
		    	}

	    		break;

	    	case "MapSearch_place_key":

	    	    System.out.println("MapSearch_place_key");
	    	    
	    	    {

		    	    Type typeMap = new TypeToken<List<Map>>(){}.getType();
		    	    List<Map> mapList = new Gson().fromJson(gson.toJson(responseController.mObject), typeMap);
		    	    for (int i = 0; i < mapList.size(); i++) {
		    	    	Map map = mapList.get(i);
						System.out.format("%s - %s - %d - %s\n", map.mName, map.mCity, map.mVersion, map.mDescription);
						for (Place place : map.mPlaces) {
							System.out.format("%s - %s\n", place.mName, place.mClassification);
						}
		    	    }

	    	    }

	    		break;

			default:
				
	    	    System.out.println("Invalid Response");

	      	}

		} catch (Exception e) {

		    System.out.println(message + " - Not a JSON");

		}

	    UI_server_communicate.mResposeFromserver = true;

	}

}
