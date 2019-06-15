package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import MVC.View;
import Communication.ClientConsole;
import DB_classes.AccountUser;
import DB_classes.AccountWorker;
import DB_classes.CityMap;
import DB_classes.Purchase;
import DB_classes.Purchases;
import Defines.EditLevel;
import Defines.ErrorCodes;
import Defines.MemLvl;
import Defines.SceneName;

public class Main extends Application {

	public static boolean mResposeFromserver = false;

	private static Map<SceneName, BaseView> scenes = new HashMap<>();

	public static MemLvl memberlevel = MemLvl.FREE_USER;
	public static EditLevel editlevel = EditLevel.ZERO;

	public static int mServerResponseErrorCode = ErrorCodes.RESET;

	public static List<CityMap> myMapList;

    public static int Price1 = 1;
    public static int Price2 = 0;

    public static Stage mStage;
    
    public static AccountUser mAccountUser;
    public static AccountWorker mAccountWorker;

    public static List<Purchases> mPurchases;

	static String ip;

    public static void main(String[] args) {
    	
        try
        {
        	ip = args[0];
        }
        catch(Throwable e)
        {
        	ip = "127.0.0.1";
        }

    	launch(args);

    }

    @Override
	public void start(Stage primaryStage) {

    	mStage = primaryStage;
    	memberlevel = MemLvl.FREE_USER;
    	editlevel = EditLevel.ZERO;
    	
    	View view = new View();

    	ClientConsole chat = new ClientConsole("Host", ip, ClientConsole.DEFAULT_PORT, view);
        
    	scenes.put(SceneName.MAIN, new MainView(chat));
        scenes.put(SceneName.SIGN_UP, new SignUpView(chat));
        scenes.put(SceneName.SIGN_IN, new SignInView(chat));
        scenes.put(SceneName.UPDATE_DETAILS, new UpdateDetailsView(chat));
        scenes.put(SceneName.WORKER_SIGN_IN, new WorkerView(chat));
        scenes.put(SceneName.WORKER_ZONE, new WorkerReportsView(chat));
        scenes.put(SceneName.SEARCH_MAP, new SearchMapView(chat));
        scenes.put(SceneName.SHOW_MAP, new ShowMapView(chat));
        scenes.put(SceneName.BUY, new BuyView(chat));
        scenes.put(SceneName.EDIT, new EditView(chat));

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
			case WORKER_ZONE:
				WorkerReportsView.refreshScene();
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
			case EDIT:
				EditView.refreshScene();
			default:
			break;
		}

		mStage.setScene(scenes.get(aSceneName).mScene);

	}

}
