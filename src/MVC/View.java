package MVC;

import java.util.List;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import DB_classes.CityMap;
import DB_classes.Purchase;
import DB_classes.Purchases;
import Defines.API;
import Defines.ErrorCodes;
import GUI.Main;
import DB_classes.AccountUser;
import DB_classes.AccountWorker;

import Responses.ResponseController;
import Responses.ResponseModel;
import Utils.UI_server_communicate;

public class View {

	public void Run(String message) {

	    try {

	    	Gson gson = new Gson();

	    	ResponseController responseController = gson.fromJson(message, ResponseController.class);

    		ResponseModel responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);

			GUI.Main.mServerResponseErrorCode = responseModel.mErrorCode;

    	    System.out.println(responseModel.mErrorCode);

	      	switch (responseController.mType) {

      		// Users

	    	case API.ADD_USER:

	    	    System.out.println("View: " + API.ADD_USER);

		    	{
		
		    		GUI.Main.mServerResponseErrorCode = responseModel.mErrorCode;

		    	}

			    UI_server_communicate.mResposeFromserver = true;

    	    	break;

	    	case API.GET_USER:
	    	case API.UPDATE_USER:

	    	    System.out.println("View: " + responseController.mType);

		    	{

		    		if (responseModel.mErrorCode == ErrorCodes.SUCCESS) {
		    			
		    			AccountUser accountUser = gson.fromJson(gson.toJson(responseModel.mObject), AccountUser.class);
		    			
		    			Main.mAccountUser = accountUser;

		    		}

		    	}

			    UI_server_communicate.mResposeFromserver = true;

	    	    break;

        	// Purchases

	    	case API.BUY_MAP:

	    	    System.out.println("View: " + API.BUY_MAP);

		    	{

		    		if (responseModel.mErrorCode == ErrorCodes.SUCCESS) {
		    		
		    		}

		    	}

			    UI_server_communicate.mResposeFromserver = true;

	    	    break;

    	    // Workers

	    	case API.GET_WORKER:

	    	    System.out.println("View: " + API.GET_WORKER);

		    	{

		    		if (responseModel.mErrorCode == ErrorCodes.SUCCESS) {
		    			
		    			AccountWorker accountWorker = gson.fromJson(gson.toJson(responseModel.mObject), AccountWorker.class);

		    			Main.mAccountWorker = accountWorker;

						System.out.println("View: " + accountWorker.toString());
		    			
		    		}

		    	}

			    UI_server_communicate.mResposeFromserver = true;

	    	    break;

	    	case API.GET_USERS_PURCHASES:

	    	    System.out.println("View: " + API.GET_USERS_PURCHASES);

		    	{

		    		if (responseModel.mErrorCode == ErrorCodes.SUCCESS) {

			    	    Type type = new TypeToken<List<Purchases>>(){}.getType();
			    	    List<Purchases> purchasesList = new Gson().fromJson(gson.toJson(responseModel.mObject), type);

			    	    Main.mPurchases = purchasesList;
			    	    
						System.out.println(purchasesList.toString());

		    		} else if (responseModel.mErrorCode == ErrorCodes.WORKER_NOT_MANAGER) {

		    			System.out.println("Worker is not a manager");
		    			
		    		}

		    	}

			    UI_server_communicate.mResposeFromserver = true;

	    	    break;

	    	// Map Search

	    	case "MapSearch_city_key":
	    		
	    	    System.out.println("View: " + "MapSearch_city_key");

		    	{
		    		
		    	    Type typeMap = new TypeToken<List<CityMap>>(){}.getType();
		    	    List<CityMap> mapList = new Gson().fromJson(gson.toJson(responseModel.mObject), typeMap);
		    	    for (int i = 0; i < mapList.size(); i++) {
		    	    	CityMap map = mapList.get(i);
						System.out.println(map.toString());
		    	    }
			    	   GUI.Main.myMapList=mapList;

		    		
		    	}

			    UI_server_communicate.mResposeFromserver = true;

	    		break;

	    	case "MapSearch_place_key":

	    	    System.out.println("View: " + "MapSearch_place_key");
	    	    
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
