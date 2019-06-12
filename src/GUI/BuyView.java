package GUI;

import Communication.ClientConsole;
import Constants.MemLvl;
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

public class BuyView extends BaseView{
	public BuyView(Stage stage, ClientConsole aChat, UI_server_communicate aCommunicate) {
		super(stage, aChat, aCommunicate);
	}
	public static String myCity;
	StackPane root=new StackPane();
    BackgroundImage myBIc = new BackgroundImage(new Image("Images\\Buy.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    Button oneTime= new Button();
    Button periodTime=new Button();
    Button goBack=new Button("Go back");
    
    
	public Scene getScene() {
		goBack.setOnAction(e->{stage.setScene(Main.getScenes().get(SceneName.SEARCH_MAP));});
		goBack.setTranslateY(-150);
		periodTime.setTranslateY(-200);
		oneTime.setTranslateY(-250);
		root.setBackground(new Background(myBIc));
		System.out.println(myCity);
		oneTime.setText("One Time Pruchase- Price"+ GUI.Main.Price1+"$, Maps of "+myCity);
		periodTime.setText("Pruchase for 6 months - Price"+GUI.Main.Price2+ "$,Maps of "+myCity);
		root.getChildren().addAll(oneTime,periodTime,goBack);
	  	   Scene scene =new Scene(root, 1280,720);
	  	   return scene;
	
	
	}
	
	
	
	
	}


