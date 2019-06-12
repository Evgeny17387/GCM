package GUI;

import Communication.ClientConsole;
import DB_classes.Purchase;
import DB_classes.Purchases;
import Defines.API;
import Defines.Dimensions;
import Defines.ErrorCodes;
import Defines.MemLvl;
import Defines.MemLvlWorkerdb;
import Defines.SceneName;
import Requests.GeneralRequest;
import Requests.Request;
import Utils.UI_server_communicate;
import ViewsItem.PurchaseView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;

public class WorkerReportsView extends BaseView {

    static TableView<PurchaseView> table;

    static ObservableList<PurchaseView> data;

	public WorkerReportsView(ClientConsole aChat, UI_server_communicate aCommunicate) {

		super(aChat, aCommunicate);

        // Init Table

        TableColumn usernameColumn = new TableColumn("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<PurchaseView, String>("username"));

        TableColumn cityColumn = new TableColumn("City");
        cityColumn.setCellValueFactory(new PropertyValueFactory<PurchaseView, String>("city"));

        TableColumn dateColumn = new TableColumn("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<PurchaseView, String>("date"));

        table = new TableView<PurchaseView>();
        table.setEditable(false);
        table.getColumns().addAll(cityColumn, dateColumn, usernameColumn);

		// Position in UI

        cityColumn.setMinWidth(145);
        dateColumn.setMinWidth(145);
        usernameColumn.setMinWidth(145);
        table.setMaxWidth(Dimensions.mWorkerReportsViewTableWidth);
		table.setMaxHeight(Dimensions.mWorkerReportsViewTableheight);

		// Scene
		
	    StackPane stackPane = new StackPane();
	    stackPane.setBackground(new Background(myBI));
	    stackPane.getChildren().addAll(table);

        mScene = new Scene(stackPane, Dimensions.mWith, Dimensions.mheight);

	}

	public static void refreshScene() {

    	GeneralRequest accountCheck = new GeneralRequest(Main.mAccountWorker.mFirstName, Main.mAccountWorker.mPassword);
    	Request request = new Request(API.GET_USERS_PURCHASES, accountCheck);
    	String jsonString = mGson.toJson(request);

    	mChat.SendToServer(jsonString);

    	mCommunicate.ask_server();

    	if (Main.mServerResponseErrorCode == ErrorCodes.SUCCESS) {
    		
    	    data = FXCollections.observableArrayList();

    	    for (Purchases purchases : Main.mPurchases) {
    		    for (Purchase purchase : purchases.mPurchases) {
    		    	data.add(new PurchaseView(purchase.mCityName, purchase.mDate, purchase.mUserName));
    		    }
    	    }

            table.setItems(data);

    	} else if (Main.mServerResponseErrorCode == ErrorCodes.WORKER_NOT_MANAGER) {

    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("You are not authorized");
    		alert.setContentText("You must be a Manger to watch reports");
    		alert.showAndWait();

		} else {

    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("An unknown error has occurred");
    		alert.setContentText("Please try again");
    		alert.showAndWait();

    	}

	}

}
