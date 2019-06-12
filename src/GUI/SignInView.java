package GUI;

import com.google.gson.Gson;

import Communication.ClientConsole;
import Constants.API;
import Constants.MemLvl;
import Constants.SceneName;
import Requests.GeneralRequest;
import Requests.Request;
import Utils.UI_server_communicate;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import GUI.MainView;

public class SignInView extends BaseView {

    TextField name = new TextField();
    TextField password = new TextField();

	public SignInView(Stage stage, ClientConsole aChat, UI_server_communicate aCommunicate) {
		super(stage, aChat, aCommunicate);
	}

	public Scene getScene() {
		
		clean_tf();

    	Gson gson = new Gson();

        BackgroundImage myBIW= new BackgroundImage(new Image("Images\\signInIm.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        name.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        password.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        Button getBack = new Button("Go back");
        getBack.setOnAction(e->{stage.setScene(Main.getScenes().get(SceneName.MAIN));clean_tf();});

        Button btn = new Button("Next");
        btn.setOnAction(e->{
        	
        	GeneralRequest accountCheck = new GeneralRequest(name.getText(), password.getText());
        	Request request = new Request(API.GET_USER, accountCheck);
        	String jsonString = gson.toJson(request);
        	mChat.SendToServer(jsonString);
        	mCommunicate.ask_server();

        	if (Main.my_flag==0) {

        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error ");
        		alert.setHeaderText("User details are incorrect");
        		alert.setContentText("Please try again");
        		alert.showAndWait();

        		Main.my_flag = -1;

        	} else if (Main.my_flag==1) {

        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error");
        		alert.setHeaderText("Some fields are missing, please fill all fields");
        		alert.setContentText("Please try again");
        		alert.showAndWait();

        		Main.my_flag = -1;

        	} else if (Main.my_flag==2) {
        		
        		Alert alert = new Alert(AlertType.CONFIRMATION);
        		alert.setTitle("Welcome");
        		alert.setHeaderText("Welcome Back !!!");
        		alert.setContentText("You are successfully signed in..");
        		alert.showAndWait();
        		Main.memberlevel=MemLvl.MEMBER;   
        		Main.my_flag = -1;
        		clean_tf();
        		MainView.changeScene();
        		ShowMapView.changeScene();
        		stage.setScene(Main.getScenes().get(SceneName.MAIN));

        	}
        	
        });

        name.setMaxWidth(400);
        name.setTranslateY(-50);
        password.setMaxWidth(400);
        getBack.setTranslateY(100);
        getBack.setTranslateX(-100);
        btn.setTranslateY(100);
        StackPane memberZone = new StackPane();
        memberZone.setBackground(new Background(myBIW)); 
        memberZone.getChildren().addAll(name, password, getBack, btn);
        Scene scene = new Scene(memberZone, 1280,720);
		return scene;
	}

    public  void clean_tf() {
	     name.setText("Please enter username");
	     password.setText("Please enter password");
    }

}
