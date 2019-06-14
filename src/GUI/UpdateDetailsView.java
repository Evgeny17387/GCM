package GUI;

import java.awt.Label;

import Communication.ClientConsole;
import DB_classes.AccountUser;
import DB_classes.Purchase;
import Defines.API;
import Defines.Dimensions;
import Defines.ErrorCodes;
import Defines.SceneName;
import Dialogs.MessageDialog;
import Dialogs.ProgressForm;
import Requests.Request;
import ViewsItem.PurchaseView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UpdateDetailsView extends BaseView {

    static TextField mUserName;
    static TextField mPassword;
    static TextField mEmail;
    static TextField mPhoneNumber;
    static TextField mCreditCard;
    static TextField mFirstName;
    static TextField mLastName;

    static TableView<PurchaseView> table;

    static ObservableList<PurchaseView> data;

	public UpdateDetailsView(ClientConsole aChat) {

		super(aChat);

		// Init
		
		mUserName = new TextField();
		mUserName.setEditable(false);
		mUserName.setPromptText("UserName");

		mPassword = new TextField();
		mPassword.setPromptText("Password");

		mEmail = new TextField();
		mEmail.setEditable(true);
		mEmail.setPromptText("Email");

		mPhoneNumber = new TextField();
		mPhoneNumber.setEditable(true);
		mPhoneNumber.setPromptText("PhoneNumber");

		mCreditCard = new TextField();
		mCreditCard.setEditable(true);
		mCreditCard.setPromptText("CreditCard");

		mFirstName = new TextField();
		mFirstName.setEditable(true);
		mFirstName.setPromptText("FirstName");

		mLastName = new TextField();
		mLastName.setEditable(true);
		mLastName.setPromptText("LastName");

        Button update = new Button("Update User Details");

        // Init Table

        TableColumn cityColumn = new TableColumn("City");
        cityColumn.setCellValueFactory(new PropertyValueFactory<PurchaseView, String>("city"));

        TableColumn dateColumn = new TableColumn("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<PurchaseView, String>("date"));

        TableColumn typeColumn = new TableColumn("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<PurchaseView, String>("type"));

        table = new TableView<PurchaseView>();
        table.setEditable(false);
        table.getColumns().addAll(cityColumn, dateColumn, typeColumn);
        
        // OnClick

        update.setOnAction(e->{
        	
        	if (
        			mUserName.getText().isEmpty() ||
        			mPassword.getText().isEmpty() ||
        			mEmail.getText().isEmpty() ||
        			mPhoneNumber.getText().isEmpty() ||
        			mFirstName.getText().isEmpty() ||
        			mLastName.getText().isEmpty() ||
        			mCreditCard.getText().isEmpty()
        		) {
    		
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error");
        		alert.setHeaderText("Some fields are missing");
        		alert.setContentText("Please fill all fields");
        		alert.showAndWait();
        		
        	} else {

            	AccountUser accountUser = new AccountUser(mFirstName.getText(), mLastName.getText(), mPassword.getText(), mEmail.getText(), mPhoneNumber.getText(), mUserName.getText(), mCreditCard.getText());
            	Request request = new Request(API.UPDATE_USER, accountUser);
            	String jsonString = mGson.toJson(request);
            	mChat.SendToServer(jsonString);

                ProgressForm progressForm = new ProgressForm();
                Task<Void> waitTask = new Dialogs.WaitTask();
                progressForm.activateProgressBar(waitTask);

                waitTask.setOnSucceeded(event -> {

                	progressForm.getDialogStage().close();
                	update.setDisable(false);
                    goBack.setDisable(false);

                	if (Main.mServerResponseErrorCode == ErrorCodes.SUCCESS) {

    	        		MessageDialog alert = new MessageDialog(AlertType.INFORMATION, "Congradulations", "Your details have been updated", "");
    	        		alert.showAndWait();
    	        		
    	        		refreshScene();

            		} else {

    	        		MessageDialog alert = new MessageDialog(AlertType.ERROR, "Error", "An unknown error has occurred", "Please try again");
                		alert.showAndWait();

                	}

            		Main.mServerResponseErrorCode = ErrorCodes.RESET;

                });

                update.setDisable(true);
                goBack.setDisable(true);

                progressForm.getDialogStage().show();

                Thread thread = new Thread(waitTask);
                thread.start();
        		
        	}
        	
        });

		// Position in UI

		mUserName.setMaxWidth(Dimensions.mUpdateDetailsViewTextWidth);
		mPassword.setMaxWidth(Dimensions.mUpdateDetailsViewTextWidth);
		mEmail.setMaxWidth(Dimensions.mUpdateDetailsViewTextWidth);
		mPhoneNumber.setMaxWidth(Dimensions.mUpdateDetailsViewTextWidth);
		mFirstName.setMaxWidth(Dimensions.mUpdateDetailsViewTextWidth);
		mLastName.setMaxWidth(Dimensions.mUpdateDetailsViewTextWidth);
		update.setMaxWidth(Dimensions.mUpdateDetailsViewUpdateButtonWidth);

		cityColumn.setMinWidth(Dimensions.mUpdateDetailsViewColumnWidth);
        dateColumn.setMinWidth(Dimensions.mUpdateDetailsViewColumnWidth);
        typeColumn.setMinWidth(Dimensions.mUpdateDetailsViewColumnWidth);

        table.setMaxWidth(Dimensions.mUpdateDetailsViewTableWidth);
		table.setMaxHeight(Dimensions.mUpdateDetailsViewTableHeight);

		mUserName.setTranslateY(-250);
		mPassword.setTranslateY(-200);
		mEmail.setTranslateY(-200);
		mPhoneNumber.setTranslateY(-150);
		mFirstName.setTranslateY(-100);
		mLastName.setTranslateY(-50);
		update.setTranslateY(0);
		table.setTranslateY(100);

		// Scene
		
	    StackPane stackPane = new StackPane();
	    stackPane.setBackground(new Background(myBI));
	    stackPane.getChildren().addAll(mUserName, mPassword, mEmail, mPhoneNumber, mFirstName, mLastName, update, table, goBack);

        mScene = new Scene(stackPane, Dimensions.mWith, Dimensions.mheight);

	}

	public static void refreshScene() {

	    mUserName.setText(Main.mAccountUser.mUserName);
	    mPassword.setText(Main.mAccountUser.mPassword);
	    mEmail.setText(Main.mAccountUser.mEmail);
	    mPhoneNumber.setText(Main.mAccountUser.mPhoneNumber);
	    mFirstName.setText(Main.mAccountUser.mFirstName);
	    mLastName.setText(Main.mAccountUser.mLastName);
	    mCreditCard.setText(Main.mAccountUser.mCreditCard);

	    data = FXCollections.observableArrayList();

	    for (Purchase purchase : Main.mAccountUser.mPurchases) {
	        data.add(new PurchaseView(purchase.mCityName, purchase.mDate, purchase.mType));
	    }

        table.setItems(data);

	}

}
