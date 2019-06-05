
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import com.google.gson.Gson;

import Requests.Message;
import Requests.Register;
import Requests.Request;

public class Main extends Application {
	Stage window;
	Scene signIn;
	Scene signUpS;
	Scene menu;
	Scene guestScene;
	Scene verifyScene;

	public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) {
    	window=primaryStage;
    	
        TextField helloTF = new TextField("");
    	ClientConsole chat = new ClientConsole("Host", "127.0.0.1", ClientConsole.DEFAULT_PORT, helloTF);
        primaryStage.setTitle("GCM");
        
       
        /****Textfields declare****/
        TextField searchTF=new TextField("Type map to search");
        TextField name = new TextField("Please enter username");
        TextField password = new TextField("Please enter password");
        TextField command = new TextField("Please enter command");
        TextField nameR =new TextField("Please enter username");
        TextField passwordR = new TextField("Please enter password");
        TextField creditCard = new TextField("Please enter credit card");
        TextField email= new TextField("Please enter your email addres");
        TextField verifySerial=new TextField("Please enter the serial we sent to you.");
        
        /****Checkboxs declare ***/
        CheckBox OneTimePruchase= new CheckBox("One time pruchase");
        CheckBox HalfYearPruchase= new CheckBox("But for 6 months");
        
        
        /****Buttons declare****/
        Button next = new Button();
        Button btn = new Button();
        Button memBtn = new Button();
        Button guestBtn = new Button();
        Button signUp = new Button();
        Button signUp2 = new Button();
        Button search = new Button();
        Button getBack = new Button();
        Button getBack2=new Button();
        Button getBack3=new Button();
        Button getBack4=new Button();
        
        
        /**** Buttons Text ****/
        next.setText("Next");
        signUp.setText("Sign up as our customer");
        signUp2.setText("Sign up");
        getBack4.setText("Go back");
        getBack3.setText("Go back");
        getBack2.setText("Go back");
        getBack.setText("Go back");
        search.setText("Search");
        memBtn.setText("I am a member");
        guestBtn.setText("I am not a member");
        btn.setText("Run Command");
        
        
        
        
        
        
        /****Buttons actions****/
        signUp2.setOnAction(e->{
        	window.setScene(verifyScene);
        	Gson gson = new Gson();
        	Register register = new Register(nameR.getText(), passwordR.getText(), email.getText(), creditCard.getText());
        	Request request = new Request(2, register);
        	String jsonString = gson.toJson(request);
        	chat.SendToServer(jsonString);
        	
        });
        signUp.setOnAction(e->window.setScene(signUpS));
        memBtn.setOnAction(e->window.setScene(signIn));
        guestBtn.setOnAction(e->window.setScene(guestScene));
        getBack.setOnAction(e->window.setScene(menu));
        getBack2.setOnAction(e->window.setScene(menu));
        getBack3.setOnAction(e->window.setScene(menu));
        getBack4.setOnAction(e->window.setScene(menu));

       
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Gson gson = new Gson();
            	Message message = new Message(name.getText(), password.getText(), command.getText());
            	Request request = new Request(1, message);
            	String jsonString = gson.toJson(request);
            	chat.SendToServer(jsonString);
            }
        });

        
        
        BackgroundImage myBI= new BackgroundImage(new Image("Images\\Background.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        
        /****Scene declare****/
        
        StackPane root = new StackPane();

        root.getChildren().add(memBtn);
        root.setBackground(new Background(myBI));
        memBtn.setTranslateY(-250);
        root.getChildren().add(guestBtn);
        guestBtn.setTranslateY(-200);
        root.getChildren().add(signUp);
        signUp.setTranslateY(-150);
        menu=new Scene(root,1280,720);
        primaryStage.setScene(menu);
        primaryStage.show();
               
        
        StackPane verifyS=new StackPane();
        verifyS.getChildren().add(verifySerial);
        verifySerial.setTranslateY(-50);
        verifyS.getChildren().add(next);
        next.setTranslateY(0);
        verifyS.getChildren().add(getBack4);
        getBack4.setTranslateY(0);
        getBack4.setTranslateX(100);
        verifyScene=new Scene(verifyS,300,300);

        
        
        
        
        StackPane guestZone = new StackPane();
        guestZone.getChildren().add(searchTF);
        guestZone.getChildren().add(getBack2);
        searchTF.setTranslateY(-100);
        getBack2.setTranslateY(50);
        guestScene=new Scene(guestZone,300,300);

        StackPane sign_Up = new StackPane();
        sign_Up.getChildren().add(nameR);
        nameR.setTranslateY(-100);
        sign_Up.getChildren().add(passwordR);
        passwordR.setTranslateY(-50);
        sign_Up.getChildren().add(creditCard);
        creditCard.setTranslateY(0);
        sign_Up.getChildren().add(email);
        email.setTranslateY(50);
        sign_Up.getChildren().add(signUp2);
        signUp2.setTranslateY(100);
        sign_Up.getChildren().add(getBack3);
        getBack3.setTranslateY(100);
        getBack3.setTranslateX(-100);
        signUpS=new Scene(sign_Up,300,300);
        
        
        
        
        StackPane memberZone = new StackPane();
        memberZone.getChildren().add(helloTF);
        helloTF.setTranslateY(-100);
        memberZone.getChildren().add(name);
        name.setTranslateY(-50);
        memberZone.getChildren().add(password);
        password.setTranslateY(0);
        memberZone.getChildren().add(command);
        command.setTranslateY(50);
        memberZone.getChildren().add(getBack);
        getBack.setTranslateY(100);
        getBack.setTranslateX(-100);
        memberZone.getChildren().add(btn);
        btn.setTranslateY(100);
        signIn=new Scene(memberZone,300,300);

        
        
    }

}
