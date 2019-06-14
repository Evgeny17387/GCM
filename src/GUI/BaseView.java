package GUI;

import com.google.gson.Gson;

import Communication.ClientConsole;
import Defines.Dimensions;
import Defines.SceneName;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class BaseView {

    static BackgroundImage myBI;
    static BackgroundImage myBIs;
    BackgroundImage myBIW;
    BackgroundImage myBIc;
    BackgroundImage myBIs1;
    BackgroundImage myBIc1;
    BackgroundImage myBIe;

    static ClientConsole mChat;
	
	static Gson mGson;
	
	public Scene mScene;

	static Button goBack;
	
	public BaseView(ClientConsole aChat) {

	    myBI = new BackgroundImage(new Image("/Images/Background.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    myBIs = new BackgroundImage(new Image("/Images/Background_sign.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    myBIW = new BackgroundImage(new Image("/Images/signInIm.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        myBIc = new BackgroundImage(new Image("/Images/catalog.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        myBIs1 = new BackgroundImage(new Image("/Images/sign_up.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    	myBIc1 = new BackgroundImage(new Image("/Images/Buy.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        myBIe = new BackgroundImage(new Image("/Images/edit.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

		this.mChat = aChat;

	    mGson = new Gson();

        goBack = new Button("Go Back");
        goBack.setOnAction(e->{Main.changeScene(SceneName.MAIN);});

        goBack.setMaxHeight(Dimensions.mBaseViewButtonsHeight);
        goBack.setMaxWidth(Dimensions.mBaseViewButtonsWidth);
        
        goBack.setTranslateY(Dimensions.mheight / 2 - Dimensions.mBaseViewButtonsHeight);
        goBack.setTranslateX( - Dimensions.mWith / 2 + Dimensions.mBaseViewButtonsWidth);

	}

}
