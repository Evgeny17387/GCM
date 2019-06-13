package GUI;

import com.google.gson.Gson;

import Communication.ClientConsole;
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
import javafx.scene.control.TextField;
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

    static TextField searchTF;

    static CheckBox Search_by_city;
    static CheckBox Search_by_inplace ;
    static CheckBox Search_by_general_description;

	String request_string;

	public SearchMapView(ClientConsole aChat) {

		super(aChat);

		// Init
		
        BackgroundImage myBIc = new BackgroundImage(new Image("Images\\catalog_up.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        searchTF = new TextField("Type map to search");

        Search_by_city = new CheckBox("Search by city");
        Search_by_inplace= new CheckBox("Search by interested place");
        Search_by_general_description = new CheckBox("Search by general description");

        Search_by_city.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        Search_by_inplace.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        Search_by_general_description.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        searchTF.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        Button search_btn = new Button("Search");

		// OnClick

        search_btn.setOnAction(e->{

        	if((Search_by_city.isSelected()&&Search_by_inplace.isSelected())||(Search_by_city.isSelected()&&Search_by_general_description.isSelected())||Search_by_inplace.isSelected()&&Search_by_general_description.isSelected())
        		return;

        	String searchKey = searchTF.getText();

        	if(Search_by_city.isSelected())
        		request_string = "MapSearch_city_key";
        	else if(Search_by_inplace.isSelected())
        		request_string = "MapSearch_place_key";
        	else if(Search_by_general_description.isSelected())
        		request_string = "MapSearch_desc_key";

        	Request request = new Request(request_string, searchKey);
        	String jsonString = mGson.toJson(request);
        	mChat.SendToServer(jsonString);

            ProgressForm progressForm = new ProgressForm();
            Task<Void> waitTask = new Dialogs.WaitTask();
            progressForm.activateProgressBar(waitTask);

            waitTask.setOnSucceeded(event -> {

            	progressForm.getDialogStage().close();
            	search_btn.setDisable(false);
                goBack.setDisable(false);

            	if (Main.mServerResponseErrorCode == ErrorCodes.SUCCESS) {

                	Main.changeScene(SceneName.SHOW_MAP);

        		} else {

            		MessageDialog alert = new MessageDialog(AlertType.ERROR, "Error", "An unknown error has occurred, please try again", "Please try again");
            		alert.showAndWait();

            	}

        		Main.mServerResponseErrorCode = ErrorCodes.RESET;

            });

            search_btn.setDisable(true);
            goBack.setDisable(true);

            progressForm.getDialogStage().show();

            Thread thread = new Thread(waitTask);
            thread.start();
    		
        });

		// UI

        searchTF.setMaxWidth(300);

        searchTF.setTranslateY(-100);
        Search_by_city.setTranslateY(-250);
        Search_by_city.setTranslateX(-63);
        Search_by_inplace.setTranslateY(-200);
        Search_by_general_description.setTranslateY(-150);

        // Scene
        
        StackPane guestZone = new StackPane();
        guestZone.setBackground(new Background(myBIc));
        guestZone.getChildren().addAll(searchTF, search_btn, goBack, Search_by_city, Search_by_inplace, Search_by_general_description);

        mScene = new Scene(guestZone, Dimensions.mWith, Dimensions.mheight);
		
	}

	public static void refreshScene() {

	     searchTF.setText("Type map to search");
	     
	     Search_by_city.setSelected(false);
	     Search_by_inplace.setSelected(false);
	     Search_by_general_description.setSelected(false);

	}

}
