package MVC;

import com.google.gson.Gson;

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

	    		GeneralResponse generalResponseRegister = gson.fromJson(gson.toJson(response.object), GeneralResponse.class);
	    	    System.out.println("Register");
	    	    System.out.println(generalResponseRegister.isValid);
	    		
	    		break;

	    	case "MapSearch":

	    		GeneralResponse generalResponseMapSearch = gson.fromJson(gson.toJson(response.object), GeneralResponse.class);

	    	    System.out.println("MapSearch");
	    	    System.out.println(generalResponseMapSearch.isValid);
	    		
	    		break;

	    	case "AccountCheck":

	    		GeneralResponse generalResponseAccountCheck = gson.fromJson(gson.toJson(response.object), GeneralResponse.class);

	    	    System.out.println("AccountCheck");
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
