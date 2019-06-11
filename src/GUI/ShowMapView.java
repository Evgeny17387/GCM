package GUI;

import Communication.ClientConsole;
import Constants.SceneName;
import DB_classes.CityMap;
import DB_classes.Place;
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

	TextField mapShow = new TextField();

	public ShowMapView(Stage stage, ClientConsole aChat, UI_server_communicate aCommunicate) {
		super(stage, aChat, aCommunicate);
	}

	public Scene getScene() {

		clean_tf();

        BackgroundImage myBIc = new BackgroundImage(new Image("Images\\catalog_up.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        mapShow.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        Button getBack11 = new Button("Go to Main");
        getBack11.setOnAction(e->{stage.setScene(Main.getScenes().get(SceneName.MAIN));clean_tf();});

  	   ImageView imageViewMap = new ImageView(new Image("https://www.myyellowknifenow.com/wp-content/uploads/2019/02/photographer-698908_960_720.jpg"));	
  	   imageViewMap.setPreserveRatio(true);

  	   ImageView imageViewPlace = new ImageView(new Image("https://www.myyellowknifenow.com/wp-content/uploads/2019/02/photographer-698908_960_720.jpg"));
  	   imageViewPlace.setPreserveRatio(true);

       Button chooseM = new Button("Choose this map");

       Button nextM = new Button("Next map");
  	   nextM.setOnAction(e->{
        	if(counter!=Main.myMapList.size()-1)counter++;
        	setMap(imageViewMap);
        });

       Button pervM = new Button("Pervious map");
        pervM.setOnAction(e->{
        	if(counter!=0)counter--;
        	setMap(imageViewMap);
        });

       Button nextPlace = new Button("Next");
       nextPlace.setOnAction(e->{
    	   if(counterPlace != Main.myMapList.get(counter).mPlaces.size()-1) {
    		   counterPlace++;
    	   }
    	   setPlace(imageViewPlace);
       });

       Button prevPlace = new Button("Previos");
  	   prevPlace.setOnAction(e->{
    	   if(counterPlace != 0) {
    		   counterPlace--;
    	   }
    	   setPlace(imageViewPlace);
       });
        
		getBack11.setTranslateY(100);
		nextM.setTranslateX(100);
		nextM.setTranslateY(50);
		chooseM.setTranslateY(50);
		chooseM.setTranslateX(0);
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

       StackPane _result = new StackPane();
       _result.setBackground(new Background(myBIc));
  	   _result.getChildren().addAll(nextM, pervM, chooseM, getBack11, imageViewPlace, prevPlace, imageViewMap, nextPlace, mapShow);

  	   Scene scene = new Scene(_result, 1280,720);
		
  	   return scene;

	}

    public  void clean_tf() {
    	
    }

    public void setMap(ImageView aImageView) {
    if (counter<0||counter>Main.myMapList.size()) return;
    CityMap map = Main.myMapList.get(counter);
	System.out.println("good" +map.toString());
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

}
