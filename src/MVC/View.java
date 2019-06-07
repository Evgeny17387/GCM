package MVC;

import java.util.List;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import DB_classes.Map;
import DB_classes.Place;
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

		    	case "Register":
	
		    	    System.out.println("Register");

			    	{
			
			    		AccountCheckResponse accountCheckResponse = gson.fromJson(gson.toJson(response.object), AccountCheckResponse.class);

			    	    System.out.println(accountCheckResponse.mErrorCode);

			    		if (accountCheckResponse.mErrorCode == ErrorCodes.SUCCESS) {
			    			
				    		AccountUser accountUser = gson.fromJson(gson.toJson(accountCheckResponse.mAccount), AccountUser.class);

							System.out.format("%d - %s - %s - %d - %s - %s\n", accountUser.mId,  accountUser.mName, accountUser.mPassword, accountUser.mPurchases, accountUser.mEmail, accountUser.mCreditCard);

			    		}else if (accountCheckResponse.mErrorCode == ErrorCodes.USER_ALREADY_EXISTS) {
			    			
				    	    System.out.println("User with this name already exists");
			    			
			    		}
			    				    		
			    	}

	    	    	UI_server_communicate.mResposeFromserver = true;

	    	    	break;

		    	case "UserCheck":

		    	    System.out.println("UserCheck");

			    	{
			    				    		
			    		AccountCheckResponse accountCheckResponse = gson.fromJson(gson.toJson(response.object), AccountCheckResponse.class);

			    	    System.out.println(accountCheckResponse.mErrorCode);

			    		if (accountCheckResponse.mErrorCode == ErrorCodes.SUCCESS) {
			    			
				    		AccountUser accountUser = gson.fromJson(gson.toJson(accountCheckResponse.mAccount), AccountUser.class);

							System.out.format("%d - %s - %s - %d - %s - %s\n", accountUser.mId,  accountUser.mName, accountUser.mPassword, accountUser.mPurchases, accountUser.mEmail, accountUser.mCreditCard);

			    		}
				    		
			    	}

	    	    	UI_server_communicate.mResposeFromserver = true;

		    	    break;

		    	case "WorkerCheck":

		    	    System.out.println("WorkerCheck");

			    	{
			    		
			    		AccountCheckResponse accountCheckResponse = gson.fromJson(gson.toJson(response.object), AccountCheckResponse.class);
		
			    	    System.out.println(accountCheckResponse.mErrorCode);

			    		if (accountCheckResponse.mErrorCode == ErrorCodes.SUCCESS) {
			    			
			    			AccountWorker accountWorker = gson.fromJson(gson.toJson(accountCheckResponse.mAccount), AccountWorker.class);

							System.out.format("%d - %s - %s - %s\n", accountWorker.mId,  accountWorker.mName, accountWorker.mPassword, accountWorker.mType);

			    		}

			    	}

		    		UI_server_communicate.mResposeFromserver = true;

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
	
		    	    UI_server_communicate.mResposeFromserver = true;
	
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
