package MVC;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import Constants.API;
import Constants.ErrorCodes;
import Constants.PurchaseType;

import DB_classes.AccountUser;

import Requests.Request;
import Requests.AccountCheckRequest;
import Requests.BuyMapRequest;

import com.google.gson.Gson;

class ControllerModelTest {

	// Tests: users add, add - no duplicate username, get, update

	@Test
	void testScenario1() {

		String firstName;
		String lastName;
		String password;
		String email;
		String phoneNumber;
		String userName;
		String creditCard;
		
		String jsonTest;
		String jsonRequest;
    	String jsonController;

		AccountUser accountUser;

		Request request;

		Controller controller = new Controller();
		Model model = new Model();
		Gson gson = new Gson();

		// ClearTable

		Assert.assertTrue(model.ClearTable("Users") == ErrorCodes.SUCCESS);

		// AddUser

	    jsonTest = "{\"type\":\"AddUser\",\"object\":{\"mErrorCode\":0,\"mAccount\":{\"mUserName\":\"userName\",\"mCreditCard\":\"creditCard\",\"mPurchases\":0,\"mFirstName\":\"firstName\",\"mLastName\":\"lastName\",\"mPassword\":\"password\",\"mEmail\":\"email\",\"mPhoneNumber\":\"phoneNumber\"}}}";

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

	    Assert.assertTrue(jsonController.equals(jsonTest));

	    System.out.println(jsonController);

		// AddUser - already registered - should fail

	    jsonTest = "{\"type\":\"AddUser\",\"object\":{\"mErrorCode\":1062}}";
	    
    	jsonController = controller.Run(jsonRequest);

	    Assert.assertTrue(jsonController.equals(jsonTest));

	    System.out.println(jsonController);

		// GetUser

	    jsonTest = "{\"type\":\"GetUser\",\"object\":{\"mErrorCode\":0,\"mAccount\":{\"mUserName\":\"userName\",\"mCreditCard\":\"creditCard\",\"mPurchases\":0,\"mFirstName\":\"firstName\",\"mLastName\":\"lastName\",\"mPassword\":\"password\",\"mEmail\":\"email\",\"mPhoneNumber\":\"phoneNumber\"}}}";

    	AccountCheckRequest accountCheckRequest = new AccountCheckRequest(userName, password);

    	request = new Request(API.GET_USER, accountCheckRequest);

    	jsonRequest = gson.toJson(request);

    	jsonController = controller.Run(jsonRequest);

	    Assert.assertTrue(jsonController.equals(jsonTest));

	    System.out.println(jsonController);

		// UpdateUser

	    jsonTest = "{\"type\":\"UpdateUser\",\"object\":{\"mErrorCode\":0,\"mAccount\":{\"mUserName\":\"userName\",\"mCreditCard\":\"creditCard1\",\"mPurchases\":0,\"mFirstName\":\"firstName1\",\"mLastName\":\"lastName1\",\"mPassword\":\"password1\",\"mEmail\":\"email1\",\"mPhoneNumber\":\"phoneNumber1\"}}}";

		firstName = "firstName1";
		lastName = "lastName1";
		password = "password1";
		email = "email1";
		phoneNumber = "phoneNumber1";
		userName = "userName";
		creditCard = "creditCard1";

		accountUser = new AccountUser(firstName, lastName, password, email, phoneNumber, userName, creditCard);

		request = new Request(API.UPDATE_USER, accountUser);

		jsonRequest = gson.toJson(request);

    	jsonController = controller.Run(jsonRequest);

	    Assert.assertTrue(jsonController.equals(jsonTest));

	    System.out.println(jsonController);

	}

	// Tests: buy map, manager worker sees all purchases

	@Test
	void testScenario2() {

		String firstName = "1";
		String password = "1";

		String userName;
		String cityName;
		String type;

		String jsonTest;
		String jsonRequest;
    	String jsonController;

    	AccountCheckRequest accountCheckRequest;

		Request request;

		Controller controller = new Controller();
		Model model = new Model();
		Gson gson = new Gson();

		// ClearTable

		Assert.assertTrue(model.ClearTable("Purchases") == ErrorCodes.SUCCESS);

		// By map

	    jsonTest = "";

		userName = "1";
		cityName = "1";
		type = PurchaseType.ONE_TIME;

	    BuyMapRequest buyMapRequest = new BuyMapRequest(userName, cityName, type);

    	request = new Request(API.BUY_MAP, buyMapRequest);

    	jsonRequest = gson.toJson(request);

    	jsonController = controller.Run(jsonRequest);

//	    Assert.assertTrue(jsonController.equals(jsonTest));

	    System.out.println(jsonController);

		// Show all purchases

	    jsonTest = "";

	    accountCheckRequest = new AccountCheckRequest(firstName, password);

    	request = new Request(API.GET_USER_PURCHASES, accountCheckRequest);

    	jsonRequest = gson.toJson(request);

    	jsonController = controller.Run(jsonRequest);

//	    Assert.assertTrue(jsonController.equals(jsonTest));

	    System.out.println(jsonController);

	}

}
