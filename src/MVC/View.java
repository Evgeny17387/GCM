package MVC;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import DB_classes.Map;

import Responses.GeneralResponse;
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

	    		GeneralResponse generalResponseRegister = gson.fromJson(gson.toJson(response.object), GeneralResponse.class);

	    		System.out.println(generalResponseRegister.isValid);
	    		
	    		break;

	    	case "MapSearch_city_key":

	    	    System.out.println("MapSearch_city_key");

	    	    Type type = new TypeToken<List<Map>>(){}.getType();
	    	    List<Map> inpList = new Gson().fromJson(gson.toJson(response.object), type);
	    	    for (int i = 0; i < inpList.size(); i++) {
	    	    	Map map = inpList.get(i);
					System.out.format("%s - %s - %d - %s\n", map.mName, map.mCity, map.mVersion, map.mDescription);
	    	    }

	    	    UI_server_communicate.mResposeFromserver = true;

	    		break;

	    	case "AccountCheck":

	    	    System.out.println("AccountCheck");

	    		GeneralResponse generalResponseAccountCheck = gson.fromJson(gson.toJson(response.object), GeneralResponse.class);

	    	    System.out.println(generalResponseAccountCheck.isValid);

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
