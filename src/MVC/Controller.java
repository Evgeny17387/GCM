package MVC;

import java.util.List;

import com.google.gson.Gson;

import DB_classes.Map;
import Requests.Register;
import Requests.Request;
import Requests.GeneralRequest;
import Requests.AccountCheck;

import MVC.Model;

import Responses.Response;
import Responses.GeneralResponse;

public class Controller {

	public String control(String msg) {

    	Gson gson = new Gson();

    	Request request = gson.fromJson(msg.toString(), Request.class);

	  	Model operations = new Model();

	  	String jsonString = "";

	  	switch (request.type) {

		case "Register":

			Register register = gson.fromJson(gson.toJson(request.object), Register.class);
			
			boolean isUserAdded = operations.AddUser(register.name, register.password, register.email, register.creditCard);

			GeneralResponse generalResponseRegister = new GeneralResponse(isUserAdded);
	    	Response responseRegister = new Response("Register", generalResponseRegister);
	    	jsonString = gson.toJson(responseRegister);

			break;

		case "MapSearch_city_key":

			GeneralRequest generalRequest = gson.fromJson(gson.toJson(request.object), GeneralRequest.class);

			List<Map> mapsList = operations.MapsByCity(generalRequest.name);

	    	Response responseMapSearch = new Response("MapSearch_city_key", mapsList);

	    	jsonString = gson.toJson(responseMapSearch);

			break;

		case "AccountCheck":

			AccountCheck accountCheck = gson.fromJson(gson.toJson(request.object), AccountCheck.class);

			boolean isUsersExists = operations.isValidUser(accountCheck.username, accountCheck.password);

			GeneralResponse generalResponseAccountCheck = new GeneralResponse(isUsersExists);
	    	Response responseAccountCheck = new Response("AccountCheck", generalResponseAccountCheck);
	    	jsonString = gson.toJson(responseAccountCheck);

			break;

		default:

			jsonString = "Invalid Request";

		}

		return jsonString;

	}

}
