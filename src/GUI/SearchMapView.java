package GUI;

import com.google.gson.Gson;

import Communication.ClientConsole;
import Defines.Dimensions;
import Defines.SceneName;
import Requests.Request;
import Utils.UI_server_communicate;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
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

    static TextField searchTF = new TextField("Type map to search");

	String request_string;

	public SearchMapView(ClientConsole aChat, UI_server_communicate aCommunicate) {

		super(aChat, aCommunicate);

        BackgroundImage myBIc = new BackgroundImage(new Image("Images\\catalog_up.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        
        CheckBox Search_by_city= new CheckBox("Search by city");
        CheckBox Search_by_inplace= new CheckBox("Search by interested place");
        CheckBox Search_by_general_description= new CheckBox("Search by general description");
        Search_by_city.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        Search_by_inplace.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        Search_by_general_description.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        searchTF.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        Button getBack2=new Button("Go back");
        getBack2.setOnAction(e->{Main.changeScene(SceneName.MAIN);});

        Button search_btn=new Button("Search");
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

        	mCommunicate.ask_server();

        	Main.changeScene(SceneName.SHOW_MAP);

        });

        searchTF.setMaxWidth(300);
        searchTF.setTranslateY(-100);
        Search_by_city.setTranslateY(-250);
        Search_by_city.setTranslateX(-63);
        Search_by_inplace.setTranslateY(-200);
        Search_by_general_description.setTranslateY(-150);
        getBack2.setTranslateX(-100);

        StackPane guestZone = new StackPane();
        guestZone.setBackground(new Background(myBIc));
        guestZone.getChildren().addAll(searchTF, search_btn, getBack2, Search_by_city, Search_by_inplace, Search_by_general_description);

        mScene = new Scene(guestZone, Dimensions.mWith, Dimensions.mheight);
		
	}

	public static void refreshScene() {

	     searchTF.setText("Type map to search");

	}

}
