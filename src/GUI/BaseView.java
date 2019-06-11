package GUI;

import Communication.ClientConsole;
import Utils.UI_server_communicate;
import javafx.stage.Stage;

public class BaseView {
	
	Stage stage;

	ClientConsole mChat;
	UI_server_communicate mCommunicate;

	public BaseView(Stage stage, ClientConsole aChat, UI_server_communicate aCommunicate) {
		this.stage = stage;
		this.mChat = aChat;
		this.mCommunicate = aCommunicate;
	}
	
}
