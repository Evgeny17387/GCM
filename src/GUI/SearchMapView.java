package GUI;

import com.google.gson.Gson;

import Communication.ClientConsole;
import Defines.API;
import Defines.Dimensions;
import Defines.ErrorCodes;
import Defines.SceneName;
import Dialogs.MessageDialog;
import Dialogs.ProgressForm;
import Requests.Request;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SearchMapView extends BaseView {

    static TextField mSearchKey;

	static RadioButton mSearchByCity;
	static RadioButton SearchByPlace;

	static String mRequest;

	public SearchMapView(ClientConsole aChat) {

		super(aChat);

		// Init
		
		mSearchKey = new TextField("Type map to search");

        mSearchByCity = new RadioButton("Search by city");
        SearchByPlace = new RadioButton("Search by interesting place");

		ToggleGroup group = new ToggleGroup();
		mSearchByCity.setToggleGroup(group);
		SearchByPlace.setToggleGroup(group);

		mSearchByCity.setSelected(true);

		mSearchByCity.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		SearchByPlace.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		mSearchKey.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        Button searchButton = new Button("Search");

		// OnClick

        searchButton.setOnAction(e->{

        	if (mSearchKey.getText().isEmpty()) {

        		MessageDialog alert = new MessageDialog(AlertType.ERROR, "Error", "Search key must not be empty", "Please fill up the key");
        		alert.showAndWait();

        	}else {

            	String searchKey = mSearchKey.getText();
            	            	
            	if(mSearchByCity.isSelected())
            		mRequest = API.SEARCH_BY_CITY;
            	else if(SearchByPlace.isSelected())
            		mRequest = API.SEARCH_BY_PLACE;

            	Request request = new Request(mRequest, searchKey);
            	String jsonString = mGson.toJson(request);
            	mChat.SendToServer(jsonString);

                ProgressForm progressForm = new ProgressForm();
                Task<Void> waitTask = new Dialogs.WaitTask();
                progressForm.activateProgressBar(waitTask);

                waitTask.setOnSucceeded(event -> {

                	progressForm.getDialogStage().close();
                	searchButton.setDisable(false);
                    goBack.setDisable(false);

                	if (Main.mServerResponseErrorCode == ErrorCodes.SUCCESS) {

                		MessageDialog alert = new MessageDialog(AlertType.INFORMATION, "Success", Main.myMapList.size() + " Maps were found", "Enjoy");
                		alert.showAndWait();

                    	Main.changeScene(SceneName.SHOW_MAP);

                	} else if (Main.mServerResponseErrorCode == ErrorCodes.NO_MAPS_FOUND) {
                		
                		MessageDialog alert = new MessageDialog(AlertType.INFORMATION, "Sorry", "No maps were found for this ley", "Please try a different key");
                		alert.showAndWait();
                		
                	} else {

                		MessageDialog alert = new MessageDialog(AlertType.ERROR, "Error", "An unknown error has occurred", "Please try again");
                		alert.showAndWait();

                	}

            		Main.mServerResponseErrorCode = ErrorCodes.RESET;

                });

                searchButton.setDisable(true);
                goBack.setDisable(true);

                progressForm.getDialogStage().show();

                Thread thread = new Thread(waitTask);
                thread.start();

        	}
    		
        });

		// UI

        mSearchKey.setMaxWidth(300);

        mSearchKey.setTranslateY(-100);
        mSearchByCity.setTranslateY(-250);
        mSearchByCity.setTranslateX(-63);
        SearchByPlace.setTranslateY(-200);

        // Scene
        
        StackPane stackPane = new StackPane();
        stackPane.setBackground(new Background(myBIc));
        stackPane.getChildren().addAll(mSearchKey, searchButton, goBack, mSearchByCity, SearchByPlace);

        mScene = new Scene(stackPane, Dimensions.mWith, Dimensions.mheight);

	}

	public static void refreshScene() {

//	     searchTF.setText("Type map to search");
		mSearchKey.setText("Haifa");

//	     Search_by_city.setSelected(false);
	     mSearchByCity.setSelected(true);

	     SearchByPlace.setSelected(false);

	}

}
