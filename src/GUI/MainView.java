package GUI;

import Communication.ClientConsole;
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
	
	public MainView(Stage stage, ClientConsole aChat, UI_server_communicate aCommunicate) {
		super(stage, aChat, aCommunicate);
	}

	public Scene getScene() {
		
        BackgroundImage myBI = new BackgroundImage(new Image("Images\\Background.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Button memBtn = new Button("Sign in");
        Button guestBtn = new Button("Free zone");
        Button signUp = new Button("Sign up");
        Button workers_zone = new Button("Workers zone");

        memBtn.setOnAction(e->stage.setScene(Main.getScenes().get(SceneName.SIGN_IN)));
        guestBtn.setOnAction(e->stage.setScene(Main.getScenes().get(SceneName.SEARCH_MAP)));
        signUp.setOnAction(e->stage.setScene(Main.getScenes().get(SceneName.SIGN_UP)));
        workers_zone.setOnAction(e->stage.setScene(Main.getScenes().get(SceneName.WORKER_ZONE)));

        memBtn.setTranslateY(-250);
        guestBtn.setTranslateY(-200);
        signUp.setTranslateY(-150);
        workers_zone.setTranslateY(-100);

        StackPane root = new StackPane();
        root.setBackground(new Background(myBI));
        root.getChildren().addAll(memBtn, guestBtn, signUp, workers_zone);

        Scene scene = new Scene(root, 1280, 720);
		
		return scene;
	}

}
