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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;

public class WorkerReportsView extends BaseView {

    static TableView<PurchaseView> table;

    static ObservableList<PurchaseView> data;

	public WorkerReportsView(ClientConsole aChat) {

		super(aChat);
        
        // Init Table

        TableColumn usernameColumn = new TableColumn("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<PurchaseView, String>("username"));

        TableColumn cityColumn = new TableColumn("City");
        cityColumn.setCellValueFactory(new PropertyValueFactory<PurchaseView, String>("city"));

        TableColumn dateColumn = new TableColumn("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<PurchaseView, String>("date"));

        table = new TableView<PurchaseView>();
        table.setEditable(false);
        table.getColumns().addAll(usernameColumn, cityColumn, dateColumn);
        
		// Position in UI

        cityColumn.setMinWidth(145);
        dateColumn.setMinWidth(145);
        usernameColumn.setMinWidth(145);
        table.setMaxWidth(Dimensions.mWorkerReportsViewTableWidth);
		table.setMaxHeight(Dimensions.mWorkerReportsViewTableheight);

		// Scene
		
	    StackPane stackPane = new StackPane();
	    stackPane.setBackground(new Background(myBI));
	    stackPane.getChildren().addAll(table, goBack);

        mScene = new Scene(stackPane, Dimensions.mWith, Dimensions.mheight);

	}

	public static void refreshScene() {

	    data = FXCollections.observableArrayList();

	    for (Purchases purchases : Main.mPurchases) {
		    for (Purchase purchase : purchases.mPurchases) {
		    	data.add(new PurchaseView(purchase.mCityName, purchase.mDate, purchase.mUserName));
		    }
	    }

        table.setItems(data);

	}

}
