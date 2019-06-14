package GUI;

import com.google.gson.Gson;
import Communication.ClientConsole;
import DB_classes.AccountUser;
import Defines.API;
import Defines.Dimensions;
import Defines.ErrorCodes;
import Defines.MemLvl;
import Defines.SceneName;
import Dialogs.MessageDialog;
import Dialogs.ProgressForm;
import Requests.Request;
import javafx.concurrent.Task;
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

	static TextField mUserName;
	static TextField mPassword;
	static TextField mCreditCard;
	static TextField mEmail;
	static TextField mPhonenumber;
	static TextField mFirstName;
	static TextField mLastName;

	public SignUpView(ClientConsole aChat) {

		super(aChat);
		
		// Init

		mUserName 		= new TextField();
		mPassword		= new TextField();
		mCreditCard 	= new TextField();
		mEmail 			= new TextField();
		mPhonenumber 	= new TextField();
		mFirstName 		= new TextField();
		mLastName 		= new TextField();

        mUserName.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        mPhonenumber.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        mPassword.setFont(Font.font("Verdana", FontWeight.BOLD, 12));;
        mCreditCard.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        mEmail.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        mFirstName.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        mLastName.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

		// OnClick

        Button signUp2 = new Button("Sign Up");
        signUp2.setOnAction(e->{

        	if (
        			mPassword.getText().isEmpty() ||
        			mEmail.getText().isEmpty() ||
        			mPhonenumber.getText().isEmpty() ||
        			mUserName.getText().isEmpty() ||
        			mUserName.getText().isEmpty() ||
        			mFirstName.getText().isEmpty() ||
        			mLastName.getText().isEmpty()
        		) {

        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error");
        		alert.setHeaderText("Some fields are missing");
        		alert.setContentText("Please fill all fields");
        		alert.showAndWait();
        		
        	} else {

            	AccountUser accountUser = new AccountUser(mFirstName.getText(), mLastName.getText(), mPassword.getText(), mEmail.getText(), mPhonenumber.getText(), mUserName.getText(), mCreditCard.getText());
            	Request request = new Request(API.ADD_USER, accountUser);
            	String jsonString = mGson.toJson(request);
            	mChat.SendToServer(jsonString);

                ProgressForm progressForm = new ProgressForm();
                Task<Void> waitTask = new Dialogs.WaitTask();
                progressForm.activateProgressBar(waitTask);

                waitTask.setOnSucceeded(event -> {

                	progressForm.getDialogStage().close();
                	signUp2.setDisable(false);
                    goBack.setDisable(false);

                	if (Main.mServerResponseErrorCode == ErrorCodes.SUCCESS) {

    	        		MessageDialog alert = new MessageDialog(AlertType.INFORMATION, "Congradulations", "You have been successfuly Singed Up", "Please Sing In");
    	        		alert.showAndWait();
    	        		
    	        		Main.changeScene(SceneName.MAIN);

            		} else if (Main.mServerResponseErrorCode == ErrorCodes.USER_ALREADY_EXISTS) {

    	        		MessageDialog alert = new MessageDialog(AlertType.ERROR, "Error", "User already exists", "Please choose another username and try again");
                		alert.showAndWait();

            		} else {

    	        		MessageDialog alert = new MessageDialog(AlertType.ERROR, "Error", "An unknown error has occurred", "Please try again");
                		alert.showAndWait();

                	}

            		Main.mServerResponseErrorCode = ErrorCodes.RESET;

                });

                signUp2.setDisable(true);
                goBack.setDisable(true);

                progressForm.getDialogStage().show();

                Thread thread = new Thread(waitTask);
                thread.start();

        	}
        	
        });

		// UI position

        mUserName.setMaxWidth(Dimensions.mSignUpViewTextWidth);
        mPassword.setMaxWidth(Dimensions.mSignUpViewTextWidth);
        mCreditCard.setMaxWidth(Dimensions.mSignUpViewTextWidth);
        mEmail.setMaxWidth(Dimensions.mSignUpViewTextWidth);
        mPhonenumber.setMaxWidth(Dimensions.mSignUpViewTextWidth);
        mFirstName.setMaxWidth(Dimensions.mSignUpViewTextWidth);
        mLastName.setMaxWidth(Dimensions.mSignUpViewTextWidth);

        mFirstName.setTranslateY(-200);
        mLastName.setTranslateY(-150);
        mUserName.setTranslateY(-100);
        mPassword.setTranslateY(-50);
        mCreditCard.setTranslateY(0);
        mEmail.setTranslateY(50);
        mPhonenumber.setTranslateY(100);

        signUp2.setTranslateY(150);

        // Scene
        
        StackPane sign_Up = new StackPane();
        sign_Up.setBackground(new Background(myBIs1));
        sign_Up.getChildren().addAll(mFirstName, mLastName, mUserName, mPassword, mCreditCard, mEmail, mPhonenumber, signUp2, goBack);

        mScene = new Scene(sign_Up, Dimensions.mWith, Dimensions.mheight);
		
	}

	public static void refreshScene() {

		mFirstName.setText("Please enter firstname");
		mLastName.setText("Please enter lastname");
		mUserName.setText("Please enter username");
		mPassword.setText("Please enter password");
		mCreditCard.setText("Please enter credit card");
		mEmail.setText("Please enter your email addres");
		mPhonenumber.setText("Please enter your phone number");

	}

}
