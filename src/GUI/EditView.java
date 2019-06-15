package GUI;

import Communication.ClientConsole;
import Defines.Dimensions;
import Defines.SceneName;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Scene;
import Defines.MemLvl;
import Defines.EditLevel;

public class EditView extends BaseView  {
	StackPane root = new StackPane();
	static TextField mName=new TextField("");
	static TextField mDescription=new TextField ("");
	static TextField mURL=new TextField("");
	static TextField mPrice=new TextField("Type new price");
	static TextField newMapName=new TextField("New map name");
	static TextField newMapDescription=new TextField("New map description");
	static TextField newMapPic=new TextField("New map jpg url");
	static TextField placeDescription=new TextField("New place description");
	static TextField placeName=new TextField("New place name");
	static TextField placeURL=new TextField("New place URL");
	static TextField editPlaceName=new TextField("");
	static TextField editPlaceUrl=new TextField("");
	static TextField editPlaceDescription=new TextField("");

	Button Next = new Button ("Next");
	Button goBack = new Button("Go back");

	public EditView(ClientConsole aChat) {

		super(aChat);

		// Init
		
	    root.setBackground(new Background(myBIe));
	
		
		
		
		mName.setVisible(false);
		mDescription.setVisible(false);
		mPrice.setVisible(false);
		newMapName.setVisible(false);
		newMapDescription.setVisible(false);
		newMapPic.setVisible(false);
		placeDescription.setVisible(false);
		placeName.setVisible(false);
		placeURL.setVisible(false);
		editPlaceName.setVisible(false);
		editPlaceUrl.setVisible(false);
		editPlaceDescription.setVisible(false);
		mURL.setVisible(false);
	
		
		
		
		
		
		
		// OnClick

		goBack.setOnAction(e->{Main.changeScene(SceneName.SHOW_MAP);});

		// UI

		goBack.setMaxHeight(Dimensions.mBaseViewButtonsHeight);
		goBack.setMaxWidth(Dimensions.mBaseViewButtonsWidth);
		goBack.setTranslateY(Dimensions.mheight / 2 - Dimensions.mBaseViewButtonsHeight);
		goBack.setTranslateX( - Dimensions.mWith / 2 + Dimensions.mBaseViewButtonsWidth);

		// Scene

       	root.getChildren().addAll(mURL,editPlaceDescription,editPlaceUrl,editPlaceName,placeURL,placeName,mName,mDescription,mPrice,newMapName,newMapDescription,newMapPic,placeDescription,Next,goBack);
     	mScene = new Scene(root, Dimensions.mWith, Dimensions.mheight);

	 
	}
	
