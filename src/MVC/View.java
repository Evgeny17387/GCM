package MVC;

import java.util.List;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import DB_classes.Map;
import DB_classes.Place;
import DB_classes.Purchase;
import DB_classes.AccountUser;
import DB_classes.AccountWorker;

import Responses.Response;
import Utils.ErrorCodes;
import Responses.AccountCheckResponse;

import GUI.UI_server_communicate;

public class View {

	public void Run(String message) {

	    try {

	    	Gson gson = new Gson();

	    	Response response = gson.fromJson(message, Response.class);

	      	switch (response.type) {

      		// Users
      	
	    	case "AddUser":

	    	    System.out.println("AddUser");

		    	{
		
		    		AccountCheckResponse accountCheckResponse = gson.fromJson(gson.toJson(response.object), AccountCheckResponse.class);

		    	    System.out.println(accountCheckResponse.mErrorCode);

		    		if (accountCheckResponse.mErrorCode == ErrorCodes.SUCCESS) {

			    		AccountUser accountUser = gson.fromJson(gson.toJson(accountCheckResponse.mAccount), AccountUser.class);

						System.out.format("%s - %s - %s - %s - %s - %s - %d\n", accountUser.mFirstName, accountUser.mLastName, accountUser.mPassword, accountUser.mEmail, accountUser.mPhoneNumber, accountUser.mUserName, accountUser.mPurchases);

		    		}else if (accountCheckResponse.mErrorCode == ErrorCodes.USER_ALREADY_EXISTS) {
		    			
			    	    System.out.println("User with this userName already exists");

		    		}else if (accountCheckResponse.mErrorCode == ErrorCodes.USER_DETAILS_MISSING) {
	    			
		    			System.out.println("One or more registration details are missing");
	    			
		    		}

		    	}

    	    	break;

	    	case "GetUser":

	    	    System.out.println("GetUser");

		    	{
		    				    		
		    		AccountCheckResponse accountCheckResponse = gson.fromJson(gson.toJson(response.object), AccountCheckResponse.class);

		    	    System.out.println(accountCheckResponse.mErrorCode);

		    		if (accountCheckResponse.mErrorCode == ErrorCodes.SUCCESS) {
		    			
			    		AccountUser accountUser = gson.fromJson(gson.toJson(accountCheckResponse.mAccount), AccountUser.class);

						System.out.format("%s - %s - %s - %s - %s - %s - %d\n", accountUser.mFirstName, accountUser.mLastName, accountUser.mPassword, accountUser.mEmail, accountUser.mPhoneNumber, accountUser.mUserName, accountUser.mPurchases);

		    		} else if (accountCheckResponse.mErrorCode == ErrorCodes.USER_NOT_FOUND) {

		    			System.out.println("User not found");
		    			
		    		} else if (accountCheckResponse.mErrorCode == ErrorCodes.USER_DETAILS_MISSING) {
		    			
		    			System.out.println("UserName or Password are missing");
	    			
		    		}
			    		
		    	}

	    	    break;

	    	case "UpdateUser":
	    		
	    	    System.out.println("UpdateUser");

		    	{
		
		    		AccountCheckResponse accountCheckResponse = gson.fromJson(gson.toJson(response.object), AccountCheckResponse.class);

		    	    System.out.println(accountCheckResponse.mErrorCode);

		    		if (accountCheckResponse.mErrorCode == ErrorCodes.SUCCESS) {

			    		AccountUser accountUser = gson.fromJson(gson.toJson(accountCheckResponse.mAccount), AccountUser.class);

						System.out.format("%s - %s - %s - %s - %s - %s - %d\n", accountUser.mFirstName, accountUser.mLastName, accountUser.mPassword, accountUser.mEmail, accountUser.mPhoneNumber, accountUser.mUserName, accountUser.mPurchases);

		    		}else if (accountCheckResponse.mErrorCode == ErrorCodes.USER_DETAILS_MISSING) {
	    			
		    			System.out.println("One or more user details are missing");
	    			
		    		}

		    	}

    	    	break;

    	    // Workers

	    	case "GetWorker":

	    	    System.out.println("GetWorker");

		    	{
		    		
		    		AccountCheckResponse accountCheckResponse = gson.fromJson(gson.toJson(response.object), AccountCheckResponse.class);
	
		    	    System.out.println(accountCheckResponse.mErrorCode);

		    		if (accountCheckResponse.mErrorCode == ErrorCodes.SUCCESS) {
		    			
		    			AccountWorker accountWorker = gson.fromJson(gson.toJson(accountCheckResponse.mAccount), AccountWorker.class);

						System.out.format("%s - %s - %s - %s - %s - %d - %s\n", accountWorker.mFirstName, accountWorker.mLastName, accountWorker.mPassword, accountWorker.mEmail, accountWorker.mPhoneNumber, accountWorker.mId, accountWorker.mType);

		    		} else if (accountCheckResponse.mErrorCode == ErrorCodes.USER_NOT_FOUND) {

		    			System.out.println("Worker not found");
		    			
		    		}

		    	}

	    	    break;

	    	case "GetUsersPurchases":

	    	    System.out.println("GetUsersPurchases");

		    	{

		    		AccountCheckResponse accountCheckResponse = gson.fromJson(gson.toJson(response.object), AccountCheckResponse.class);

		    	    System.out.println(accountCheckResponse.mErrorCode);

		    		if (accountCheckResponse.mErrorCode == ErrorCodes.SUCCESS) {

			    	    Type type = new TypeToken<List<Purchase>>(){}.getType();

			    	    List<Purchase> purchaseList = new Gson().fromJson(gson.toJson(accountCheckResponse.mAccount), type);

			    	    for (int i = 0; i < purchaseList.size(); i++) {
			    	    	Purchase purchase = purchaseList.get(i);
							System.out.println(purchase.toString());
			    	    }
		    			
		    		} else if (accountCheckResponse.mErrorCode == ErrorCodes.USER_NOT_FOUND) {

		    			System.out.println("Worker is not found");

		    		} else if (accountCheckResponse.mErrorCode == ErrorCodes.WORKER_NOT_MANAGER) {

		    			System.out.println("Worker is not manager");

		    		}

		    	}

	    	    break;

	    	case "MapSearch_city_key":

	    	    System.out.println("MapSearch_city_key");

		    	{
		    		
		    	    Type typeMap = new TypeToken<List<Map>>(){}.getType();
		    	    List<Map> mapList = new Gson().fromJson(gson.toJson(response.object), typeMap);
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
		    	    List<Map> mapList = new Gson().fromJson(gson.toJson(response.object), typeMap);
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
