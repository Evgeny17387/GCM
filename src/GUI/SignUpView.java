package GUI;

import com.google.gson.Gson;
import Communication.ClientConsole;
import Constants.API;
import Constants.SceneName;
import DB_classes.AccountUser;
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

    TextField nameR 		= new TextField();
	TextField passwordR		= new TextField();
    TextField creditCard 	= new TextField();
    TextField email 		= new TextField();
    TextField phone_number 	= new TextField();

	public SignUpView(Stage stage, ClientConsole aChat, UI_server_communicate aCommunicate) {
		super(stage, aChat, aCommunicate);
	}

	public Scene getScene() {
		
		clean_tf();

    	Gson gson = new Gson();

        nameR.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        phone_number.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        passwordR.setFont(Font.font("Verdana", FontWeight.BOLD, 12));;
        creditCard.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        email.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        BackgroundImage myBIs = new BackgroundImage(new Image("Images\\sign_up.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        StackPane sign_Up = new StackPane();
        sign_Up.setBackground(new Background(myBIs));

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

        Button signUp2 = new Button("Sign up");
        signUp2.setTranslateY(150);
        signUp2.setOnAction(e->{

        	AccountUser accountUser = new AccountUser("FirstName", "LastName", passwordR.getText(), email.getText(), phone_number.getText(), nameR.getText(), creditCard.getText());
        	Request request = new Request(API.ADD_USER, accountUser);
        	String jsonString = gson.toJson(request);

        	mChat.SendToServer(jsonString);
        	mCommunicate.ask_server();
        	System.out.println("its the flag " + Main.my_flag);

        	if (Main.my_flag==0) {

        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error Dialog");
        		alert.setHeaderText("User already exists, please choose another username");
        		alert.setContentText("Ooops, there was an error!");
        		alert.showAndWait();

        		Main.my_flag = -1;

        	} else if (Main.my_flag==1) {

        		Alert alert = new Alert(AlertType.CONFIRMATION);
        		alert.setTitle("Confirmation Dialog");
        		alert.setHeaderText("Congradulations !!! you have been successfuly singed in");
        		alert.setContentText("O.K.");
        		alert.showAndWait();

        		Main.my_flag = -1;

        		clean_tf();
        		
        		stage.setScene(Main.getScenes().get(SceneName.MAIN));

        	} else if (Main.my_flag==2) {
        		
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error Dialog");
        		alert.setHeaderText("Some fields are missing, please fill all fields");
        		alert.setContentText("Ooops, there was an error!");
        		alert.showAndWait();
        		
        		Main.my_flag = -1;

        	}
        	
        	System.out.println("this is the after "+ Main.my_flag);

        });

        Button getBack3 = new Button("Go back");
        getBack3.setTranslateY(150);
        getBack3.setTranslateX(-100);
        getBack3.setOnAction(e->{stage.setScene(Main.getScenes().get(SceneName.MAIN));clean_tf();});

        sign_Up.getChildren().addAll(nameR, passwordR, creditCard, phone_number, email, signUp2, getBack3);

        Scene scene = new Scene(sign_Up, 1280,720);
		
		return scene;
	}

    public  void clean_tf() {

    	nameR.setText("Please enter username");
	     passwordR.setText("Please enter password");
	     creditCard.setText("Please enter credit card");
	     email.setText("Please enter your email addres");
	     phone_number.setText("Please enter your phone number");

    }

}
