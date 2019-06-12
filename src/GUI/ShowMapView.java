package GUI;

import Communication.ClientConsole;
import DB_classes.CityMap;
import DB_classes.Place;
import Defines.Dimensions;
import Defines.MemLvl;
import Defines.SceneName;
import Utils.UI_server_communicate;
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

	String request_string;

    int counter = 0;
    int counterPlace = 0;

	static ImageView imageViewMap = new ImageView(new Image("https://www.myyellowknifenow.com/wp-content/uploads/2019/02/photographer-698908_960_720.jpg"));	
	static ImageView imageViewPlace = new ImageView(new Image("https://www.myyellowknifenow.com/wp-content/uploads/2019/02/photographer-698908_960_720.jpg"));

    static StackPane _result = new StackPane();

	static TextField mapShow = new TextField();

    static Button chooseM = new Button("Buy options");
    static Button getBack11 = new Button("Go to Main");
    static Button nextM = new Button("Next map");
    static Button pervM = new Button("Pervious map");
    static Button nextPlace = new Button("Next");
    static Button prevPlace = new Button("Previos");

	public ShowMapView(ClientConsole aChat, UI_server_communicate aCommunicate) {

		super(aChat, aCommunicate);

        BackgroundImage myBIc = new BackgroundImage(new Image("Images\\catalog_up.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        mapShow.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        chooseM.setOnAction(e->{
    		BuyView.myCity = Main.myMapList.get(0).mCity;
    		Main.changeScene(SceneName.BUY);
        });
      
        getBack11.setOnAction(e->Main.changeScene(SceneName.MAIN));

  	   imageViewMap.setPreserveRatio(true);

  	   imageViewPlace.setPreserveRatio(true);

  	   nextM.setOnAction(e->{
        	if(counter!=Main.myMapList.size()-1)counter++;
        	setMap(imageViewMap);
        });

        pervM.setOnAction(e->{
        	if(counter!=0)counter--;
        	setMap(imageViewMap);
        });

       nextPlace.setOnAction(e->{
    	   if(counterPlace != Main.myMapList.get(counter).mPlaces.size()-1) {
    		   counterPlace++;
    	   }
    	   setPlace(imageViewPlace);
       });

  	   prevPlace.setOnAction(e->{
    	   if(counterPlace != 0) {
    		   counterPlace--;
    	   }
    	   setPlace(imageViewPlace);
       });
        
		getBack11.setTranslateY(100);
		nextM.setTranslateX(100);
		nextM.setTranslateY(50);
		pervM.setTranslateY(50);
		pervM.setTranslateX(-100);
		prevPlace.setTranslateY(-50);
		prevPlace.setTranslateX(100);
		nextPlace.setTranslateY(-50);
		nextPlace.setTranslateX(200);
		imageViewMap.setTranslateX(-100);
		imageViewMap.setTranslateY(-150);
		imageViewMap.setFitHeight(200);
		imageViewMap.setFitWidth(350);
		imageViewPlace.setTranslateX(150);
		imageViewPlace.setTranslateY(-150);
		imageViewPlace.setFitHeight(100);
		imageViewPlace.setFitWidth(150);
		mapShow.setMaxWidth(500);
		mapShow.setTranslateY(0);
    	chooseM.setTranslateY(50);
		chooseM.setTranslateX(0);

       _result.setBackground(new Background(myBIc));
  	   _result.getChildren().addAll(nextM, pervM, getBack11, imageViewPlace, prevPlace, imageViewMap, nextPlace, mapShow, chooseM);

  	   mScene = new Scene(_result, Dimensions.mWith, Dimensions.mheight);
		
	}

    public void setMap(ImageView aImageView) {
    if (counter<0||counter>Main.myMapList.size()) return;
    CityMap map = Main.myMapList.get(counter);
	aImageView.setImage(new Image(map.mURL));
	mapShow.setText(map.toString());
	
    }

    public void setPlace(ImageView aImageView) {
    	CityMap map = Main.myMapList.get(counter);
    	if (counterPlace < 0 || counterPlace >= map.mPlaces.size()) {
    		return;
    	}
    	Place place = map.mPlaces.get(counterPlace);
		aImageView.setImage(new Image(place.mURL));
    }

	public static void refreshScene() {
		
		if (Main.memberlevel == MemLvl.FREE_USER) {

			chooseM.setVisible(false);

		} else if (Main.memberlevel == MemLvl.MEMBER) {

			chooseM.setVisible(true);

		}

	}

}
