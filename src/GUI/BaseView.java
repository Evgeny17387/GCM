package GUI;

import Communication.ClientConsole;
import Utils.UI_server_communicate;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class BaseView {

	ClientConsole mChat;
	UI_server_communicate mCommunicate;
	
	public Scene mScene;

	public BaseView(ClientConsole aChat, UI_server_communicate aCommunicate) {
		this.mChat = aChat;
		this.mCommunicate = aCommunicate;
	}

}
