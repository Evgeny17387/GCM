package GUI;

import com.google.gson.Gson;
import Communication.ClientConsole;
import Defines.API;
import Defines.Dimensions;
import Defines.ErrorCodes;
import Defines.MemLvl;
import Defines.MemLvlWorkerdb;
import Defines.SceneName;
import Dialogs.MessageDialog;
import Dialogs.ProgressForm;
import Requests.GeneralRequest;
import Requests.Request;
import javafx.concurrent.Task;
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

public class WorkerView extends BaseView {

    static TextField mName;
    static TextField mPassword;

	public WorkerView(ClientConsole aChat) {

		super(aChat);

		// Init

		mName = new TextField();
		mPassword = new TextField();

        mName.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        mPassword.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        // OnClick

        Button singIn = new Button("Sign In");
        singIn.setOnAction(e->{

        	if (mName.getText().isEmpty() || mPassword.getText().isEmpty()) {

        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error");
        		alert.setHeaderText("Some fields are missing");
        		alert.setContentText("Please fill all fields");
        		alert.showAndWait();
        		
        	} else {

            	GeneralRequest accountCheck = new GeneralRequest(mName.getText(), mPassword.getText());
            	Request request = new Request(API.GET_WORKER, accountCheck);
            	String jsonString = mGson.toJson(request);
            	mChat.SendToServer(jsonString);

                ProgressForm progressForm = new ProgressForm();
                Task<Void> waitTask = new Dialogs.WaitTask();
                progressForm.activateProgressBar(waitTask);

                waitTask.setOnSucceeded(event -> {

                	progressForm.getDialogStage().close();
                	singIn.setDisable(false);
                    goBack.setDisable(false);

    	        	if (Main.mServerResponseErrorCode == ErrorCodes.SUCCESS) {

    	        		MessageDialog alert = new MessageDialog(AlertType.INFORMATION, "Welcome", "You are successfully signed in..", "You are welcome to visit the Reports");
    	        		alert.showAndWait();

    	        		MemLvlWorkerdb.UpdateWorkerLevel();
    	        		
    	        		Main.changeScene(SceneName.MAIN);
    	
    	        	} else if (Main.mServerResponseErrorCode == ErrorCodes.USER_NOT_FOUND) {
    	        		
    	        		MessageDialog alert = new MessageDialog(AlertType.ERROR, "Error", "Worker details are incorrect", "Please try again");
    	        		alert.showAndWait();
    	
    	    		} else {

    	        		MessageDialog alert = new MessageDialog(AlertType.ERROR, "Error", "An unknown error has occurred", "Please try again");
    	        		alert.showAndWait();
    	
    	        	}
    	
    	    		Main.mServerResponseErrorCode = ErrorCodes.RESET;

                });

                singIn.setDisable(true);
                goBack.setDisable(true);

                progressForm.getDialogStage().show();

                Thread thread = new Thread(waitTask);
                thread.start();
	    		
        	}

        });

		// Position in UI

        mName.setMaxWidth(Dimensions.mWorkerViewTextWidth);
        mPassword.setMaxWidth(Dimensions.mWorkerViewTextWidth);

        mName.setTranslateY(-50);
        singIn.setTranslateY(100);

        // Scene
        
        StackPane stackPane = new StackPane();
        stackPane.setBackground(new Background(myBIW));
        stackPane.getChildren().addAll(mName, mPassword, singIn ,goBack);

        mScene = new Scene(stackPane, Dimensions.mWith, Dimensions.mheight);
		
	}

	public static void refreshScene() {

        mName.setText("Please enter worker name");
        mPassword.setText("Please enter worker password");

	}

}
