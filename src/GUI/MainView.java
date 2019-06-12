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

    static Button mSignUp = new Button("User Sign Up");
    static Button mUserSignIn = new Button("Sign In");
    static Button mUpdateDetails;
    static Button mSignOut = new Button("Sign Out");
    static Button mCatalog = new Button("Catalog");
    static Button mWorkerSignIn = new Button("Worker Sign In");
    static Button mWorkersZone;

	public MainView(ClientConsole aChat, UI_server_communicate aCommunicate) {

		super(aChat, aCommunicate);

        // Init

		mStackPane = new StackPane();
		
		mUpdateDetails = new Button("User Details");
		mWorkersZone = new Button("Worker Zone");

        // OnClick

        mSignUp.setOnAction(e->
    		Main.changeScene(SceneName.SIGN_UP)
        );

		mUserSignIn.setOnAction(e->
        	Main.changeScene(SceneName.SIGN_IN)
        );

        mUpdateDetails.setOnAction(e->
    		Main.changeScene(SceneName.UPDATE_DETAILS)
        );

        mSignOut.setOnAction(e->{
        	Main.memberlevel = MemLvl.FREE_USER;
        	Main.changeScene(SceneName.MAIN);
        });

		mCatalog.setOnAction(e->
        	Main.changeScene(SceneName.SEARCH_MAP)
        );

        mWorkerSignIn.setOnAction(e->
        	Main.changeScene(SceneName.WORKER_SIGN_IN)
        );

        mWorkersZone.setOnAction(e->
    		Main.changeScene(SceneName.WORKER_ZONE)
        );
        
        // UI position

        mSignOut.setMaxWidth(Dimensions.mMainViewButtonsWidth);
        mCatalog.setMaxWidth(Dimensions.mMainViewButtonsWidth);
        mUserSignIn.setMaxWidth(Dimensions.mMainViewButtonsWidth);
        mSignUp.setMaxWidth(Dimensions.mMainViewButtonsWidth);
        mWorkerSignIn.setMaxWidth(Dimensions.mMainViewButtonsWidth);
        mUpdateDetails.setMaxWidth(Dimensions.mMainViewButtonsWidth);
        mWorkersZone.setMaxWidth(Dimensions.mMainViewButtonsWidth);

        int top = -100;
        int interval = 50;
        
        mSignUp.setTranslateY(top);
        mUserSignIn.setTranslateY(top + 1*interval);
        mCatalog.setTranslateY(top + 2*interval);
        mWorkerSignIn.setTranslateY(top + 3*interval);

        mUpdateDetails.setTranslateY(top);
        mSignOut.setTranslateY(top + 1*interval);
        
        mWorkersZone.setTranslateY(top);

        // Scene

        mStackPane.getChildren().addAll(mSignOut, mCatalog, mUserSignIn, mSignUp, mWorkerSignIn, mUpdateDetails, mWorkersZone);
        mStackPane.setBackground(new Background(myBI));

        mScene = new Scene(mStackPane, Dimensions.mWith, Dimensions.mheight);
	}

	public static void refreshScene() {

		if (Main.memberlevel == MemLvl.FREE_USER) {

	    	Main.mStage.setTitle("GCM - Free User");

			mStackPane.setBackground(new Background(myBI));

			mSignUp.setVisible(true);
			mUserSignIn.setVisible(true);
			mCatalog.setVisible(true);
			mWorkerSignIn.setVisible(true);

			mUpdateDetails.setVisible(false);
			mSignOut.setVisible(false);
			mWorkersZone.setVisible(false);

		} else if (Main.memberlevel == MemLvl.MEMBER) {

	    	Main.mStage.setTitle("GCM - Member");

			mStackPane.setBackground(new Background(myBIs));

			mUpdateDetails.setVisible(true);
			mSignOut.setVisible(true);
			mCatalog.setVisible(true);

			mSignUp.setVisible(false);
			mUserSignIn.setVisible(false);
			mWorkerSignIn.setVisible(false);
			mWorkersZone.setVisible(false);

		} else if (Main.memberlevel == MemLvl.MANAGER || Main.memberlevel == MemLvl.EDITOR_MANAGER || Main.memberlevel == MemLvl.WORKER) {
		
			Main.mStage.setTitle("GCM - " + Main.mAccountWorker.mType);
		
			mStackPane.setBackground(new Background(myBIs));

			mWorkersZone.setVisible(true);
			mSignOut.setVisible(true);
			mCatalog.setVisible(true);

			mUpdateDetails.setVisible(false);
			mSignUp.setVisible(false);
			mUserSignIn.setVisible(false);
			mWorkerSignIn.setVisible(false);
		
		}

	}

}
