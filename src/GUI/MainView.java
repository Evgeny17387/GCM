package GUI;

import Communication.ClientConsole;
import Defines.Dimensions;
import Defines.MemLvl;
import Defines.SceneName;
import Utils.UI_server_communicate;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainView extends BaseView {

    static StackPane mStackPane;

    static Button memBtn = new Button("Sign in");
    static Button outBtn = new Button("Sign out");
    static Button guestBtn = new Button("To catalog");
    static Button signUp = new Button("Sign up");
    static Button workers_zone = new Button("Workers zone");

    static Button mUpdateDetails;

	public MainView(ClientConsole aChat, UI_server_communicate aCommunicate) {

		super(aChat, aCommunicate);

        // Init

		mStackPane = new StackPane();
		
		mUpdateDetails = new Button("Update User Details");

        // OnClick

        memBtn.setOnAction(e->
        	Main.changeScene(SceneName.SIGN_IN)
        );

        outBtn.setOnAction(e->{
        	Main.memberlevel = MemLvl.FREE_USER;
        	Main.changeScene(SceneName.MAIN);
        });

        guestBtn.setOnAction(e->
        	Main.changeScene(SceneName.SEARCH_MAP)
        );

        signUp.setOnAction(e->
        	Main.changeScene(SceneName.SIGN_UP)
        );

        workers_zone.setOnAction(e->
        	Main.changeScene(SceneName.WORKER_ZONE)
        );

        mUpdateDetails.setOnAction(e->
        	Main.changeScene(SceneName.UPDATE_DETAILS)
        );

        // UI position
        
        outBtn.setTranslateY(-200);
        guestBtn.setTranslateY(-150);
        memBtn.setTranslateY(-100);
        signUp.setTranslateY(-50);
        workers_zone.setTranslateY(0);
        
        mUpdateDetails.setTranslateY(50);
        mUpdateDetails.setMaxWidth(300);

        // Scene

        mStackPane.getChildren().addAll(outBtn, guestBtn, memBtn, signUp, workers_zone, mUpdateDetails);
        mStackPane.setBackground(new Background(myBI));

        mScene = new Scene(mStackPane, Dimensions.mWith, Dimensions.mheight);
	}

	public static void refreshScene() {

		if (Main.memberlevel == MemLvl.FREE_USER) {

			mStackPane.setBackground(new Background(myBI));

			outBtn.setVisible(false);
			guestBtn.setVisible(true);
			memBtn.setVisible(true);
			signUp.setVisible(true);
			workers_zone.setVisible(true);
			mUpdateDetails.setVisible(false);

		} else if (Main.memberlevel == MemLvl.MEMBER) {

			mStackPane.setBackground(new Background(myBIs));

			outBtn.setVisible(true);
			guestBtn.setVisible(true);
			memBtn.setVisible(false);
			signUp.setVisible(false);
			workers_zone.setVisible(false);
			mUpdateDetails.setVisible(true);

		}

	}

}
