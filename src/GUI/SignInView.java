package GUI;

import com.google.gson.Gson;

import Communication.ClientConsole;
import Defines.API;
import Defines.Dimensions;
import Defines.ErrorCodes;
import Defines.MemLvl;
import Defines.SceneName;
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

    static TextField name = new TextField("Please enter username");
    static TextField password = new TextField("Please enter password");

	public SignInView(ClientConsole aChat) {

		super(aChat);

        name.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        password.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        Button btn = new Button("Sign In");
        btn.setOnAction(e->{

        	if (password.getText().isEmpty() || name.getText().isEmpty()) {

        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error");
        		alert.setHeaderText("Some fields are missing");
        		alert.setContentText("Please fill all fields");
        		alert.showAndWait();
        		
        	} else {

	        	GeneralRequest accountCheck = new GeneralRequest(name.getText(), password.getText());
	        	Request request = new Request(API.GET_USER, accountCheck);
	        	String jsonString = mGson.toJson(request);
	        	mChat.SendToServer(jsonString);

	        	UI_server_communicate.ask_server();
	
	        	if (Main.mServerResponseErrorCode == ErrorCodes.SUCCESS) {
	
	        		Alert alert = new Alert(AlertType.CONFIRMATION);
	        		alert.setTitle("Welcome");
	        		alert.setHeaderText("You are successfully signed in..");
	        		alert.setContentText("You are welcome to visit the Map Catalog");
	        		alert.showAndWait();

	        		Main.memberlevel = MemLvl.MEMBER;

	        		Main.changeScene(SceneName.MAIN);
	
	        	} else if (Main.mServerResponseErrorCode == ErrorCodes.USER_NOT_FOUND) {
	        		
	        		Alert alert = new Alert(AlertType.ERROR);
	        		alert.setTitle("Error");
	        		alert.setHeaderText("User details are incorrect");
	        		alert.setContentText("Please try again");
	        		alert.showAndWait();
	
	    		} else {
	
	        		Alert alert = new Alert(AlertType.ERROR);
	        		alert.setTitle("Error");
	        		alert.setHeaderText("An unknown error has occurred, please try again");
	        		alert.setContentText("Please try again");
	        		alert.showAndWait();
	
	        	}
	
	    		Main.mServerResponseErrorCode = ErrorCodes.RESET;

        	}

        });

        name.setMaxWidth(Dimensions.mSignInViewTextWidth);
        password.setMaxWidth(Dimensions.mSignInViewTextWidth);

        name.setTranslateY(-50);
        btn.setTranslateY(100);

        StackPane memberZone = new StackPane();
        memberZone.setBackground(new Background(myBIW)); 
        memberZone.getChildren().addAll(name, password, goBack, btn);
        
        mScene = new Scene(memberZone, Dimensions.mWith, Dimensions.mheight);

	}

	public static void refreshScene() {
	     name.setText("Please enter username");
	     password.setText("Please enter password");
    }

}
