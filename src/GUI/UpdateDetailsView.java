package GUI;

import Communication.ClientConsole;
import DB_classes.AccountUser;
import Defines.API;
import Defines.Dimensions;
import Defines.ErrorCodes;
import Defines.SceneName;
import Requests.Request;
import Utils.UI_server_communicate;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;

public class UpdateDetailsView extends BaseView {

    static TextField mUserName;
    static TextField mPassword;
    static TextField mEmail;
    static TextField mPhoneNumber;
    static TextField mCreditCard;
    static TextField mFirstName;
    static TextField mLastName;

	public UpdateDetailsView(ClientConsole aChat, UI_server_communicate aCommunicate) {

		super(aChat, aCommunicate);

		// Init
		
		mUserName = new TextField();
		mUserName.setEditable(false);
		mUserName.setPromptText("UserName");

		mPassword = new TextField();
		mPassword.setPromptText("Password");

		mEmail = new TextField();
		mEmail.setEditable(true);
		mEmail.setPromptText("Email");

		mPhoneNumber = new TextField();
		mPhoneNumber.setEditable(true);
		mPhoneNumber.setPromptText("PhoneNumber");

		mCreditCard = new TextField();
		mCreditCard.setEditable(true);
		mCreditCard.setPromptText("CreditCard");

		mFirstName = new TextField();
		mFirstName.setEditable(true);
		mFirstName.setPromptText("FirstName");

		mLastName = new TextField();
		mLastName.setEditable(true);
		mLastName.setPromptText("LastName");

        Button goBack = new Button("Back to Main");

        Button update = new Button("Update User Details");

		// OnClick

        goBack.setOnAction(e->
        	Main.changeScene(SceneName.MAIN)
        );

        update.setOnAction(e->{
        	
        	System.out.println("1 - " + Main.mAccountUser.mUserName);
        	System.out.println("2 - " + mPassword.getText());
        	System.out.println("3 - " + mEmail.getText());
        	System.out.println("4 - " + mPhoneNumber.getText());
        	System.out.println("5 - " + mFirstName.getText());
        	System.out.println("6 - " + mLastName.getText());
        	System.out.println("7 - " + mCreditCard.getText());
        	
        	if (
        			mUserName.getText().isEmpty() ||
        			mPassword.getText().isEmpty() ||
        			mEmail.getText().isEmpty() ||
        			mPhoneNumber.getText().isEmpty() ||
        			mFirstName.getText().isEmpty() ||
        			mLastName.getText().isEmpty() ||
        			mCreditCard.getText().isEmpty()
        		) {
    		
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error");
        		alert.setHeaderText("Some fields are missing");
        		alert.setContentText("Please fill all fields");
        		alert.showAndWait();
        		
        	} else {

            	AccountUser accountUser = new AccountUser(mFirstName.getText(), mLastName.getText(), mPassword.getText(), mEmail.getText(), mPhoneNumber.getText(), mUserName.getText(), mCreditCard.getText());
            	Request request = new Request(API.UPDATE_USER, accountUser);
            	String jsonString = mGson.toJson(request);

            	mChat.SendToServer(jsonString);
            	mCommunicate.ask_server();

            	if (Main.mServerResponseErrorCode == ErrorCodes.SUCCESS) {

	        		Alert alert = new Alert(AlertType.CONFIRMATION);
	        		alert.setTitle("Congradulations");
	        		alert.setHeaderText("Your details have been updated");
	        		alert.setContentText("");
	        		alert.showAndWait();
	        		
	        		refreshScene();

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

		// Position in UI
		
		mUserName.setTranslateY(-250);
		mUserName.setMaxWidth(300);
		
		mPassword.setTranslateY(-200);
		mPassword.setMaxWidth(300);

		mEmail.setTranslateY(-200);
		mEmail.setMaxWidth(300);

		mPhoneNumber.setTranslateY(-150);
		mPhoneNumber.setMaxWidth(300);

		mFirstName.setTranslateY(-100);
		mFirstName.setMaxWidth(300);

		mLastName.setTranslateY(-50);
		mLastName.setMaxWidth(300);

		update.setTranslateY(0);
		update.setMaxWidth(200);

		goBack.setTranslateY(50);
		goBack.setMaxWidth(100);

		// Scene
		
	    StackPane stackPane = new StackPane();
	    stackPane.setBackground(new Background(myBI));
	    stackPane.getChildren().addAll(mUserName, mPassword, mEmail, mPhoneNumber, mFirstName, mLastName, update, goBack);

        mScene = new Scene(stackPane, Dimensions.mWith, Dimensions.mheight);

	}

	public static void refreshScene() {

	    mUserName.setText(Main.mAccountUser.mUserName);
	    mPassword.setText(Main.mAccountUser.mPassword);
	    mEmail.setText(Main.mAccountUser.mEmail);
	    mPhoneNumber.setText(Main.mAccountUser.mPhoneNumber);
	    mFirstName.setText(Main.mAccountUser.mFirstName);
	    mLastName.setText(Main.mAccountUser.mLastName);
	    mCreditCard.setText(Main.mAccountUser.mCreditCard);
		
	}

}
