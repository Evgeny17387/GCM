package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Utils.UI_server_communicate;
import MVC.View;
import Communication.ClientConsole;
import DB_classes.CityMap;
import Constants.ErrorCodes;
import Constants.SceneName;
import Constants.MemLvl;

public class Main extends Application {

	private static Map<SceneName, Scene> scenes = new HashMap<>();

	public static MemLvl memberlevel = MemLvl.FREE_USER;

	public static int mServerResponseErrorCode = ErrorCodes.RESET;

	public static List<CityMap> myMapList;

	public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) {

    	View view = new View();
    	ClientConsole chat = new ClientConsole("Host", "127.0.0.1", ClientConsole.DEFAULT_PORT, view);
    	UI_server_communicate communicate = new UI_server_communicate();

    	primaryStage.setTitle("GCM");

        scenes.put(SceneName.MAIN, new MainView(primaryStage, chat, communicate).getScene());
        scenes.put(SceneName.SIGN_UP, new SignUpView(primaryStage, chat, communicate).getScene());
        scenes.put(SceneName.SIGN_IN, new SignInView(primaryStage, chat, communicate).getScene());
        scenes.put(SceneName.WORKER_ZONE, new WorkerView(primaryStage, chat, communicate).getScene());
        scenes.put(SceneName.SEARCH_MAP, new SearchMapView(primaryStage, chat, communicate).getScene());
        scenes.put(SceneName.SHOW_MAP, new ShowMapView(primaryStage, chat, communicate).getScene());

        primaryStage.setScene(scenes.get(SceneName.MAIN));

        primaryStage.show();
        
    }

	public static Map<SceneName, Scene> getScenes() {
		return scenes;
	}

}
