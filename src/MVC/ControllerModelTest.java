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

	// Tests: users add, add - no duplicate username, get, update, buy maps, show all maps

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
		AccountCheckRequest accountCheckRequest;
		BuyMapRequest buyMapRequest;

		String cityName;
		String type;

		Controller controller = new Controller();
		Model model = new Model();
		Gson gson = new Gson();

		// ClearTable

		Assert.assertTrue(model.ClearTable("Users") == ErrorCodes.SUCCESS);
		Assert.assertTrue(model.ClearTable("Purchases") == ErrorCodes.SUCCESS);

		// AddUser

	    jsonTest = "{\"mType\":\"AddUser\",\"mObject\":{\"mErrorCode\":0,\"mObject\":{\"mUserName\":\"userName\",\"mCreditCard\":\"creditCard\",\"mPurchases\":[],\"mFirstName\":\"firstName\",\"mLastName\":\"lastName\",\"mPassword\":\"password\",\"mEmail\":\"email\",\"mPhoneNumber\":\"phoneNumber\"}}}";
	    
		firstName = "firstName";
		lastName = "lastName";
		password = "password";
		email = "email";
		phoneNumber = "phoneNumber";
		userName = "userName";
		creditCard = "creditCard";

		accountUser = new AccountUser(firstName, lastName, password, email, phoneNumber, userName, creditCard, null);

		request = new Request(API.ADD_USER, accountUser);

		jsonRequest = gson.toJson(request);

    	jsonController = controller.Run(jsonRequest);

	    System.out.println(jsonController);

	    Assert.assertTrue(jsonController.equals(jsonTest));

		// AddUser - already registered - should fail

	    jsonTest = "{\"mType\":\"AddUser\",\"mObject\":{\"mErrorCode\":1062}}";
	    
    	jsonController = controller.Run(jsonRequest);

	    System.out.println(jsonController);

	    Assert.assertTrue(jsonController.equals(jsonTest));

		// GetUser

	    jsonTest = "{\"mType\":\"GetUser\",\"mObject\":{\"mErrorCode\":0,\"mObject\":{\"mUserName\":\"userName\",\"mCreditCard\":\"creditCard\",\"mPurchases\":[],\"mFirstName\":\"firstName\",\"mLastName\":\"lastName\",\"mPassword\":\"password\",\"mEmail\":\"email\",\"mPhoneNumber\":\"phoneNumber\"}}}";

    	accountCheckRequest = new AccountCheckRequest(userName, password);

    	request = new Request(API.GET_USER, accountCheckRequest);

    	jsonRequest = gson.toJson(request);

    	jsonController = controller.Run(jsonRequest);

	    System.out.println(jsonController);

	    Assert.assertTrue(jsonController.equals(jsonTest));

		// UpdateUser

	    jsonTest = "{\"mType\":\"UpdateUser\",\"mObject\":{\"mErrorCode\":0,\"mObject\":{\"mUserName\":\"userName\",\"mCreditCard\":\"creditCard1\",\"mPurchases\":[],\"mFirstName\":\"firstName1\",\"mLastName\":\"lastName1\",\"mPassword\":\"password1\",\"mEmail\":\"email1\",\"mPhoneNumber\":\"phoneNumber1\"}}}";

		firstName = "firstName1";
		lastName = "lastName1";
		password = "password1";
		email = "email1";
		phoneNumber = "phoneNumber1";
		userName = "userName";
		creditCard = "creditCard1";

		accountUser = new AccountUser(firstName, lastName, password, email, phoneNumber, userName, creditCard, null);

		request = new Request(API.UPDATE_USER, accountUser);

		jsonRequest = gson.toJson(request);

    	jsonController = controller.Run(jsonRequest);

	    System.out.println(jsonController);

	    Assert.assertTrue(jsonController.equals(jsonTest));

		// By map

	    jsonTest = "{\"mType\":\"BuyMap\",\"mObject\":{\"mErrorCode\":0}}";

		cityName = "1";
		type = PurchaseType.ONE_TIME;

	    buyMapRequest = new BuyMapRequest(userName, cityName, type);

    	request = new Request(API.BUY_MAP, buyMapRequest);

    	jsonRequest = gson.toJson(request);

    	jsonController = controller.Run(jsonRequest);

	    System.out.println(jsonController);

	    Assert.assertTrue(jsonController.equals(jsonTest));

		// By map

	    jsonTest = "{\"mType\":\"BuyMap\",\"mObject\":{\"mErrorCode\":0}}";

		cityName = "2";
		type = PurchaseType.SUBSCRIPTION;

	    buyMapRequest = new BuyMapRequest(userName, cityName, type);

    	request = new Request(API.BUY_MAP, buyMapRequest);

    	jsonRequest = gson.toJson(request);

    	jsonController = controller.Run(jsonRequest);

	    System.out.println(jsonController);

	    Assert.assertTrue(jsonController.equals(jsonTest));

		// GetUser

	    jsonTest = "{\"mType\":\"GetUser\",\"mObject\":{\"mErrorCode\":0,\"mObject\":{\"mUserName\":\"userName\",\"mCreditCard\":\"creditCard1\",\"mPurchases\":[{\"mUserName\":\"userName\",\"mCityName\":\"1\",\"mType\":\"OneTime\"},{\"mUserName\":\"userName\",\"mCityName\":\"2\",\"mType\":\"Subscruption\"}],\"mFirstName\":\"firstName1\",\"mLastName\":\"lastName1\",\"mPassword\":\"password1\",\"mEmail\":\"email1\",\"mPhoneNumber\":\"phoneNumber1\"}}}";

    	accountCheckRequest = new AccountCheckRequest(userName, password);

    	request = new Request(API.GET_USER, accountCheckRequest);

    	jsonRequest = gson.toJson(request);

    	jsonController = controller.Run(jsonRequest);

	    System.out.println(jsonController);

	    Assert.assertTrue(jsonController.equals(jsonTest));

		// Show all purchases

	    jsonTest = "{\"mType\":\"GetUsersPurchases\",\"mObject\":{\"mErrorCode\":0,\"mObject\":[{\"mUserName\":\"userName\",\"mCityName\":\"1\",\"mType\":\"OneTime\"},{\"mUserName\":\"userName\",\"mCityName\":\"2\",\"mType\":\"Subscruption\"}]}}";

	    accountCheckRequest = new AccountCheckRequest("1", "1");

    	request = new Request(API.GET_USER_PURCHASES, accountCheckRequest);

    	jsonRequest = gson.toJson(request);

    	jsonController = controller.Run(jsonRequest);

	    System.out.println(jsonController);

	    Assert.assertTrue(jsonController.equals(jsonTest));

	}

}