	public static void refreshScene() {

//		BuyView.myCity = Main.myMapList.get(0);
		
		
	    switch(GUI.Main.editlevel) {
   	 case ZERO:
   			break;
   	 case PLACE:
   		 mName.setVisible(false);
   		 mDescription.setVisible(false);
   		 mURL.setVisible(false);
   		 mPrice.setVisible(false);
   		 newMapName.setVisible(false);
   		 newMapDescription.setVisible(false);
   		 newMapPic.setVisible(false);
   		 placeDescription.setVisible(false);
   		 placeName.setVisible(false);
   		 placeURL.setVisible(false);
   		 editPlaceName.setVisible(true);
   		 editPlaceUrl.setVisible(true);
   		 editPlaceDescription.setVisible(true);
   		 editPlaceName.setMaxWidth(Dimensions.mSignInViewTextWidth);
   		 editPlaceUrl.setMaxWidth(Dimensions.mSignInViewTextWidth);
   		 editPlaceDescription.setMaxWidth(Dimensions.mSignInViewTextWidth);
   		 editPlaceName.setPromptText("New name of the place.");
   		 editPlaceDescription.setPromptText("New description of the place.");
   	 	 editPlaceUrl.setPromptText("New jpg url of the place.");   	
   	     editPlaceName.setText(Main.myMapList.get(ShowMapView.mCounterMap).mPlaces.get(GUI.ShowMapView.mCounterPlace).mName);
		 editPlaceDescription.setText(Main.myMapList.get(ShowMapView.mCounterMap).mPlaces.get(GUI.ShowMapView.mCounterPlace).mDescription);
	 	 editPlaceUrl.setText(Main.myMapList.get(ShowMapView.mCounterMap).mPlaces.get(GUI.ShowMapView.mCounterPlace).mURL);  
   	 	 
   	 	 
	 	editPlaceName.setTranslateY(-250);
	 	editPlaceDescription.setTranslateY(-200);
	 	editPlaceUrl.setTranslateY(-150);
   		 
   		 
   		 
			break;
   		 
   		 
   		 
			
			
   	 case MAP:
   		 
   		 mName.setVisible(true);
   		 mDescription.setVisible(true);
   		 mURL.setVisible(true);
   		 mPrice.setVisible(false);
   		 newMapName.setVisible(false);
   		 newMapDescription.setVisible(false);
   		 newMapPic.setVisible(false);
   		 placeDescription.setVisible(false);
   		 placeName.setVisible(false);
   		 placeURL.setVisible(false);
   		 editPlaceName.setVisible(false);
   		 editPlaceUrl.setVisible(false);
   		 editPlaceDescription.setVisible(false);
   		 mName.setMaxWidth(Dimensions.mSignInViewTextWidth);
   		 mDescription.setMaxWidth(Dimensions.mSignInViewTextWidth);
   		 mURL.setMaxWidth(Dimensions.mSignInViewTextWidth);
   		 mName.setText(Main.myMapList.get(ShowMapView.mCounterMap).mName);
   		 mDescription.setText(Main.myMapList.get(ShowMapView.mCounterMap).mDescription);
   		 mURL.setText(Main.myMapList.get(ShowMapView.mCounterMap).mURL);   		 
   	     mName.setTranslateY(-250);
   	     mDescription.setTranslateY(-200);
   	     mURL.setTranslateY(-150);
   		 
   		 
   		 
   		 
   		 
   		 
			break;			
			
   		 
   		 
   		 
   	 case ADD:
   		 mName.setVisible(false);
   		 mDescription.setVisible(false);
   		 mURL.setVisible(false);
   		 mPrice.setVisible(false);
   		 newMapName.setVisible(true);
   		 newMapDescription.setVisible(true);
   		 newMapPic.setVisible(true);
   		 placeDescription.setVisible(false);
   		 placeName.setVisible(false);
   		 placeURL.setVisible(false);
   		 editPlaceName.setVisible(false);
   		 editPlaceUrl.setVisible(false);
   		 editPlaceDescription.setVisible(false);
   		 newMapName.setMaxWidth(Dimensions.mSignInViewTextWidth);
   		 newMapDescription.setMaxWidth(Dimensions.mSignInViewTextWidth);
   		 newMapPic.setMaxWidth(Dimensions.mSignInViewTextWidth);
   		 newMapName.setPromptText("New map name");
   	 	 newMapDescription.setPromptText("New map  description");
   		 newMapPic.setPromptText("New map jpg url");   		 
   		 newMapName.setTranslateY(-250);
   		 newMapDescription.setTranslateY(-200);
   		 newMapPic.setTranslateY(-150);
   		 
   		 
   		 
			break;

			
			
   	 case PRICE:
   		 mName.setVisible(false);
   		 mDescription.setVisible(false);
   		 mURL.setVisible(false);
   		 mPrice.setVisible(true);
   		 newMapName.setVisible(false);
   		 newMapDescription.setVisible(false);
   		 newMapPic.setVisible(false);
   		 placeDescription.setVisible(false);
   		 placeName.setVisible(false);
   		 placeURL.setVisible(false);
   		 editPlaceName.setVisible(false);
   		 editPlaceUrl.setVisible(false);
   		 editPlaceDescription.setVisible(false);
   		 mPrice.setMaxWidth(Dimensions.mSignInViewTextWidth);
   		 mPrice.setText(""+Main.myMapList.get(ShowMapView.mCounterMap).mPrice);
   		 mPrice.setPromptText("New price");   		 
   		 mPrice.setTranslateY(-250);
   		 
   		 
   		 
   		 
   		 break;
   		 
   		 
   		 
   	 case ADD_PLACE:
   		 mName.setVisible(false);
   		 mDescription.setVisible(false);
   		 mURL.setVisible(false);
   		 mPrice.setVisible(false);
   		 newMapName.setVisible(false);
   		 newMapDescription.setVisible(false);
   		 newMapPic.setVisible(false);
   		 placeDescription.setVisible(true);
   		 placeName.setVisible(true);
   		 placeURL.setVisible(true);
   		 editPlaceName.setVisible(false);
   		 editPlaceUrl.setVisible(false);
   		 editPlaceDescription.setVisible(false);
   		 newMapName.setMaxWidth(Dimensions.mSignInViewTextWidth);
   		 newMapDescription.setMaxWidth(Dimensions.mSignInViewTextWidth);
   		 newMapPic.setMaxWidth(Dimensions.mSignInViewTextWidth);
   		 placeName.setPromptText("New place name");
   		 placeDescription.setPromptText("New place description");
   		 placeURL.setPromptText("New place jpg url");   		 
   		 placeName.setTranslateY(-250);
   		 placeDescription.setTranslateY(-200);
   		 placeURL.setTranslateY(-150);
   		 
   		 
   		 
   		 break;
   		 
   	default:
   		break;
    }

		

	}
	
}
