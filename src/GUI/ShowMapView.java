package GUI;

import Communication.ClientConsole;
import DB_classes.CityMap;
import DB_classes.Place;
import Defines.API;
import Defines.Dimensions;
import Defines.EditLevel;
import Defines.ErrorCodes;
import Defines.MemLvl;
import Defines.SceneName;
import Dialogs.MessageDialog;
import Dialogs.ProgressForm;
import Requests.BuyRequest;
import Requests.GeneralRequest;
import Requests.Request;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ShowMapView extends BaseView {

	// Images
	
    static Image mMapPlaceDefault;

    static ImageView imageViewMap;
    static ImageView imageViewPlace;

	// Variables

	String request_string;

	static boolean mIsFirstViewAfterPurchase = false;

    static int mCounterMap;
    public static int mCounterPlace;

    // TextView

	static TextField mapShow = new TextField();

	// Buttons

    static Button mBuy = new Button("Buy");

    static Button editPlace = new Button("Edit this place");
    static Button addMapToCity = new Button("");
    static Button editPrice= new Button ("Edit price");
    static Button editMap= new Button ("Edit map");

    static Button nextM = new Button("Next map");
    static Button pervM = new Button("Pervious map");
    static Button nextPlace = new Button("Next place");
    static Button prevPlace = new Button("Previous place");
    static Button showCity= new Button ("Show city");
    static Button showPlaces= new Button ("Show places");
    static Button showDescription= new Button ("Show Description");
    static Button showVersion= new Button ("Show Version");

    // Radio - Buy
    
	static RadioButton mSubscription;
	static RadioButton mOneTime;
   
    // Other
    
    static StackPane _result = new StackPane();

	public ShowMapView(ClientConsole aChat) {

		super(aChat);

		// Init
		
	    mMapPlaceDefault = new Image("/Images/MapPlaceDefault.png");

	    imageViewMap = new ImageView(new Image("/Images/MapPlaceDefault.png"));
	    imageViewPlace = new ImageView(new Image("/Images/MapPlaceDefault.png"));

		imageViewMap.setPreserveRatio(true);
		imageViewPlace.setPreserveRatio(true);

		mapShow.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		mapShow.setAlignment(Pos.CENTER);

		// Buy
		
		mSubscription = new RadioButton("Subscription");
		mOneTime = new RadioButton("OneTime");

		ToggleGroup group = new ToggleGroup();
		mSubscription.setToggleGroup(group);
		mOneTime.setToggleGroup(group);

		mSubscription.setSelected(true);

		// OnClick
		
        mBuy.setOnAction(e->{

        	setDiableAll(true);
        	
        	String type;
        	
        	if (mSubscription.isSelected()) {
        		type = mSubscription.getText();
        	} else {
        		type = mOneTime.getText();
        	}
        	
        	BuyRequest buyRequest = new BuyRequest(Main.mAccountUser.mUserName, Main.myMapList.get(0).mCity, type);
        	Request request = new Request(API.BUY, buyRequest);
        	String jsonString = mGson.toJson(request);
        	mChat.SendToServer(jsonString);

            ProgressForm progressForm = new ProgressForm();
            Task<Void> waitTask = new Dialogs.WaitTask();
            progressForm.activateProgressBar(waitTask);

            waitTask.setOnSucceeded(event -> {

            	progressForm.getDialogStage().close();

	        	setDiableAll(false);

                if (Main.mServerResponseErrorCode == ErrorCodes.SUCCESS) {

	        		MessageDialog alert = new MessageDialog(AlertType.INFORMATION, "Congradulation", "Your purchase has been accepted", "Enjoy the Maps");
	        		alert.showAndWait();
	
	        		mIsFirstViewAfterPurchase = true;

	    		} else {

	        		MessageDialog alert = new MessageDialog(AlertType.ERROR, "Error", "An unknown error has occurred, please try again", "Please try again");
	        		alert.showAndWait();
	
	        	}

	        	refreshScene();

	    		Main.mServerResponseErrorCode = ErrorCodes.RESET;
	    			        		        	
            });

            progressForm.getDialogStage().show();

            Thread thread = new Thread(waitTask);
            thread.start();
        
        });

		goBack.setOnAction(e->{
			mIsFirstViewAfterPurchase = false;
			Main.changeScene(SceneName.MAIN);
		});

		showVersion.setOnAction(e->{
			CityMap map = Main.myMapList.get(mCounterMap);
			mapShow.setText(map.mVersion);
			nextPlace.setVisible(false);
			prevPlace.setVisible(false);
			setDeafulePlace();
		});

		showDescription.setOnAction(e->{
			CityMap map = Main.myMapList.get(mCounterMap);
			mapShow.setText(map.mDescription);
			nextPlace.setVisible(false);
			prevPlace.setVisible(false);
			setDeafulePlace();
		});

		showCity.setOnAction(e->{
			CityMap map = Main.myMapList.get(mCounterMap);
			mapShow.setText(map.mCity);
			nextPlace.setVisible(false);
			prevPlace.setVisible(false);
			setDeafulePlace();
		});

		showPlaces.setOnAction(e->{
			if (Main.memberlevel != MemLvl.MEMBER && Main.memberlevel != MemLvl.FREE_USER) {
				editPlace.setVisible(true);
			}
			mCounterPlace = 0;
			CityMap map = Main.myMapList.get(mCounterMap);
			mapShow.setText(map.mPlaces.get(mCounterPlace).mName + "- " + map.mPlaces.get(mCounterPlace).mDescription);
			setPlace();
			nextPlace.setVisible(true);
			prevPlace.setVisible(true);
		});

		nextM.setOnAction(e->{
			mCounterPlace = 0;
  		   if(mCounterMap != Main.myMapList.size()-1) {
  			   mCounterMap++;
  		   }
  		   setMap();
  		   nextPlace.setVisible(false);
  		   prevPlace.setVisible(false);
  		   setDeafulePlace();
        });

		pervM.setOnAction(e->{
			mCounterPlace = 0;
        	if (mCounterMap != 0) {
        		mCounterMap--;
        	}
        	setMap();
        	nextPlace.setVisible(false);
     		prevPlace.setVisible(false);
     		setDeafulePlace();
        });

       nextPlace.setOnAction(e->{
    	   if(mCounterPlace != Main.myMapList.get(mCounterMap).mPlaces.size()-1) {
    		   mCounterPlace++;
    	   }
    	   CityMap map = Main.myMapList.get(mCounterMap);
    	   mapShow.setText(map.mPlaces.get(mCounterPlace).mName + " - " + map.mPlaces.get(mCounterPlace).mDescription);
    	   setPlace();
    	   nextPlace.setVisible(true);
    	   prevPlace.setVisible(true);
       });

  	   prevPlace.setOnAction(e->{
    	   if(mCounterPlace != 0) {
    		   mCounterPlace--;
    	   }
    	   CityMap map = Main.myMapList.get(mCounterMap);
    	   mapShow.setText(map.mPlaces.get(mCounterPlace).mName + " - " + map.mPlaces.get(mCounterPlace).mDescription);
    	   setPlace();
    	   nextPlace.setVisible(true);
	 	   prevPlace.setVisible(true);
       });

		// Edit

  	   editPrice.setOnAction(e->{
  			GUI.Main.editlevel = EditLevel.PRICE;
  		});

  		addMapToCity.setOnAction(e->{
  			GUI.Main.editlevel = EditLevel.ADD;
  		});

  		editPlace.setOnAction(e->{
  			GUI.Main.editlevel = EditLevel.PLACE;

  		});

  		editMap.setOnAction(e->{
  			GUI.Main.editlevel = EditLevel.MAP;

  		});

  	   // UI

		nextM.setMaxWidth(Dimensions.mShowMapViewButtonWidth);
		pervM.setMaxWidth(Dimensions.mShowMapViewButtonWidth);
		nextPlace.setMaxWidth(Dimensions.mShowMapViewButtonWidth);
		prevPlace.setMaxWidth(Dimensions.mShowMapViewButtonWidth);
		showCity.setMaxWidth(Dimensions.mShowMapViewButtonWidth);
		showPlaces.setMaxWidth(Dimensions.mShowMapViewButtonWidth);
		showDescription.setMaxWidth(Dimensions.mShowMapViewButtonWidth);
		showVersion.setMaxWidth(Dimensions.mShowMapViewButtonWidth);
		mBuy.setMaxWidth(Dimensions.mShowMapViewButtonWidth);
		mapShow.setMaxWidth(Dimensions.mShowMapViewTextWidth);
		
		// Y - coordinates
		
		nextM.setTranslateY(Dimensions.mShowMapViewTextRow1);
		pervM.setTranslateY(Dimensions.mShowMapViewTextRow1);

        showCity.setTranslateY(Dimensions.mShowMapViewTextRow2);
        showPlaces.setTranslateY(Dimensions.mShowMapViewTextRow2);
        showDescription.setTranslateY(Dimensions.mShowMapViewTextRow2);
        showVersion.setTranslateY(Dimensions.mShowMapViewTextRow2);
        
        nextPlace.setTranslateY(Dimensions.mShowMapViewTextRow3);
        prevPlace.setTranslateY(Dimensions.mShowMapViewTextRow4);

		mBuy.setTranslateY(Dimensions.mShowMapViewTextRow1);

		mSubscription.setTranslateY(Dimensions.mShowMapViewTextRow1 - 15);
		mOneTime.setTranslateY(Dimensions.mShowMapViewTextRow1 + 15);

		// X - coordinates

        showCity.setTranslateX(Dimensions.mShowMapViewTextCol1);
        showPlaces.setTranslateX(Dimensions.mShowMapViewTextCol2);
        prevPlace.setTranslateX(Dimensions.mShowMapViewTextCol2);
		nextPlace.setTranslateX(Dimensions.mShowMapViewTextCol2);
        showDescription.setTranslateX(Dimensions.mShowMapViewTextCol3);
        showVersion.setTranslateX(Dimensions.mShowMapViewTextCol4);
        
		pervM.setTranslateX(Dimensions.mShowMapViewTextCol1);
        nextM.setTranslateX(Dimensions.mShowMapViewTextCol4);

		mBuy.setTranslateX(Dimensions.mShowMapViewTextCol2);

		mSubscription.setTranslateX(Dimensions.mShowMapViewTextCol3);
		mOneTime.setTranslateX(Dimensions.mShowMapViewTextCol3 - 5);

		// Maps

		imageViewMap.setTranslateX(-200);
		imageViewMap.setTranslateY(-150);

		imageViewMap.setFitWidth(390);
		imageViewMap.setFitHeight(290);

		imageViewPlace.setTranslateX(200);
		imageViewPlace.setTranslateY(-150);

		imageViewPlace.setFitWidth(390);
		imageViewPlace.setFitHeight(290);

		// Edit

	 	editPlace.setTranslateY(220);
	 	editPrice.setTranslateY(220);
	 	editMap.setTranslateY(220);
	 	addMapToCity.setTranslateY(220);
	 	editPlace.setTranslateX(-170);
	 	editPrice.setTranslateX(-70);
	 	editMap.setTranslateX(50);
	 	addMapToCity.setTranslateX(180);

		// Scene

		_result.setBackground(new Background(myBIc));
		_result.getChildren().addAll(mSubscription, mOneTime, editPrice,addMapToCity,editPlace,editMap,showVersion,showDescription,showCity,showPlaces,nextM, pervM, goBack, imageViewPlace, prevPlace, imageViewMap, nextPlace, mapShow, mBuy);  	   
		mScene = new Scene(_result, Dimensions.mWith, Dimensions.mheight);
  	   
	}

	// Edit
	
    public static void setDeafulePlace() {
		editPlace.setVisible(false);
		imageViewPlace.setImage(mMapPlaceDefault);
    }
    
	public static void refreshScene() {
		
		mCounterMap = 0;
	    mCounterPlace = 0;

		nextPlace.setVisible(false);
	 	prevPlace.setVisible(false);

	 	boolean isUserCanWatchImages = Main.memberlevel == MemLvl.MEMBER && ( Main.mAccountUser.HasSubscription(Main.myMapList.get(mCounterMap).mCity) || mIsFirstViewAfterPurchase);
	 	
		if (!isUserCanWatchImages) {
			imageViewMap.setImage(mMapPlaceDefault);
			imageViewPlace.setImage(mMapPlaceDefault);
		}

		if (Main.memberlevel == MemLvl.FREE_USER) {
			mBuy.setVisible(false);
			mSubscription.setVisible(false);
			mOneTime.setVisible(false);
		} else if (!isUserCanWatchImages) {
			mBuy.setVisible(true);
			mSubscription.setVisible(true);
			mOneTime.setVisible(true);
		} else {
			mBuy.setVisible(false);
			mSubscription.setVisible(false);
			mOneTime.setVisible(false);
		}

	 	// Edit
	 	
	 	editPlace.setVisible(false);
	 	editPrice.setVisible(false);
	 	editMap.setVisible(false);
	 	addMapToCity.setVisible(false);

		setMap();
			
		if(Main.memberlevel!=MemLvl.MEMBER && Main.memberlevel!= MemLvl.FREE_USER) {

				CityMap map = Main.myMapList.get(mCounterMap);

			 	editPrice.setVisible(true);
			 	editMap.setVisible(true);
			 	editMap.setText("edit " + map.mName+" map");
			 	addMapToCity.setVisible(true);
			 	addMapToCity.setText("Add map of "+map.mCity);

		}

	}

    public static void setMap() {

    	if (mCounterMap < 0 || mCounterMap > Main.myMapList.size()) {
	    	return;
	    }

    	CityMap map = Main.myMapList.get(mCounterMap);
    	
		if (Main.memberlevel == MemLvl.MEMBER && ( Main.mAccountUser.HasSubscription(map.mCity) || mIsFirstViewAfterPurchase) ) {
			imageViewMap.setImage(new Image(map.mURL));
		}

	    mapShow.setText(map.mName);

    }

    public void setPlace() {

    	CityMap map = Main.myMapList.get(mCounterMap);

    	if (mCounterPlace < 0 || mCounterPlace >= map.mPlaces.size()) {
    		return;
    	}    	

    	Place place = map.mPlaces.get(mCounterPlace);

		if (Main.memberlevel == MemLvl.MEMBER && ( Main.mAccountUser.HasSubscription(map.mCity) || mIsFirstViewAfterPurchase) ) {
			imageViewPlace.setImage((new Image(place.mURL)));
		}

    }

   private static void setDiableAll(boolean aDisable) {
	   
	   showVersion.setDisable(aDisable);
	   showDescription.setDisable(aDisable);
	   showCity.setDisable(aDisable);
	   showPlaces.setDisable(aDisable);
	   
	   nextM.setDisable(aDisable);
	   pervM.setDisable(aDisable);

	   prevPlace.setDisable(aDisable);
	   nextPlace.setDisable(aDisable);
	   
	   mBuy.setDisable(aDisable);

   }
    
}
