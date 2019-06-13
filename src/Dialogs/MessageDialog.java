package Dialogs;

import javafx.scene.control.Alert;

public class MessageDialog extends Alert{

	public MessageDialog(AlertType arg0, String aTitle, String aHeader , String aContent) {
		super(arg0);
		this.setTitle(aTitle);
		this.setHeaderText(aHeader);
		this.setContentText(aContent);
	}
	
}
