package GUI;

import com.google.gson.Gson;

import Communication.ClientConsole;
import Defines.Dimensions;
import Defines.SceneName;
import Utils.UI_server_communicate;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class BaseView {

    static BackgroundImage myBI = new BackgroundImage(new Image("Images\\Background.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    static BackgroundImage myBIs = new BackgroundImage(new Image("Images\\Background_sign.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    static BackgroundImage myBIW = new BackgroundImage(new Image("Images\\signInIm.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    
	static ClientConsole mChat;
	
	static Gson mGson;
	
	public Scene mScene;

	static Button goBack;
	
	public BaseView(ClientConsole aChat) {

		this.mChat = aChat;

	    mGson = new Gson();

        goBack = new Button("Go Back");
        goBack.setOnAction(e->Main.changeScene(SceneName.MAIN));

        goBack.setMaxHeight(Dimensions.mBaseViewButtonsHeight);
        goBack.setMaxWidth(Dimensions.mBaseViewButtonsWidth);
        
        goBack.setTranslateY(Dimensions.mheight / 2 - Dimensions.mBaseViewButtonsHeight);
        goBack.setTranslateX( - Dimensions.mWith / 2 + Dimensions.mBaseViewButtonsWidth);

	}

}
