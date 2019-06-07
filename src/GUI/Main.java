package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import com.google.gson.Gson;

import GUI.UI_server_communicate;

import Requests.Request;
import Requests.Register;
import Requests.AccountCheckRequest;

import MVC.View;

import Communication.ClientConsole;

import Utils.PasswordUtils;

public class Main extends Application {
	String request_string;
	
	/****Scenes declare****/

	Stage window;
	Scene welcomeR;
	Scene signIn;
	Scene signInR;
	Scene signUpS;
	Scene menu;
	Scene guestScene;
	Scene verifyScene;
	Scene welcome;
	Scene clientZone;
	
    /****Textfields declare****/

	
    TextField searchTF=new TextField("Type map to search");
    TextField name = new TextField("Please enter username");
    TextField password = new TextField("Please enter password");
    TextField nameW = new TextField("Please enter worker name");
    TextField passwordW = new TextField("Please enter worker password");
    TextField command = new TextField("Please enter command");
    TextField nameR =new TextField("Please enter username");
    TextField passwordR = new TextField("Please enter password");
    TextField creditCard = new TextField("Please enter credit card");
    TextField email= new TextField("Please enter your email addres");
    TextField verifySerial=new TextField("Please enter the serial we sent to you.");
    
	/**** ****/
    
    /***Clean all  textfields function***/
    
    public  void clean_tf() {
    	     searchTF.setText("Type map to search");
    	     name.setText("Please enter username");
    	     password.setText("Please enter password");
    	     command.setText("Please enter command");
    	     nameR.setText("Please enter username");
    	     passwordR.setText("Please enter password");
    	     creditCard.setText("Please enter credit card");
    	     email.setText("Please enter your email addres");
    	     verifySerial.setText("Please enter the serial we sent to you.");
    }
	
