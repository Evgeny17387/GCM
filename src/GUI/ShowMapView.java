package GUI;

import Communication.ClientConsole;
import DB_classes.CityMap;
import DB_classes.Place;
import Defines.Dimensions;
import Defines.EditLevel;
import Defines.MemLvl;
import Defines.SceneName;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    static int mCounter;
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

    // Other
    
    static StackPane _result = new StackPane();

	public ShowMapView(ClientConsole aChat) {

		super(aChat);

		// Init
		
		mMapPlaceDefault = new Image("/Images/MapPlaceDefault.png");
		
		imageViewMap = new ImageView(mMapPlaceDefault);
		imageViewPlace = new ImageView(mMapPlaceDefault);

		imageViewMap.setPreserveRatio(true);
		imageViewPlace.setPreserveRatio(true);

		mapShow.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		mapShow.setAlignment(Pos.CENTER);

		// OnClick
		
        mBuy.setOnAction(e->{

        	
        	
        	BuyView.myCity = Main.myMapList.get(0);
    		Main.changeScene(SceneName.BUY);
    		setDeafulePlace(imageViewPlace);

        
        
        });
  	   
  	  showVersion.setOnAction(e->{
  		 CityMap map = Main.myMapList.get(mCounter);
 		mapShow.setText(map.mVersion);
 		nextPlace.setVisible(false);
 		prevPlace.setVisible(false);
 		setDeafulePlace(imageViewPlace);
  	   });
  	 showDescription.setOnAction(e->{
  		 CityMap map = Main.myMapList.get(mCounter);
 		mapShow.setText(map.mDescription);
 		nextPlace.setVisible(false);
 		prevPlace.setVisible(false);
 		setDeafulePlace(imageViewPlace);
  	   });
  	 
  	   showCity.setOnAction(e->{
  		 CityMap map = Main.myMapList.get(mCounter);
 		mapShow.setText(map.mCity);
 		nextPlace.setVisible(false);
 		prevPlace.setVisible(false);
 		setDeafulePlace(imageViewPlace);
  	   });
  	   
  	   
  	 showPlaces.setOnAction(e->{
  		if(Main.memberlevel!=MemLvl.MEMBER && Main.memberlevel!= MemLvl.FREE_USER)editPlace.setVisible(true);
  		mCounterPlace =0;
  		 CityMap map = Main.myMapList.get(mCounter);
 		mapShow.setText(map.mPlaces.get(mCounterPlace).mName+"- "+map.mPlaces.get(mCounterPlace).mDescription);
 		setPlace(imageViewPlace);
 		nextPlace.setVisible(true);
 		prevPlace.setVisible(true);
  	   });
  	 
  	   nextM.setOnAction(e->{
  		 mCounterPlace=0;
        	if(mCounter!=Main.myMapList.size()-1)mCounter++;
        	setMap();
        	nextPlace.setVisible(false);
     		prevPlace.setVisible(false);
     		setDeafulePlace(imageViewPlace);
        });

        pervM.setOnAction(e->{
        	mCounterPlace=0;
        	if(mCounter!=0)mCounter--;
        	setMap();
        	nextPlace.setVisible(false);
     		prevPlace.setVisible(false);
     		setDeafulePlace(imageViewPlace);
        });

       nextPlace.setOnAction(e->{
    	   if(mCounterPlace != Main.myMapList.get(mCounter).mPlaces.size()-1) {
    		   mCounterPlace++;
    		 
    	   }
    	   CityMap map = Main.myMapList.get(mCounter);
    	   mapShow.setText(map.mPlaces.get(mCounterPlace).mName+"- "+map.mPlaces.get(mCounterPlace).mDescription);
    	   setPlace(imageViewPlace);
    	   nextPlace.setVisible(true);
    	   prevPlace.setVisible(true);
       });

  	   prevPlace.setOnAction(e->{
    	   if(mCounterPlace != 0) {
    		   mCounterPlace--;
    	   }
    	   CityMap map = Main.myMapList.get(mCounter);
    	   mapShow.setText(map.mPlaces.get(mCounterPlace).mName+"- "+map.mPlaces.get(mCounterPlace).mDescription);
    	   setPlace(imageViewPlace);
    	   nextPlace.setVisible(true);
	 	   prevPlace.setVisible(true);
       });

  	   editPrice.setOnAction(e->{
  			GUI.Main.editlevel=EditLevel.PRICE;
  		});

  		addMapToCity.setOnAction(e->{
  			GUI.Main.editlevel=EditLevel.ADD;
  		});

  		editPlace.setOnAction(e->{
  			GUI.Main.editlevel=EditLevel.PLACE;

  		});

  		editMap.setOnAction(e->{
  			GUI.Main.editlevel=EditLevel.MAP;

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
		
		// X - coordinates

        showCity.setTranslateX(Dimensions.mShowMapViewTextCol1);
        showPlaces.setTranslateX(Dimensions.mShowMapViewTextCol2);
        prevPlace.setTranslateX(Dimensions.mShowMapViewTextCol2);
		nextPlace.setTranslateX(Dimensions.mShowMapViewTextCol2);
        showDescription.setTranslateX(Dimensions.mShowMapViewTextCol3);
        showVersion.setTranslateX(Dimensions.mShowMapViewTextCol4);
        
		pervM.setTranslateX(Dimensions.mShowMapViewTextCol1);
        nextM.setTranslateX(Dimensions.mShowMapViewTextCol4);

		mBuy.setTranslateY(50);

		// Maps

		imageViewMap.setTranslateX(-200);
		imageViewMap.setTranslateY(-150);

		imageViewMap.setFitWidth(390);
		imageViewMap.setFitHeight(290);

		imageViewPlace.setTranslateX(200);
		imageViewPlace.setTranslateY(-150);

		imageViewPlace.setFitWidth(390);
		imageViewPlace.setFitHeight(290);

		// Edit Buttons

	 	editPlace.setTranslateY(220);
	 	editPrice.setTranslateY(220);
	 	editMap.setTranslateY(220);
	 	addMapToCity.setTranslateY(220);
	 	editPlace.setTranslateX(-170);
	 	editPrice.setTranslateX(-70);
	 	editMap.setTranslateX(50);
	 	addMapToCity.setTranslateX(180);

		// Scene

		_result.setBackground(new Background(myBIc2));
		_result.getChildren().addAll(editPrice,addMapToCity,editPlace,editMap,showVersion,showDescription,showCity,showPlaces,nextM, pervM, goBack, imageViewPlace, prevPlace, imageViewMap, nextPlace, mapShow, mBuy);  	   
		mScene = new Scene(_result, Dimensions.mWith, Dimensions.mheight);
  	   
	}

	public static void refreshScene() {

	    mCounter = 0;
	    mCounterPlace = 0;

		imageViewMap = new ImageView(mMapPlaceDefault);
		imageViewPlace = new ImageView(mMapPlaceDefault);

		nextPlace.setVisible(false);
	 	prevPlace.setVisible(false);

	 	editPlace.setVisible(false);
	 	editPrice.setVisible(false);
	 	editMap.setVisible(false);
	 	addMapToCity.setVisible(false);

		setMap();

		if (Main.memberlevel == MemLvl.FREE_USER) {

			mBuy.setVisible(false);

		} else if (Main.memberlevel == MemLvl.MEMBER) {

			mBuy.setVisible(true);

		} else if(Main.memberlevel!=MemLvl.MEMBER && Main.memberlevel!= MemLvl.FREE_USER) {

				CityMap map = Main.myMapList.get(mCounter);

			 	editPrice.setVisible(true);
			 	editMap.setVisible(true);
			 	editMap.setText("edit " + map.mName+" map");
			 	addMapToCity.setVisible(true);
			 	addMapToCity.setText("Add map of "+map.mCity);

		}

	}

    public static void setMap() {

    	if (mCounter < 0 || mCounter > Main.myMapList.size()) {
	    	return;
	    }

    	CityMap map = Main.myMapList.get(mCounter);

		if (Main.memberlevel != MemLvl.FREE_USER) {
		    imageViewMap.setImage(new Image(map.mURL));
	    }

	    mapShow.setText(map.mName);

    }

    public void setPlace(ImageView aImageView) {
    	CityMap map = Main.myMapList.get(mCounter);
    	if (mCounterPlace < 0 || mCounterPlace >= map.mPlaces.size()) {
    		return;
    	}    	
    	Place place = map.mPlaces.get(mCounterPlace);

		if (Main.memberlevel != MemLvl.FREE_USER) {
			aImageView.setImage(new Image(place.mURL));
		}

    }

    public static void setDeafulePlace(ImageView aImageView) {
		editPlace.setVisible(false);
    	aImageView.setImage(mMapPlaceDefault);
    }

}
