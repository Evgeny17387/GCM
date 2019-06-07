package MVC;

import java.util.List;

import com.google.gson.Gson;

import DB_classes.Map;

import MVC.Model;

import Requests.Register;
import Requests.Request;
import Requests.AccountCheck;

import Responses.Response;

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

			Response responseRegister = new Response("Register", isUserAdded);

			jsonString = gson.toJson(responseRegister);

			break;

		case "MapSearch_city_key":

			String cityName = gson.fromJson(gson.toJson(request.object), String.class);

			List<Map> mapsListCityName = operations.MapsByCity(cityName);

	    	Response responseMapSearchCityName = new Response("MapSearch_city_key", mapsListCityName);

	    	jsonString = gson.toJson(responseMapSearchCityName);

			break;

		case "MapSearch_place_key":

			String placeName = gson.fromJson(gson.toJson(request.object), String.class);

			List<Map> mapsListPlaceName = operations.MapsByPlace(placeName);

	    	Response responseMapSearchPalceName = new Response("MapSearch_place_key", mapsListPlaceName);

	    	jsonString = gson.toJson(responseMapSearchPalceName);

			break;

		case "UserCheck":

			AccountCheck accountCheckUser = gson.fromJson(gson.toJson(request.object), AccountCheck.class);

			boolean isUsersExists = operations.isValidAccount("Users", accountCheckUser.username, accountCheckUser.password);

	    	Response responseUserCheck = new Response("UserCheck", isUsersExists);

	    	jsonString = gson.toJson(responseUserCheck);

			break;

		case "WorkerCheck":

			AccountCheck accountCheckWorker = gson.fromJson(gson.toJson(request.object), AccountCheck.class);

			boolean isWorkerExists = operations.isValidAccount("Workers", accountCheckWorker.username, accountCheckWorker.password);

	    	Response responseWorkerCheck = new Response("UserCheck", isWorkerExists);

	    	jsonString = gson.toJson(responseWorkerCheck);

			break;

		default:

			jsonString = "Invalid Request";

		}

		return jsonString;

	}

}
