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

}
