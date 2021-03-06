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

public class View {

	public void Run(String message) {

	    try {

	    	Gson gson = new Gson();

	    	ResponseController responseController = gson.fromJson(message, ResponseController.class);

    		ResponseModel responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);

			GUI.Main.mServerResponseErrorCode = responseModel.mErrorCode;

    	    System.out.println("View: " + responseController.mType);

    	    System.out.println("View: " + responseModel.mErrorCode);

	      	switch (responseController.mType) {

      		// Users

	    	case API.ADD_USER:

			    Main.mResposeFromserver = true;

    	    	break;
    	    
	    
	    	case API.UPDATE_MAP:
	    		responseModel.mErrorCode = ErrorCodes.SUCCESS;
	    	    Main.mResposeFromserver = true;

    	    break;
	    	case API.ADD_PLACE:
	    		responseModel.mErrorCode = ErrorCodes.SUCCESS;
	    	    Main.mResposeFromserver = true;
	    	    break;
	    		
	    	case API.ADD_MAP:
	    		responseModel.mErrorCode = ErrorCodes.SUCCESS;
	    	    Main.mResposeFromserver = true;
	    	    break;
	    	   
	    		
	    	case API.UPDATE_PLACE:
	    		responseModel.mErrorCode = ErrorCodes.SUCCESS;
	    	    Main.mResposeFromserver = true;

    	    break;
	    	case API.GET_USER:
	    	case API.UPDATE_USER:

		    	{

		    		if (responseModel.mErrorCode == ErrorCodes.SUCCESS) {
		    			
		    			AccountUser accountUser = gson.fromJson(gson.toJson(responseModel.mObject), AccountUser.class);
		    			
		    			Main.mAccountUser = accountUser;

		    		}

		    	}

		    	Main.mResposeFromserver = true;

	    	    break;

        	// Purchases

	    	case API.BUY:

		    	{

		    		if (responseModel.mErrorCode == ErrorCodes.SUCCESS) {
		    			
		    			Purchase purchase = gson.fromJson(gson.toJson(responseModel.mObject), Purchase.class);
		    			
		    			Main.mAccountUser.mPurchases.add(purchase);
		    			
		    		}

		    	}

		    	Main.mResposeFromserver = true;

	    	    break;

    	    // Workers

	    	case API.GET_WORKER:

		    	{

		    		if (responseModel.mErrorCode == ErrorCodes.SUCCESS) {
		    			
		    			AccountWorker accountWorker = gson.fromJson(gson.toJson(responseModel.mObject), AccountWorker.class);

		    			Main.mAccountWorker = accountWorker;

						System.out.println("View: " + accountWorker.toString());
		    			
		    		}

		    	}

		    	Main.mResposeFromserver = true;

	    	    break;

	    	case API.GET_USERS_PURCHASES:

		    	{

		    		if (responseModel.mErrorCode == ErrorCodes.SUCCESS) {

			    	    Type type = new TypeToken<List<Purchases>>(){}.getType();
			    	    List<Purchases> purchasesList = new Gson().fromJson(gson.toJson(responseModel.mObject), type);

			    	    Main.mPurchases = purchasesList;
			    	    
						System.out.println(purchasesList.toString());
		    			
		    		}

		    	}

		    	Main.mResposeFromserver = true;

	    	    break;

	    	// Map Search

	    	case API.SEARCH_BY_CITY:

		    	{
		    		
		    	    Type typeMap = new TypeToken<List<CityMap>>(){}.getType();
		    	    List<CityMap> mapList = new Gson().fromJson(gson.toJson(responseModel.mObject), typeMap);

		    	    GUI.Main.myMapList = mapList;
		    		
		    	}

		    	Main.mResposeFromserver = true;

	    		break;

	    	case API.SEARCH_BY_PLACE:
	    	    
	    	    {

		    	    Type typeMap = new TypeToken<List<CityMap>>(){}.getType();
		    	    List<CityMap> mapList = new Gson().fromJson(gson.toJson(responseModel.mObject), typeMap);

		    	    GUI.Main.myMapList = mapList;

	    	    }

	    	    Main.mResposeFromserver = true;

	    		break;

			default:
				
	    	    System.out.println("Invalid Response");

	      	}

		} catch (Exception e) {

		    System.out.println(message + " - Not a JSON");

		}

	}
	

}
