
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;

public class Main extends Application {

	public static void main(String[] args) {

		launch(args);
	}

    @Override
    public void start(Stage primaryStage) {

    	Connect connect;

    	ClientConsole chat;

        TextField helloTF = new TextField("Press 'Check' to check name and password of user");

		connect = new Connect();
	    chat = new ClientConsole("Evgeny", "127.0.0.1", ClientConsole.DEFAULT_PORT, helloTF);

        primaryStage.setTitle("Hello World!");

        TextField name = new TextField("Enter name here");
        TextField password = new TextField("Enter password here");

        Button btn = new Button();
        btn.setText("Check");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	chat.SendToServer(name.getText() + " " + password.getText());
            }
        });

        StackPane root = new StackPane();

        root.getChildren().add(helloTF);
        helloTF.setTranslateY(-50);

        root.getChildren().add(name);
        name.setTranslateY(0);

        root.getChildren().add(password);
        password.setTranslateY(50);

        root.getChildren().add(btn);
        btn.setTranslateY(100);

        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
    }

}
