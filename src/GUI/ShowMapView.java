package GUI;

import Communication.ClientConsole;
import DB_classes.CityMap;
import DB_classes.Place;
import Defines.Dimensions;
import Defines.MemLvl;
import Defines.SceneName;
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

    static int counter = 0;
    public static int counterPlace =0;

	static ImageView imageViewMap = new ImageView(new Image("https://www.myyellowknifenow.com/wp-content/uploads/2019/02/photographer-698908_960_720.jpg"));	
	static ImageView imageViewPlace = new ImageView(new Image("https://scontent.fhfa2-2.fna.fbcdn.net/v/t1.0-9/62406156_2525296037483339_111679483387314176_n.jpg?_nc_cat=110&_nc_ht=scontent.fhfa2-2.fna&oh=e0ca60d86bdb6a893fd0887712c464f7&oe=5D84F941"));

    static StackPane _result = new StackPane();

	static TextField mapShow = new TextField();

    static Button chooseM = new Button("Buy options");
    static Button nextM = new Button("Next map");
    static Button pervM = new Button("Pervious map");
    static Button nextPlace = new Button("Next place");
    static Button prevPlace = new Button("Previous place");
    static Button showCity= new Button ("Show city");
    static Button showPlaces= new Button ("Show places");
    static Button showDescription= new Button ("Show Description");
    static Button showVersion= new Button ("Show Version");

	public ShowMapView(ClientConsole aChat) {

		super(aChat);

        mapShow.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        chooseM.setOnAction(e->{
    		BuyView.myCity = Main.myMapList.get(0);
    		Main.changeScene(SceneName.BUY);
    		setDeafulePlace(imageViewPlace);
        });

  	   imageViewMap.setPreserveRatio(true);
  	   imageViewPlace.setPreserveRatio(true);
  	   
  	 showVersion.setOnAction(e->{
  		 CityMap map = Main.myMapList.get(counter);
 		mapShow.setText(map.mVersion);
 		nextPlace.setVisible(false);
 		prevPlace.setVisible(false);
 		setDeafulePlace(imageViewPlace);
  	   });
  	 showDescription.setOnAction(e->{
  		 CityMap map = Main.myMapList.get(counter);
 		mapShow.setText(map.mDescription);
 		nextPlace.setVisible(false);
 		prevPlace.setVisible(false);
 		setDeafulePlace(imageViewPlace);
  	   });
  	 
  	   showCity.setOnAction(e->{
  		 CityMap map = Main.myMapList.get(counter);
 		mapShow.setText(map.mCity);
 		nextPlace.setVisible(false);
 		prevPlace.setVisible(false);
 		setDeafulePlace(imageViewPlace);
  	   });
  	   
  	   
  	 showPlaces.setOnAction(e->{
  		counterPlace =0;
  		 CityMap map = Main.myMapList.get(counter);
 		mapShow.setText(map.mPlaces.get(counterPlace).mName+"- "+map.mPlaces.get(counterPlace).mDescription);
 		setPlace(imageViewPlace);
 		nextPlace.setVisible(true);
 		prevPlace.setVisible(true);
  	   });
  	 
  	   nextM.setOnAction(e->{
  		    counterPlace=0;
        	if(counter!=Main.myMapList.size()-1)counter++;
        	setMap(imageViewMap);
        	nextPlace.setVisible(false);
     		prevPlace.setVisible(false);
     		setDeafulePlace(imageViewPlace);
        });

        pervM.setOnAction(e->{
        	counterPlace=0;
        	if(counter!=0)counter--;
        	setMap(imageViewMap);
        	nextPlace.setVisible(false);
     		prevPlace.setVisible(false);
     		setDeafulePlace(imageViewPlace);
        });

       nextPlace.setOnAction(e->{
    	   if(counterPlace != Main.myMapList.get(counter).mPlaces.size()-1) {
    		   counterPlace++;
    		 
    	   }
    	   CityMap map = Main.myMapList.get(counter);
    	   mapShow.setText(map.mPlaces.get(counterPlace).mName+"- "+map.mPlaces.get(counterPlace).mDescription);
    	   setPlace(imageViewPlace);
    	   nextPlace.setVisible(true);
    	   prevPlace.setVisible(true);
       });

  	   prevPlace.setOnAction(e->{
    	   if(counterPlace != 0) {
    		   counterPlace--;
    	   }
    	   CityMap map = Main.myMapList.get(counter);
    	   mapShow.setText(map.mPlaces.get(counterPlace).mName+"- "+map.mPlaces.get(counterPlace).mDescription);
    	   setPlace(imageViewPlace);
    	   nextPlace.setVisible(true);
	 	   prevPlace.setVisible(true);
       });
        showCity.setTranslateY(120);
        showCity.setTranslateX(-170);
        showPlaces.setTranslateY(120);
        showPlaces.setTranslateX(-70);
        prevPlace.setTranslateY(190);
		prevPlace.setTranslateX(-70);
		nextPlace.setTranslateY(160);
		nextPlace.setTranslateX(-70);
        showDescription.setTranslateY(120);
        showDescription.setTranslateX(50);
        showVersion.setTranslateX(180);
        showVersion.setTranslateY(120);
        
		nextM.setTranslateX(100);
		nextM.setTranslateY(50);
		pervM.setTranslateY(50);
		pervM.setTranslateX(-100);
		nextPlace.setVisible(false);
	 	prevPlace.setVisible(false);
		
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

       _result.setBackground(new Background(myBIc2));
  	   _result.getChildren().addAll(showVersion,showDescription,showCity,showPlaces,nextM, pervM, goBack, imageViewPlace, prevPlace, imageViewMap, nextPlace, mapShow, chooseM);

  	   mScene = new Scene(_result, Dimensions.mWith, Dimensions.mheight);
		
	}

    public static void setMap(ImageView aImageView) {
    if (counter<0||counter>Main.myMapList.size()) return;
    CityMap map = Main.myMapList.get(counter);
	aImageView.setImage(new Image(map.mURL));
	mapShow.setText(map.mName);
	
    }

    public void setPlace(ImageView aImageView) {
    	CityMap map = Main.myMapList.get(counter);
    	if (counterPlace < 0 || counterPlace >= map.mPlaces.size()) {
    		return;
    	}    	
    	Place place = map.mPlaces.get(counterPlace);
		aImageView.setImage(new Image(place.mURL));
    }
    

    public static void setDeafulePlace(ImageView aImageView) {
    	aImageView.setImage(new Image("https://scontent.fhfa2-2.fna.fbcdn.net/v/t1.0-9/62406156_2525296037483339_111679483387314176_n.jpg?_nc_cat=110&_nc_ht=scontent.fhfa2-2.fna&oh=e0ca60d86bdb6a893fd0887712c464f7&oe=5D84F941"));
    }
    
    
    
	public static void refreshScene() {
		CityMap map = Main.myMapList.get(counter);
		mapShow.setText(map.mName);
		setMap(imageViewMap);
		
		if (Main.memberlevel == MemLvl.FREE_USER) {

			chooseM.setVisible(false);

		} else if (Main.memberlevel == MemLvl.MEMBER) {

			chooseM.setVisible(true);

		}

		
			else if(Main.memberlevel!=MemLvl.MEMBER && Main.memberlevel!= MemLvl.FREE_USER) {
		}
	}

}
