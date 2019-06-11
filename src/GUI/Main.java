package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import Requests.Request;
import Utils.UI_server_communicate;

import MVC.View;

import Communication.ClientConsole;

import DB_classes.CityMap;
import DB_classes.Place;

import Constants.SceneName;

public class Main extends Application {
	
	private static Map<SceneName, Scene> scenes = new HashMap<>();

	String request_string;

	/****Scenes declare****/

	Stage window;
	Scene result;
	Scene guestScene;
	Scene catalogScene;
	
    /****Textfields declare****/

	TextField mapShow=new TextField();
    TextField searchTF=new TextField("Type map to search");
    TextField searchTF2=new TextField("Type map to search");
    
	/**** ****/

    static int my_flag = -1;
    public static List<CityMap> myMapList;

    static int counter = 0;
    static int counterPlace = 0;

    /***Clean all  textfields function***/
    
    public  void clean_tf() {
    	     searchTF.setText("Type map to search");
    	     searchTF2.setText("Type map to search");
    }
    	
	public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) {

    	// Initiation

    	Gson gson = new Gson();

    	View view = new View();

    	ClientConsole chat = new ClientConsole("Host", "127.0.0.1", ClientConsole.DEFAULT_PORT, view);

    	UI_server_communicate communicate = new UI_server_communicate();

    	// UI stuff

        primaryStage.setTitle("GCM");

    	window = primaryStage;

        scenes.put(SceneName.MAIN, new MainView(primaryStage).getScene());
        scenes.put(SceneName.SIGN_UP, new SignUpView(primaryStage, chat, communicate).getScene());
        scenes.put(SceneName.SIGN_IN, new SignInView(primaryStage, chat, communicate).getScene());
        scenes.put(SceneName.WORKER_ZONE, new WorkerView(primaryStage, chat, communicate).getScene());

        primaryStage.setScene(scenes.get(SceneName.MAIN));

        primaryStage.show();

        /****Textfields edit****/
        searchTF.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        searchTF2.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        mapShow.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        /****Checkboxs declare ***/
        CheckBox Search_by_city= new CheckBox("Search by city");
        CheckBox Search_by_inplace= new CheckBox("Search by interested place");
        CheckBox Search_by_general_description= new CheckBox("Search by general description");
        Search_by_city.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        Search_by_inplace.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        Search_by_general_description.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        CheckBox Search_by_city2= new CheckBox("Search by city");
        CheckBox Search_by_inplace2= new CheckBox("Search by interested place");
        CheckBox Search_by_general_description2= new CheckBox("Search by general description");
        Search_by_city2.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        Search_by_inplace2.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        Search_by_general_description2.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        
        /****Buttons declare****/
        Button chooseM=new Button();
        Button nextM=new Button();
        Button pervM=new Button();
        Button next = new Button();
        Button getBackU=new Button();
        Button search = new Button();
        Button getBack2=new Button();
        Button getBack4=new Button();
        Button getBack11=new Button();
        Button search_btn=new Button();
        Button search_btn2=new Button();
        
        /**** Buttons Text ****/
        chooseM.setText("Choose this map");
        nextM.setText("Next map");
        pervM.setText("Pervious map");
        next.setText("Next");
        search_btn.setText("Search");
        search_btn2.setText("Search");
        getBack4.setText("Go back");
        getBack2.setText("Go back");
        getBack11.setText("Go back");
        search.setText("Search");
        getBackU.setText("Go back");

        /****Buttons actions****/

        getBackU.setOnAction(e->{window.setScene(Main.getScenes().get(SceneName.MAIN));clean_tf();});
        getBack2.setOnAction(e->{window.setScene(Main.getScenes().get(SceneName.MAIN));clean_tf();});
        getBack4.setOnAction(e->{window.setScene(Main.getScenes().get(SceneName.MAIN));clean_tf();});
        getBack11.setOnAction(e->{window.setScene(Main.getScenes().get(SceneName.MAIN));clean_tf();});

        /****background zone****/

        BackgroundImage myBIc = new BackgroundImage(new Image("Images\\catalog_up.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        
        /****Scene declare***/
      
        StackPane _catalog = new StackPane();
        _catalog.setBackground(new Background(myBIc));
        searchTF2.setMaxWidth(300);
        _catalog.getChildren().add(searchTF2);
        _catalog.getChildren().add(search_btn2);
        _catalog.getChildren().add(getBackU);
        _catalog.getChildren().add(Search_by_city2);
        _catalog.getChildren().add(Search_by_inplace2);
        _catalog.getChildren().add(Search_by_general_description2);
        Search_by_city2.setTranslateY(-250);
        Search_by_city2.setTranslateX(-63);
        Search_by_inplace2.setTranslateY(-200);
        Search_by_general_description2.setTranslateY(-150);
        searchTF2.setTranslateY(-100);
        getBackU.setTranslateX(-100);
        catalogScene=new Scene(_catalog,1280,720);

        StackPane guestZone = new StackPane();
        guestZone.setBackground(new Background(myBIc));
        searchTF.setMaxWidth(300);
        guestZone.getChildren().add(searchTF);
        guestZone.getChildren().add(search_btn);
        guestZone.getChildren().add(getBack2);
        guestZone.getChildren().add(Search_by_city);
        guestZone.getChildren().add(Search_by_inplace);
        guestZone.getChildren().add(Search_by_general_description);
        Search_by_city.setTranslateY(-250);
        Search_by_city.setTranslateX(-63);
        Search_by_inplace.setTranslateY(-200);
        Search_by_general_description.setTranslateY(-150);
        searchTF.setTranslateY(-100);
        getBack2.setTranslateX(-100);
        guestScene=new Scene(guestZone,1280,720);

        StackPane _result=new StackPane();
        _result.setBackground(new Background(myBIc));
        mapShow.setMaxWidth(500);
        mapShow.setTranslateY(0);
  	   _result.getChildren().add(mapShow);
  	   _result.getChildren().add(nextM);
  	   _result.getChildren().add(pervM);
  	   _result.getChildren().add(chooseM);
  	   _result.getChildren().add(getBack11);
  	   nextM.setTranslateX(100);
  	   nextM.setTranslateY(50);
  	   chooseM.setTranslateY(50);
  	   pervM.setTranslateY(50);
  	   chooseM.setTranslateX(0);
  	   getBack11.setTranslateY(100);
  	   pervM.setTranslateX(-100);

  	   ImageView imageViewMap = new ImageView(new Image("https://www.myyellowknifenow.com/wp-content/uploads/2019/02/photographer-698908_960_720.jpg"));	
  	   imageViewMap.setTranslateX(-100);
  	   imageViewMap.setTranslateY(-150);
  	   imageViewMap.setFitHeight(200);
  	   imageViewMap.setFitWidth(350);
  	   imageViewMap.setPreserveRatio(true);
       _result.getChildren().add(imageViewMap);

  	   ImageView imageViewPlace = new ImageView(new Image("https://www.myyellowknifenow.com/wp-content/uploads/2019/02/photographer-698908_960_720.jpg"));
  	   imageViewPlace.setTranslateX(150);
  	   imageViewPlace.setTranslateY(-150);
  	   imageViewPlace.setFitHeight(100);
  	   imageViewPlace.setFitWidth(150);
  	   imageViewPlace.setPreserveRatio(true);
       _result.getChildren().add(imageViewPlace);

       Button nextPlace = new Button();
       nextPlace.setText("Next");
       nextPlace.setTranslateY(-50);
       nextPlace.setTranslateX(200);
  	   _result.getChildren().add(nextPlace);
       nextPlace.setOnAction(e->{
    	   if(counterPlace != myMapList.get(counter).mPlaces.size()-1) {
    		   counterPlace++;
    	   }
    	   setPlace(imageViewPlace);
       });

       Button prevPlace = new Button();
       prevPlace.setText("Previos");
       prevPlace.setTranslateY(-50);
       prevPlace.setTranslateX(100);
  	   _result.getChildren().add(prevPlace);
  	   prevPlace.setOnAction(e->{
    	   if(counterPlace != 0) {
    		   counterPlace--;
    	   }
    	   setPlace(imageViewPlace);
       });

  	   result = new Scene(_result,1280,720);
        
        nextM.setOnAction(e->{
        	if(counter!=myMapList.size()-1)counter++;
        	setMap(imageViewMap);
        });
        pervM.setOnAction(e->{
        	if(counter!=0)counter--;
        	setMap(imageViewMap);
        });
        
        
        search_btn.setOnAction(e->{

        	if((Search_by_city.isSelected()&&Search_by_inplace.isSelected())||(Search_by_city.isSelected()&&Search_by_general_description.isSelected())||Search_by_inplace.isSelected()&&Search_by_general_description.isSelected())
        		return;

        	String searchKey = searchTF.getText();

        	if(Search_by_city.isSelected())
        		request_string = "MapSearch_city_key";
        	else if(Search_by_inplace.isSelected())
        		request_string = "MapSearch_place_key";
        	else if(Search_by_general_description.isSelected())
        		request_string = "MapSearch_desc_key";

        	Request request = new Request(request_string, searchKey);
        	String jsonString = gson.toJson(request);

        	chat.SendToServer(jsonString);

        	communicate.ask_server();
        	setMap(imageViewMap);


        });
    }

    public static void setFlag(int flag) {
    	my_flag=flag;
    }

    public void setMap(ImageView aImageView) {
    if (counter<0||counter>myMapList.size()) return;
    CityMap map = myMapList.get(counter);
	System.out.println("good" +map.toString());
	aImageView.setImage(new Image(map.mURL));
	mapShow.setText(map.toString());
    window.setScene(result);}

    public void setPlace(ImageView aImageView) {
    	CityMap map = myMapList.get(counter);
    	if (counterPlace < 0 || counterPlace >= map.mPlaces.size()) {
    		return;
    	}
    	Place place = map.mPlaces.get(counterPlace);
		aImageView.setImage(new Image(place.mURL));
    }

	public static Map<SceneName, Scene> getScenes() {
		return scenes;
	}

}
