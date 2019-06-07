package MVC;

import java.util.List;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import DB_classes.Map;
import DB_classes.Place;

import Responses.Response;

import GUI.UI_server_communicate;

public class View {

	public void Run(String message) {

	    try {

	    	Gson gson = new Gson();

	    	Response response = gson.fromJson(message, Response.class);

	      	switch (response.type) {

		    	case "Register":
	
		    	    System.out.println("Register");
	
		    		boolean isRegistered = gson.fromJson(gson.toJson(response.object), boolean.class);
	
		    		System.out.println(isRegistered);
		    		
		    		break;
	
		    	case "MapSearch_city_key":
	
		    	    System.out.println("MapSearch_city_key");
	
		    	    Type typeMap1 = new TypeToken<List<Map>>(){}.getType();
		    	    List<Map> mapList1 = new Gson().fromJson(gson.toJson(response.object), typeMap1);
		    	    for (int i = 0; i < mapList1.size(); i++) {
		    	    	Map map = mapList1.get(i);
						System.out.format("%s - %s - %d - %s\n", map.mName, map.mCity, map.mVersion, map.mDescription);
						for (Place place : map.mPlaces) {
							System.out.format("%s - %s\n", place.mName, place.mClassification);
						}
		    	    }
	
		    	    UI_server_communicate.mResposeFromserver = true;
	
		    		break;
	
		    	case "MapSearch_place_key":
	
		    	    System.out.println("MapSearch_place_key");
	
		    	    Type typeMap2 = new TypeToken<List<Map>>(){}.getType();
		    	    List<Map> mapList2 = new Gson().fromJson(gson.toJson(response.object), typeMap2);
		    	    for (int i = 0; i < mapList2.size(); i++) {
		    	    	Map map = mapList2.get(i);
						System.out.format("%s - %s - %d - %s\n", map.mName, map.mCity, map.mVersion, map.mDescription);
						for (Place place : map.mPlaces) {
							System.out.format("%s - %s\n", place.mName, place.mClassification);
						}
		    	    }
	
		    	    UI_server_communicate.mResposeFromserver = true;
	
		    		break;
	
		    	case "UserCheck":

		    	    System.out.println("UserCheck");

			    	{
			    				    		
			    	    boolean isValid = gson.fromJson(gson.toJson(response.object), boolean.class);
		
			    	    System.out.println(isValid);
				    		
			    	}

	    	    	UI_server_communicate.mResposeFromserver = true;

		    	    break;

		    	case "WorkerCheck":

		    	    System.out.println("WorkerCheck");

			    	{
			    		
			    	    boolean isValid = gson.fromJson(gson.toJson(response.object), boolean.class);
		
			    	    System.out.println(isValid);
	
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
