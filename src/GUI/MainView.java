package GUI;

import Communication.ClientConsole;
import Constants.MemLvl;
import Constants.SceneName;
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

    static StackPane root = new StackPane();

    static BackgroundImage myBI = new BackgroundImage(new Image("Images\\Background.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    static BackgroundImage myBIs = new BackgroundImage(new Image("Images\\Background_sign.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

    static Button memBtn = new Button("Sign in");
    static Button outBtn = new Button("Sign out");
    static Button guestBtn = new Button("To catalog");
    static Button signUp = new Button("Sign up");
    static Button workers_zone = new Button("Workers zone");

	public MainView(ClientConsole aChat, UI_server_communicate aCommunicate) {

		super(aChat, aCommunicate);

        memBtn.setOnAction(e->Main.changeScene(SceneName.SIGN_IN));

        outBtn.setOnAction(e->{

        	Main.memberlevel = MemLvl.FREE_USER;
        	Main.changeScene(SceneName.MAIN);

        });

        guestBtn.setOnAction(e->Main.changeScene(SceneName.SEARCH_MAP));

        signUp.setOnAction(e->Main.changeScene(SceneName.SIGN_UP));

        workers_zone.setOnAction(e->Main.changeScene(SceneName.WORKER_ZONE));

        outBtn.setTranslateY(-200);
        guestBtn.setTranslateY(-150);
        memBtn.setTranslateY(-100);
        signUp.setTranslateY(-50);
        workers_zone.setTranslateY(0);

        root.getChildren().addAll(outBtn, guestBtn, memBtn, signUp, workers_zone);
        root.setBackground(new Background(myBI));

        mScene = new Scene(root, 1280, 720);
	}

	public static void refreshScene() {

		if (Main.memberlevel == MemLvl.FREE_USER) {

			root.setBackground(new Background(myBI));

			outBtn.setVisible(false);
			guestBtn.setVisible(true);
			memBtn.setVisible(true);
			signUp.setVisible(true);
			workers_zone.setVisible(true);

		} else if (Main.memberlevel == MemLvl.MEMBER) {

			root.setBackground(new Background(myBIs));

			outBtn.setVisible(true);
			guestBtn.setVisible(true);
			memBtn.setVisible(false);
			signUp.setVisible(false);
			workers_zone.setVisible(false);

		}

	}

}
