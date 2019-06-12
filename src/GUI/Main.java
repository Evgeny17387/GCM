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
import DB_classes.AccountUser;
import DB_classes.AccountWorker;
import DB_classes.CityMap;
import Defines.ErrorCodes;
import Defines.MemLvl;
import Defines.SceneName;

public class Main extends Application {

	private static Map<SceneName, BaseView> scenes = new HashMap<>();

	public static MemLvl memberlevel = MemLvl.FREE_USER;

	public static int mServerResponseErrorCode = ErrorCodes.RESET;

	public static List<CityMap> myMapList;

    public static int Price1 = 1;
    public static int Price2 = 0;

    public static Stage mStage;
    
    public static AccountUser mAccountUser;
    public static AccountWorker mAccountWorker;
    
    public static void main(String[] args) {
		launch(args);
	}

    @Override
	public void start(Stage primaryStage) {

    	mStage = primaryStage;

    	memberlevel = MemLvl.FREE_USER;
    	
    	View view = new View();
    	ClientConsole chat = new ClientConsole("Host", "127.0.0.1", ClientConsole.DEFAULT_PORT, view);
    	UI_server_communicate communicate = new UI_server_communicate();

        scenes.put(SceneName.MAIN, new MainView(chat, communicate));
        scenes.put(SceneName.SIGN_UP, new SignUpView(chat, communicate));
        scenes.put(SceneName.SIGN_IN, new SignInView(chat, communicate));
        scenes.put(SceneName.UPDATE_DETAILS, new UpdateDetailsView(chat, communicate));
        scenes.put(SceneName.WORKER_SIGN_IN, new WorkerView(chat, communicate));
        scenes.put(SceneName.WORKER_ZONE, new WorkerReportsView(chat, communicate));
        scenes.put(SceneName.SEARCH_MAP, new SearchMapView(chat, communicate));
        scenes.put(SceneName.SHOW_MAP, new ShowMapView(chat, communicate));
        scenes.put(SceneName.BUY, new BuyView(chat, communicate));

        changeScene(SceneName.MAIN);

        primaryStage.show();
        
    }

	public static void changeScene(SceneName aSceneName) {
		
		switch (aSceneName) {
			case MAIN:
				MainView.refreshScene();
				break;
			case SIGN_UP:
				SignUpView.refreshScene();
				break;
			case SIGN_IN:
				SignInView.refreshScene();
				break;
			case WORKER_SIGN_IN:
				WorkerView.refreshScene();
				break;
			case SEARCH_MAP:
				SearchMapView.refreshScene();
				break;
			case SHOW_MAP:
				ShowMapView.refreshScene();
				break;
			case BUY:
				BuyView.refreshScene();
				break;
			case UPDATE_DETAILS:
				UpdateDetailsView.refreshScene();
				break;
			default:
			break;
		}

		mStage.setScene(scenes.get(aSceneName).mScene);

	}

}
