package GUI;

import Communication.ClientConsole;
import Defines.API;
import Defines.Constants;
import Defines.Dimensions;
import Defines.ErrorCodes;
import Defines.MemLvl;
import Defines.SceneName;
import Dialogs.MessageDialog;
import Dialogs.ProgressForm;
import Requests.GeneralRequest;
import Requests.Request;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import GUI.MainView;

public class SignInView extends BaseView {

    static TextField mName = new TextField("Please enter username");
    static TextField mPassword = new TextField("Please enter password");

	public SignInView(ClientConsole aChat) {

		super(aChat);

		mName.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		mPassword.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        Button btn = new Button("Sign In");
        btn.setOnAction(e->{

        	if (mPassword.getText().isEmpty() || mName.getText().isEmpty()) {

        		Alert alert = new Alert(AlertType.ERROR);

        		alert.setTitle("Error");
        		alert.setHeaderText("Some fields are missing");
        		alert.setContentText("Please fill all fields");

        		alert.showAndWait();

        	} else {

	        	GeneralRequest accountCheck = new GeneralRequest(mName.getText(), mPassword.getText());
	        	Request request = new Request(API.GET_USER, accountCheck);
	        	String jsonString = mGson.toJson(request);
	        	mChat.SendToServer(jsonString);

                ProgressForm progressForm = new ProgressForm();
                Task<Void> waitTask = new Dialogs.WaitTask();
                progressForm.activateProgressBar(waitTask);

                waitTask.setOnSucceeded(event -> {

                	progressForm.getDialogStage().close();
                    btn.setDisable(false);

                    if (Main.mServerResponseErrorCode == ErrorCodes.SUCCESS) {

    	        		MessageDialog alert = new MessageDialog(AlertType.INFORMATION, "Welcome", "You are successfully signed in..", "You are welcome to visit the Map Catalog");
    	        		alert.showAndWait();

    	        		Main.memberlevel = MemLvl.MEMBER;

    	        		Main.changeScene(SceneName.MAIN);

    	        	} else if (Main.mServerResponseErrorCode == ErrorCodes.USER_NOT_FOUND) {

    	        		MessageDialog alert = new MessageDialog(AlertType.ERROR, "Error", "User details are incorrect", "Please try again");
    	        		alert.showAndWait();
    	
    	    		} else {

    	        		MessageDialog alert = new MessageDialog(AlertType.ERROR, "Error", "An unknown error has occurred, please try again", "Please try again");
    	        		alert.showAndWait();
    	
    	        	}
    	
    	    		Main.mServerResponseErrorCode = ErrorCodes.RESET;

                });

                btn.setDisable(true);

                progressForm.getDialogStage().show();

                Thread thread = new Thread(waitTask);
                thread.start();

        	}

        });

        mName.setMaxWidth(Dimensions.mSignInViewTextWidth);
        mPassword.setMaxWidth(Dimensions.mSignInViewTextWidth);

        mName.setTranslateY(-50);
        mPassword.setTranslateY(0);
        btn.setTranslateY(100);

        StackPane memberZone = new StackPane();
        memberZone.setBackground(new Background(myBIW)); 
        memberZone.getChildren().addAll(mName, mPassword, goBack, btn);
        
        mScene = new Scene(memberZone, Dimensions.mWith, Dimensions.mheight);

	}

	public static void refreshScene() {
		mName.setText("Please enter username");
		mPassword.setText("Please enter password");
    }
    
}
