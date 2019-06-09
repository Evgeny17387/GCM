package MVC;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import Constants.API;
import Constants.ErrorCodes;

import DB_classes.AccountUser;

import Requests.Request;
import Responses.ResponseController;
import Responses.ResponseModel;
import Requests.GeneralRequest;

import com.google.gson.Gson;

class ControllerModelTest {

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
