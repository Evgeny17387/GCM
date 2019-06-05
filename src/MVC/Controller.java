package MVC;

import com.google.gson.Gson;

import Requests.Message;
import Requests.Register;
import Requests.Request;
import Requests.SearchMap;

import MVC.Model;

import Responses.Response;
import Responses.UserCheck;

public class Controller {

	public String control(String msg) {

    	Gson gson = new Gson();

    	Request request = gson.fromJson(msg.toString(), Request.class);

	  	Model operations = new Model();

	  	String jsonString = "";

	  	switch (request.type) {

		case 1:

			Message message = gson.fromJson(gson.toJson(request.object), Message.class);

			switch (message.command) {

			case "0":
			
				operations.PrintUsers();
				
				break;

			case "1":
				
				if (operations.isValidUser(message.name, message.password)) {

					jsonString = "Purcheses - " + String.valueOf(operations.GetPurchases(message.name));

				} else {

					jsonString = "Invalid username or password";

				}
				
				break;
				
			case "2":
				
				if (operations.isValidUser(message.name, message.password)) {

					operations.IncreasePurchases(message.name);

				} else {

					jsonString = "Invalid username or password";

				}
				
				break;

			default:
					
				jsonString = "Invalid Command";
		
			}

			break;

		case 2:

			Register register = gson.fromJson(gson.toJson(request.object), Register.class);
			
			operations.AddUser(register.name, register.password, register.email, register.creditCard);

			break;

		case 3:

			SearchMap searchMap = gson.fromJson(gson.toJson(request.object), SearchMap.class);
			
			boolean isUserValid = operations.isMapExists(searchMap.name);

	    	UserCheck userCheck = new UserCheck(isUserValid);
	    	Response response = new Response("UserCheck", userCheck);
	    	jsonString = gson.toJson(response);

			break;

		}
		
		return jsonString;

	}
	
}
