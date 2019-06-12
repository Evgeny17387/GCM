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
	
	public MainView(Stage stage, ClientConsole aChat, UI_server_communicate aCommunicate) {
		super(stage, aChat, aCommunicate);
	}
	
    static StackPane root = new StackPane();
    static Button memBtn = new Button("Sign in");
    static Button outBtn = new Button("Sign out");
    static Button guestBtn = new Button("To catalog");
    static Button signUp = new Button("Sign up");
    static Button workers_zone = new Button("Workers zone");
    
    
    
    
    static BackgroundImage myBI = new BackgroundImage(new Image("Images\\Background.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    static BackgroundImage myBIs = new BackgroundImage(new Image("Images\\Background_sign.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

	public Scene getScene() {
		        

        memBtn.setOnAction(e->stage.setScene(Main.getScenes().get(SceneName.SIGN_IN)));
        outBtn.setOnAction(e->{Main.memberlevel=MemLvl.FREE_USER;changeScene();ShowMapView.changeScene();;stage.setScene(Main.getScenes().get(SceneName.MAIN));});
        guestBtn.setOnAction(e->stage.setScene(Main.getScenes().get(SceneName.SEARCH_MAP)));
        signUp.setOnAction(e->stage.setScene(Main.getScenes().get(SceneName.SIGN_UP)));
        workers_zone.setOnAction(e->stage.setScene(Main.getScenes().get(SceneName.WORKER_ZONE)));

        memBtn.setTranslateY(-250);
        outBtn.setTranslateY(-250);
        guestBtn.setTranslateY(-200);
        signUp.setTranslateY(-150);
        workers_zone.setTranslateY(-100);

        root.getChildren().addAll(memBtn, guestBtn, signUp, workers_zone);
        root.setBackground(new Background(myBI));


        Scene scene = new Scene(root, 1280, 720);
		
		return scene;
	}
	public static void changeScene() {
	
		if(Main.memberlevel==MemLvl.FREE_USER) {
			   System.out.println("im here now");
	           root.setBackground(new Background(myBI));
	    	   root.getChildren().clear();
	    	   root.getChildren().addAll(memBtn, guestBtn, signUp, workers_zone);
		}
		if(Main.memberlevel==MemLvl.MEMBER) {
    	   root.getChildren().clear();
           root.setBackground(new Background(myBIs));
    	   root.getChildren().addAll(outBtn, guestBtn);
       }
}


}
