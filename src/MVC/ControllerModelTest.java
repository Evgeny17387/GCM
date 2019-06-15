package MVC;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import DB_classes.CityMap;
import DB_classes.Place;
import Defines.API;
import Defines.ErrorCodes;
import Requests.Request;
import Responses.ResponseController;
import Responses.ResponseModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class ControllerModelTest {

	// Scenario: SearchMapByCity

	@Test
	void testScenario1() {

		Controller controller = new Controller();
		Gson gson = new Gson();

		String testName = "Test";

		CityMap cityMap = new CityMap(testName, "0", testName, testName, new ArrayList<Place>(), 0, testName);
		Place place = new Place(testName, testName, testName, testName);
		cityMap.mPlaces.add(place);
		
		Request request = new Request(API.SEARCH_BY_CITY, testName);
		String jsonRequest = gson.toJson(request);

		String jsonController = controller.Run(jsonRequest);
    	
		ResponseController responseController = gson.fromJson(jsonController, ResponseController.class);
		ResponseModel responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);

		Assert.assertTrue(responseModel.mErrorCode == ErrorCodes.SUCCESS);

		Type type = new TypeToken<List<CityMap>>(){}.getType();
		List<CityMap> cityMapResponseList = gson.fromJson(gson.toJson(responseModel.mObject), type);
	    CityMap cityMapResponse = cityMapResponseList.get(0);

	    type = new TypeToken<List<Place>>(){}.getType();
	    List<Place> placeResponseList = gson.fromJson(gson.toJson(cityMapResponse.mPlaces), type);
	    Place placeResponse = placeResponseList.get(0);

		Assert.assertTrue(place.equals(placeResponse));

	}

	// Scenario: SearchMapByPlace

	@Test
	void testScenario2() {

		Controller controller = new Controller();
		Gson gson = new Gson();

		String testName = "Test";

		CityMap cityMap = new CityMap(testName, "0", testName, testName, new ArrayList<Place>(), 0, testName);
		Place place = new Place(testName, testName, testName, testName);
		cityMap.mPlaces.add(place);
		
		Request request = new Request(API.SEARCH_BY_PLACE, testName);
		String jsonRequest = gson.toJson(request);

		String jsonController = controller.Run(jsonRequest);
    	
		ResponseController responseController = gson.fromJson(jsonController, ResponseController.class);
		ResponseModel responseModel = gson.fromJson(gson.toJson(responseController.mObject), ResponseModel.class);

		Assert.assertTrue(responseModel.mErrorCode == ErrorCodes.SUCCESS);

		Type type = new TypeToken<List<CityMap>>(){}.getType();
		List<CityMap> cityMapResponseList = gson.fromJson(gson.toJson(responseModel.mObject), type);
	    CityMap cityMapResponse = cityMapResponseList.get(0);

	    type = new TypeToken<List<Place>>(){}.getType();
	    List<Place> placeResponseList = gson.fromJson(gson.toJson(cityMapResponse.mPlaces), type);
	    Place placeResponse = placeResponseList.get(0);

		Assert.assertTrue(place.equals(placeResponse));

	}

}
