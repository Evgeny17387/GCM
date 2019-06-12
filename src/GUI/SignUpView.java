package GUI;

import com.google.gson.Gson;
import Communication.ClientConsole;
import DB_classes.AccountUser;
import Defines.API;
import Defines.Dimensions;
import Defines.ErrorCodes;
import Defines.MemLvl;
import Defines.SceneName;
import Requests.Request;
import Utils.UI_server_communicate;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

public class SignUpView extends BaseView {

	static TextField nameR;
	static TextField passwordR;
	static TextField creditCard;
	static TextField email;
	static TextField phone_number;

	public SignUpView(ClientConsole aChat) {

		super(aChat);

	    nameR 		= new TextField();
		passwordR		= new TextField();
	    creditCard 	= new TextField();
	    email 		= new TextField();
	    phone_number 	= new TextField();

        BackgroundImage myBIs = new BackgroundImage(new Image("Images\\sign_up.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        nameR.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        phone_number.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        passwordR.setFont(Font.font("Verdana", FontWeight.BOLD, 12));;
        creditCard.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        email.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        Button signUp2 = new Button("Sign Up");
        signUp2.setOnAction(e->{

        	if (passwordR.getText().isEmpty() || email.getText().isEmpty() || phone_number.getText().isEmpty() || nameR.getText().isEmpty() || creditCard.getText().isEmpty()) {

        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error");
        		alert.setHeaderText("Some fields are missing");
        		alert.setContentText("Please fill all fields");
        		alert.showAndWait();
        		
        	} else {

            	AccountUser accountUser = new AccountUser("FirstName", "LastName", passwordR.getText(), email.getText(), phone_number.getText(), nameR.getText(), creditCard.getText());
            	Request request = new Request(API.ADD_USER, accountUser);
            	String jsonString = mGson.toJson(request);

            	mChat.SendToServer(jsonString);

            	UI_server_communicate.ask_server();

            	if (Main.mServerResponseErrorCode == ErrorCodes.SUCCESS) {

	        		Alert alert = new Alert(AlertType.CONFIRMATION);
	        		alert.setTitle("Congradulations");
	        		alert.setHeaderText("You have been successfuly Singed Up");
	        		alert.setContentText("Please Sing In");
	        		alert.showAndWait();
	        		
	        		Main.changeScene(SceneName.MAIN);

        		} else if (Main.mServerResponseErrorCode == ErrorCodes.USER_ALREADY_EXISTS) {

            		Alert alert = new Alert(AlertType.ERROR);
            		alert.setTitle("Error");
            		alert.setHeaderText("User already exists");
            		alert.setContentText("Please choose another username and try again");
            		alert.showAndWait();

        		} else {

            		Alert alert = new Alert(AlertType.ERROR);
            		alert.setTitle("Error");
            		alert.setHeaderText("An unknown error has occurred");
            		alert.setContentText("Please try again");
            		alert.showAndWait();

            	}

        		Main.mServerResponseErrorCode = ErrorCodes.RESET;

        	}
        	
        });

        nameR.setTranslateY(-100);
        nameR.setMaxWidth(300);
        passwordR.setMaxWidth(300);
        passwordR.setTranslateY(-50);
        creditCard.setMaxWidth(300);
        creditCard.setTranslateY(0);
        email.setMaxWidth(300);
        email.setTranslateY(50);
        phone_number.setMaxWidth(300);
        phone_number.setTranslateY(100);
        signUp2.setTranslateY(150);

        StackPane sign_Up = new StackPane();
        sign_Up.setBackground(new Background(myBIs));
        sign_Up.getChildren().addAll(nameR, passwordR, creditCard, phone_number, email, signUp2, goBack);

        mScene = new Scene(sign_Up, Dimensions.mWith, Dimensions.mheight);
		
	}

	public static void refreshScene() {

		nameR.setText("Please enter username");
		passwordR.setText("Please enter password");
		creditCard.setText("Please enter credit card");
		email.setText("Please enter your email addres");
		phone_number.setText("Please enter your phone number");

	}

}
