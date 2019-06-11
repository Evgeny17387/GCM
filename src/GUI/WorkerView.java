package GUI;

import com.google.gson.Gson;
import Communication.ClientConsole;
import Constants.API;
import Constants.SceneName;
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

public class WorkerView {

	private Stage stage;

	ClientConsole mChat;
	UI_server_communicate mCommunicate;

    TextField nameW = new TextField("Please enter worker name");
    TextField passwordW = new TextField("Please enter worker password");

	public WorkerView(Stage stage, ClientConsole aChat, UI_server_communicate aCommunicate) {
		this.stage = stage;
		this.mChat = aChat;
		this.mCommunicate = aCommunicate;
	}

	public Scene getScene() {
		
		clean_tf();

    	Gson gson = new Gson();

        StackPane workersZone = new StackPane();

        BackgroundImage myBIW= new BackgroundImage(new Image("Images\\signInIm.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        workersZone.setBackground(new Background(myBIW));

        nameW.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        passwordW.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        nameW.setTranslateY(-50);
        nameW.setMaxWidth(400);

        passwordW.setMaxWidth(400);

        Button getBack6 = new Button("Go back");
        getBack6.setOnAction(e->{stage.setScene(Main.getScenes().get(SceneName.MAIN));clean_tf();});

        getBack6.setTranslateY(100);
        getBack6.setTranslateX(-100);

        Button nextW = new Button("Next");
        nextW.setTranslateY(100);
        nextW.setOnAction(e->{
            
        	GeneralRequest accountCheck = new GeneralRequest(nameW.getText(), passwordW.getText());

        	Request request = new Request(API.GET_WORKER, accountCheck);
        	String jsonString = gson.toJson(request);

        	mChat.SendToServer(jsonString);

        	mCommunicate.ask_server();

        });

        workersZone.getChildren().addAll(nameW, passwordW, nextW ,getBack6);

        Scene scene = new Scene(workersZone, 1280,720);
		
		return scene;
	}

    public  void clean_tf() {

        nameW.setText("Please enter worker name");
        passwordW.setText("Please enter worker password");

    }

}
