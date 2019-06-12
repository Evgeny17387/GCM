package MVC;

import java.lang.reflect.Type;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import DB_classes.AccountUser;
import DB_classes.CityMap;
import DB_classes.Route;
import Defines.API;
import Defines.ErrorCodes;
import Requests.Request;
import Responses.ResponseController;
import Responses.ResponseModel;
import Requests.GeneralRequest;
import Requests.GetRoutesRequest;
import Requests.ProposeNewPriceRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class ControllerModelTest {

	// Scenario: GetRoutes

	@Test
	void testScenario3() {

		String routeName;
		
		String jsonRequest;
    	String jsonController;

		ResponseController responseController;
		ResponseModel responseModel;	

		Request request;
		GetRoutesRequest getRoutesRequest;

		List<Route> routes;
		
		Controller controller = new Controller();
//		Model model = new Model();
		Gson gson = new Gson();

		// ClearTable

//		Assert.assertTrue(model.ClearTable("PriceChange") == ErrorCodes.SUCCESS);

		// GetRoutes
		
		routeName = "1";

		getRoutesRequest = new GetRoutesRequest(routeName);
		request = new Request(API.GET_ROUTES, getRoutesRequest);
		jsonRequest = gson.toJson(request);

    	jsonController = controller.Run(jsonRequest);
    	
    	responseController = gson.fromJson(jsonController, ResponseController.class);
//		responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);
	    Type type = new TypeToken<List<Route>>(){}.getType();
    	routes = gson.fromJson(gson.toJson(responseController.mObject), type);

    	System.out.println(routes);
    	
//		Assert.assertTrue(responseModel.mErrorCode == ErrorCodes.SUCCESS);

	}

	// Scenario: ChangePrice, ApprovePrice

	@Test
	void testScenario2() {

		String mapName;
		int proposedPrice;
		
		String jsonRequest;
    	String jsonController;

		ResponseController responseController;
		ResponseModel responseModel;	

		Request request;
		ProposeNewPriceRequest proposedNewPriceRequest;

		Controller controller = new Controller();
		Model model = new Model();
		Gson gson = new Gson();

		// ClearTable

		Assert.assertTrue(model.ClearTable("PriceChange") == ErrorCodes.SUCCESS);

		// ProposeNewPrice
		
		mapName = "1";
		proposedPrice = 4;

		proposedNewPriceRequest = new ProposeNewPriceRequest(mapName, proposedPrice);
		request = new Request(API.PROPOSE_NEW_PRICE, proposedNewPriceRequest);
		jsonRequest = gson.toJson(request);

    	jsonController = controller.Run(jsonRequest);
    	
    	responseController = gson.fromJson(jsonController, ResponseController.class);
		responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);

		Assert.assertTrue(responseModel.mErrorCode == ErrorCodes.SUCCESS);

		// ProposeNewPrice

		proposedNewPriceRequest = new ProposeNewPriceRequest(mapName, proposedPrice);
		request = new Request(API.APPROVE_PROPOSED_PRICE, proposedNewPriceRequest);
		jsonRequest = gson.toJson(request);

    	jsonController = controller.Run(jsonRequest);
    	
    	responseController = gson.fromJson(jsonController, ResponseController.class);
		responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);

		Assert.assertTrue(responseModel.mErrorCode == ErrorCodes.SUCCESS);

	}

	// Scenario: AddUser, BuySubscription, GetUser, DeleteSubscription, GetUser

	@Test
	void testScenario1() {

		String firstName;
		String lastName;
		String password;
		String email;
		String phoneNumber;
		String userName;
		String creditCard;
		String subscription;
		
		String jsonRequest;
    	String jsonController;

		AccountUser accountUser;
		AccountUser accountUserResponse;

		ResponseController responseController;
		ResponseModel responseModel;	

		Request request;
		GeneralRequest generalRequest;

		Controller controller = new Controller();
		Model model = new Model();
		Gson gson = new Gson();

		// ClearTable

		Assert.assertTrue(model.ClearTable("Users") == ErrorCodes.SUCCESS);
		Assert.assertTrue(model.ClearTable("Purchases") == ErrorCodes.SUCCESS);

		// AddUser

		firstName = "firstName";
		lastName = "lastName";
		password = "password";
		email = "email";
		phoneNumber = "phoneNumber";
		userName = "userName";
		creditCard = "creditCard";

		accountUser = new AccountUser(firstName, lastName, password, email, phoneNumber, userName, creditCard);
		request = new Request(API.ADD_USER, accountUser);
		jsonRequest = gson.toJson(request);

    	jsonController = controller.Run(jsonRequest);

    	responseController = gson.fromJson(jsonController, ResponseController.class);
		responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);

    	Assert.assertTrue(responseModel.mErrorCode == ErrorCodes.SUCCESS);

		accountUserResponse = gson.fromJson(gson.toJson(responseModel.mObject), AccountUser.class);

		subscription = "NO";
		accountUser.mSubscription = subscription;
		
    	Assert.assertTrue(accountUserResponse.equals(accountUser));

	    // Buy Subscription

	    generalRequest = new GeneralRequest(userName, password);
		request = new Request(API.BUY_SUBSCRITION, generalRequest);
		jsonRequest = gson.toJson(request);
    	jsonController = controller.Run(jsonRequest);

    	responseController = gson.fromJson(jsonController, ResponseController.class);
		responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);

    	Assert.assertTrue(responseModel.mErrorCode == ErrorCodes.SUCCESS);

    	subscription = (String)responseModel.mObject;
		accountUser.mSubscription = subscription;

	    // GetUser

		generalRequest = new GeneralRequest(userName, password);
    	request = new Request(API.GET_USER, generalRequest);
    	jsonRequest = gson.toJson(request);
    	jsonController = controller.Run(jsonRequest);

    	responseController = gson.fromJson(jsonController, ResponseController.class);
		responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);

    	Assert.assertTrue(responseModel.mErrorCode == ErrorCodes.SUCCESS);

		accountUserResponse = gson.fromJson(gson.toJson(responseModel.mObject), AccountUser.class);

    	Assert.assertTrue(accountUserResponse.equals(accountUser));

	    // Delete Subscription

	    generalRequest = new GeneralRequest(userName, password);
		request = new Request(API.DELETE_SUBSCRITION, generalRequest);
		jsonRequest = gson.toJson(request);
    	jsonController = controller.Run(jsonRequest);

    	responseController = gson.fromJson(jsonController, ResponseController.class);
		responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);

    	Assert.assertTrue(responseModel.mErrorCode == ErrorCodes.SUCCESS);

    	subscription = "NO";

    	Assert.assertTrue(((String)responseModel.mObject).equals(subscription));

		accountUser.mSubscription = subscription;

	    // GetUser

		generalRequest = new GeneralRequest(userName, password);
    	request = new Request(API.GET_USER, generalRequest);
    	jsonRequest = gson.toJson(request);
    	jsonController = controller.Run(jsonRequest);

    	responseController = gson.fromJson(jsonController, ResponseController.class);
		responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);

    	Assert.assertTrue(responseModel.mErrorCode == ErrorCodes.SUCCESS);

		accountUserResponse = gson.fromJson(gson.toJson(responseModel.mObject), AccountUser.class);

    	Assert.assertTrue(accountUserResponse.equals(accountUser));

	}

}
