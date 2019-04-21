
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) {

        TextField helloTF = new TextField("");

    	ClientConsole chat = new ClientConsole("Evgeny", "127.0.0.1", ClientConsole.DEFAULT_PORT, helloTF);

        primaryStage.setTitle("GCM");

        TextField name = new TextField("Evgeny");
        TextField password = new TextField("Evgeny");
        TextField command = new TextField("2");

        Button btn = new Button();
        btn.setText("Run Command");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	chat.SendToServer(command.getText() + " " + name.getText() + " " + password.getText());
            }
        });

        StackPane root = new StackPane();

        root.getChildren().add(helloTF);
        helloTF.setTranslateY(-100);

        root.getChildren().add(name);
        name.setTranslateY(-50);

        root.getChildren().add(password);
        password.setTranslateY(0);

        root.getChildren().add(command);
        command.setTranslateY(50);

        root.getChildren().add(btn);
        btn.setTranslateY(100);

        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
    }

}
