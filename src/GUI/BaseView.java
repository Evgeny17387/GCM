package GUI;

import com.google.gson.Gson;

import Communication.ClientConsole;
import Utils.UI_server_communicate;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class BaseView {

    static BackgroundImage myBI = new BackgroundImage(new Image("Images\\Background.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    static BackgroundImage myBIs = new BackgroundImage(new Image("Images\\Background_sign.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    static BackgroundImage myBIW = new BackgroundImage(new Image("Images\\signInIm.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    
	ClientConsole mChat;
	UI_server_communicate mCommunicate;
	
	Gson mGson;
	
	public Scene mScene;

	public BaseView(ClientConsole aChat, UI_server_communicate aCommunicate) {

		this.mChat = aChat;
		this.mCommunicate = aCommunicate;

	    mGson = new Gson();

	}

}
