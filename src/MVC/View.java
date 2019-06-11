package MVC;
import GUI.Main;

import java.util.List;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Constants.ErrorCodes;
import Constants.API;
import DB_classes.CityMap;
import DB_classes.Place;
import DB_classes.Purchase;
import DB_classes.AccountUser;
import DB_classes.AccountWorker;

import Responses.ResponseController;
import Responses.ResponseModel;
import Utils.UI_server_communicate;

public class View {
	public  int valid_flag=-1;
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
		    			GUI.Main.my_flag = 1;
		    		}else if (responseModel.mErrorCode == ErrorCodes.USER_ALREADY_EXISTS) {
		    			GUI.Main.my_flag = 0;
			    	    System.out.println("User with this userName already exists");
		    		}else if (responseModel.mErrorCode == ErrorCodes.USER_DETAILS_MISSING) {
		    			System.out.println("One or more registration details are missing");
		    			GUI.Main.my_flag = 2;
	    			
		    		}

		    	}

			    UI_server_communicate.mResposeFromserver = true;

    	    	break;

	    	case API.GET_USER:

	    	    System.out.println(API.GET_USER);

		    	{
		    				    		
		    		ResponseModel responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);

		    	    System.out.println(responseModel.mErrorCode);

		    		if (responseModel.mErrorCode == ErrorCodes.SUCCESS) {
		    			
			    		AccountUser accountUser = gson.fromJson(gson.toJson(responseModel.mObject), AccountUser.class);

						System.out.println(accountUser.toString());
		    			GUI.Main.my_flag = 2;

		    		} else if (responseModel.mErrorCode == ErrorCodes.USER_NOT_FOUND) {

		    			System.out.println("User not found");
		    			GUI.Main.my_flag = 0;

		    			
		    		} else if (responseModel.mErrorCode == ErrorCodes.USER_DETAILS_MISSING) {
		    			
		    			System.out.println("UserName or Password are missing");
		    			GUI.Main.my_flag = 1;

	    			
		    		}
			    		
		    	}

			    UI_server_communicate.mResposeFromserver = true;

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

			    UI_server_communicate.mResposeFromserver = true;

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

			    UI_server_communicate.mResposeFromserver = true;

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

			    UI_server_communicate.mResposeFromserver = true;

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

			    UI_server_communicate.mResposeFromserver = true;

	    	    break;

	    	case "MapSearch_city_key":
	    		
	    	    System.out.println("MapSearch_city_key");

		    	{
		    		
		    	    Type typeMap = new TypeToken<List<CityMap>>(){}.getType();
		    	    List<CityMap> mapList = new Gson().fromJson(gson.toJson(responseController.mObject), typeMap);
		    	    for (int i = 0; i < mapList.size(); i++) {
		    	    	CityMap map = mapList.get(i);
						System.out.println(map.toString());
		    	    }
			    	   GUI.Main.myMapList=mapList;

		    		
		    	}

			    UI_server_communicate.mResposeFromserver = true;

	    		break;

	    	case "MapSearch_place_key":

	    	    System.out.println("MapSearch_place_key");
	    	    
	    	    {

		    	    Type typeMap = new TypeToken<List<CityMap>>(){}.getType();
		    	    List<CityMap> mapList = new Gson().fromJson(gson.toJson(responseController.mObject), typeMap);
		    	    for (int i = 0; i < mapList.size(); i++) {
		    	    	CityMap map = mapList.get(i);
						System.out.println(map.toString());
		    	    }
		    	    
		    	
	    	    }

	    	    UI_server_communicate.mResposeFromserver = true;

	    		break;

			default:
				
	    	    System.out.println("Invalid Response");

	      	}

		} catch (Exception e) {

		    System.out.println(message + " - Not a JSON");

		}

	}
	

}
