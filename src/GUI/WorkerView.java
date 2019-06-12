package GUI;

import com.google.gson.Gson;
import Communication.ClientConsole;
import Defines.API;
import Defines.Dimensions;
import Defines.SceneName;
import Requests.GeneralRequest;
import Requests.Request;
import Utils.UI_server_communicate;
import javafx.scene.Scene;
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

public class WorkerView extends BaseView {

    static TextField nameW;
    static TextField passwordW;

	public WorkerView(ClientConsole aChat, UI_server_communicate aCommunicate) {

		super(aChat, aCommunicate);

	    nameW = new TextField();
	    passwordW = new TextField();

        BackgroundImage myBIW = new BackgroundImage(new Image("Images\\signInIm.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        nameW.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        passwordW.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        Button getBack6 = new Button("Go back");
        getBack6.setOnAction(e->{Main.changeScene(SceneName.MAIN);});

        Button nextW = new Button("Next");
        nextW.setOnAction(e->{
            
        	GeneralRequest accountCheck = new GeneralRequest(nameW.getText(), passwordW.getText());

        	Request request = new Request(API.GET_WORKER, accountCheck);
        	String jsonString = mGson.toJson(request);

        	mChat.SendToServer(jsonString);

        	mCommunicate.ask_server();

        });

        nameW.setTranslateY(-50);
        nameW.setMaxWidth(400);
        passwordW.setMaxWidth(400);
        getBack6.setTranslateY(100);
        getBack6.setTranslateX(-100);
        nextW.setTranslateY(100);

        StackPane workersZone = new StackPane();
        workersZone.setBackground(new Background(myBIW));
        workersZone.getChildren().addAll(nameW, passwordW, nextW ,getBack6);

        mScene = new Scene(workersZone, Dimensions.mWith, Dimensions.mheight);
		
	}

	public static void refreshScene() {

        nameW.setText("Please enter worker name");
        passwordW.setText("Please enter worker password");

	}

}