	public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) {

    	Gson gson = new Gson();

    	window = primaryStage;
    	UI_server_communicate communicate= new UI_server_communicate();
        View view = new View();
    	ClientConsole chat = new ClientConsole("Host", "127.0.0.1", ClientConsole.DEFAULT_PORT, view);
        primaryStage.setTitle("GCM");


        /****Textfields edit****/
        searchTF.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        name.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        password.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        nameW.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        passwordW.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        nameR.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        passwordR.setFont(Font.font("Verdana", FontWeight.BOLD, 12));;
        creditCard.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        email.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        verifySerial.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        /****Checkboxs declare ***/
        CheckBox Search_by_city= new CheckBox("Search by city");
        CheckBox Search_by_inplace= new CheckBox("Search by interested place");
        CheckBox Search_by_general_description= new CheckBox("Search by general description");
        Search_by_city.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        Search_by_inplace.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        Search_by_general_description.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        
        /****Buttons declare****/
        Button next = new Button();
        Button nextW = new Button();
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
        Button getBack5=new Button();
        Button getBack6=new Button();
        Button search_btn=new Button();
        Button workers_zone=new Button();
        
        /**** Buttons Text ****/
        next.setText("Next");
        nextW.setText("Next");
        workers_zone.setText("Workers zone");
        search_btn.setText("Search");
        signUp.setText("Sign up as our customer");
        signUp2.setText("Sign up");
        getBack5.setText("Go to Main");
        getBack4.setText("Go back");
        getBack3.setText("Go back");
        getBack2.setText("Go back");
        getBack6.setText("Go back");
        getBack.setText("Go back");
        search.setText("Search");
        memBtn.setText("I am a member");
        guestBtn.setText("I am not a member");
        btn.setText("Next");
        
        
        
        
        
        
        /****Buttons actions****/

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
        	String jsonString = gson.toJson(request);

        	chat.SendToServer(jsonString);

        	communicate.ask_server();

        });


        signUp2.setOnAction(e->{

        	Register register = new Register(nameR.getText(), passwordR.getText(), email.getText(), creditCard.getText());
        	Request request = new Request("Register", register);
        	String jsonString = gson.toJson(request);
        	chat.SendToServer(jsonString);

        	communicate.ask_server();

        	window.setScene(welcomeR);
        	
        });
        
        
        signUp.setOnAction(e->window.setScene(signUpS));
        memBtn.setOnAction(e->window.setScene(signIn));
        guestBtn.setOnAction(e->window.setScene(guestScene));
        getBack.setOnAction(e->{window.setScene(menu);clean_tf();});
        getBack2.setOnAction(e->{window.setScene(menu);clean_tf();});
        getBack3.setOnAction(e->{window.setScene(menu);clean_tf();});
        getBack4.setOnAction(e->{window.setScene(menu);clean_tf();});
        getBack5.setOnAction(e->{window.setScene(menu);clean_tf();});
        getBack6.setOnAction(e->{window.setScene(menu);clean_tf();});
        workers_zone.setOnAction(e->{window.setScene(signInR);clean_tf();});

       
        
        btn.setOnAction(e->{
            	AccountCheckRequest accountCheck = new AccountCheckRequest(name.getText(), password.getText());
            	Request request = new Request("UserCheck", accountCheck);
            	String jsonString = gson.toJson(request);
            	chat.SendToServer(jsonString);

            	communicate.ask_server();

//            	if(communicate.ask_server())window.setScene(clientZone);

            });
            


        nextW.setOnAction(e->{
           
        	AccountCheckRequest accountCheck = new AccountCheckRequest(nameW.getText(), passwordW.getText());
            	Request request = new Request("WorkerCheck", accountCheck);
            	String jsonString = gson.toJson(request);
            	chat.SendToServer(jsonString);

            	communicate.ask_server();

        });

        
        /****background zone****/
        
        BackgroundImage myBI= new BackgroundImage(new Image("Images\\Background.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        BackgroundImage myBIwe= new BackgroundImage(new Image("Images\\Welcome.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        BackgroundImage myBIs= new BackgroundImage(new Image("Images\\sign_up.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        BackgroundImage myBIW= new BackgroundImage(new Image("Images\\signInIm.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        BackgroundImage myBIWr= new BackgroundImage(new Image("Images\\Wrong.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        BackgroundImage myBIc= new BackgroundImage(new Image("Images\\catalog_up.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        BackgroundImage myBIz= new BackgroundImage(new Image("Images\\clientZone.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        
        
        /****Scene declare****/
        
        StackPane root = new StackPane();
        root.setBackground(new Background(myBI));
        root.getChildren().add(workers_zone);
        workers_zone.setTranslateY(-100);
        root.getChildren().add(memBtn);
        memBtn.setTranslateY(-250);
        root.getChildren().add(guestBtn);
        guestBtn.setTranslateY(-200);
        root.getChildren().add(signUp);
        signUp.setTranslateY(-150);
        menu=new Scene(root,1280,720);
        primaryStage.setScene(menu);
        primaryStage.show();
        
        StackPane client=new StackPane();
        client.setBackground(new Background(myBIz));
        clientZone=new Scene(client,1280,720);

        
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
        guestZone.setBackground(new Background(myBIc));
        searchTF.setMaxWidth(300);
        guestZone.getChildren().add(searchTF);
        guestZone.getChildren().add(search_btn);
        guestZone.getChildren().add(getBack2);
        guestZone.getChildren().add(Search_by_city);
        guestZone.getChildren().add(Search_by_inplace);
        guestZone.getChildren().add(Search_by_general_description);
        Search_by_city.setTranslateY(-250);
        Search_by_city.setTranslateX(-63);
        Search_by_inplace.setTranslateY(-200);
        Search_by_general_description.setTranslateY(-150);
        searchTF.setTranslateY(-100);
        getBack2.setTranslateX(-100);
        guestScene=new Scene(guestZone,1280,720);

        
        StackPane welcomeRs=new StackPane();
        welcomeRs.setBackground(new Background(myBIwe));
        welcomeRs.getChildren().add(getBack5);
        welcomeR=new Scene(welcomeRs,1280,720);


        
        
        StackPane sign_Up = new StackPane();
        sign_Up.setBackground(new Background(myBIs));
        nameR.setTranslateY(-100);
        nameR.setMaxWidth(300);
        sign_Up.getChildren().add(nameR);
        passwordR.setMaxWidth(300);
        passwordR.setTranslateY(-50);
        sign_Up.getChildren().add(passwordR);
        creditCard.setMaxWidth(300);
        creditCard.setTranslateY(0);
        sign_Up.getChildren().add(creditCard);
        email.setMaxWidth(300);
        email.setTranslateY(50);
        sign_Up.getChildren().add(email);
        sign_Up.getChildren().add(signUp2);
        signUp2.setTranslateY(100);
        sign_Up.getChildren().add(getBack3);
        getBack3.setTranslateY(100);
        getBack3.setTranslateX(-100);
        signUpS=new Scene(sign_Up,1280,720);
        
        
        StackPane workersZone = new StackPane();
        workersZone.setBackground(new Background(myBIW));
        nameW.setTranslateY(-50);
        nameW.setMaxWidth(400);
        workersZone.getChildren().add(nameW);
        passwordW.setMaxWidth(400);
        workersZone.getChildren().add(passwordW);
        workersZone.getChildren().add(getBack6);
        getBack6.setTranslateY(100);
        getBack6.setTranslateX(-100);
        workersZone.getChildren().add(nextW);
        nextW.setTranslateY(100);
        signInR=new Scene(workersZone,1280,720);
        


        // Members zone sign in

        StackPane memberZone = new StackPane();
        memberZone.setBackground(new Background(myBIW)); 

        name.setMaxWidth(400);
        name.setTranslateY(-50);
        memberZone.getChildren().add(name);

        password.setMaxWidth(400);
        memberZone.getChildren().add(password);

        memberZone.getChildren().add(getBack);
        getBack.setTranslateY(100);
        getBack.setTranslateX(-100);

        memberZone.getChildren().add(btn);
        btn.setTranslateY(100);

        signIn = new Scene(memberZone,1280,720);

    }
    }
