package GUI;


import Communication.ClientConsole;
import Defines.Dimensions;
import Defines.SceneName;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Scene;
import Defines.MemLvl;
import Defines.EditLevel;

public class EditView extends BaseView  {
	public EditView(ClientConsole aChat) {

		super(aChat);

        
    		BuyView.myCity = Main.myMapList.get(0);
    		Main.changeScene(SceneName.EDIT);
       
	
	
	StackPane root = new StackPane();
    root.setBackground(new Background(myBIe));
	TextField mName=new TextField("Type new map name");
	TextField mDescription=new TextField ("Type new description");
	TextField mPrice=new TextField("Type new price");
	TextField newMapName=new TextField("Insert new map name");
	TextField newMapDescription=new TextField("Insert new map description");
	TextField newMapPic=new TextField("Insert new map picture url");
	TextField placeDescription=new TextField("Insert new place description");

	
	Button Next=new Button ("Next");
	Button goBack=new Button("Go back");
	   goBack.setOnAction(e->{Main.changeScene(SceneName.SHOW_MAP);GUI.ShowMapView.counter=0;GUI.ShowMapView.counterPlace=0;});
       goBack.setMaxHeight(Dimensions.mBaseViewButtonsHeight);
       goBack.setMaxWidth(Dimensions.mBaseViewButtonsWidth);
       goBack.setTranslateY(Dimensions.mheight / 2 - Dimensions.mBaseViewButtonsHeight);
       goBack.setTranslateX( - Dimensions.mWith / 2 + Dimensions.mBaseViewButtonsWidth);
       
     root.getChildren().addAll(mName,mDescription,mPrice,newMapName,newMapDescription,newMapPic,placeDescription,Next,goBack);
     mScene = new Scene(root, Dimensions.mWith, Dimensions.mheight);

     switch(GUI.Main.editlevel) {
    	 case ZERO:
    		 
    		 
    		 
    	
    	 case PLACE:
    	
    		 
    		 
    	 case ADD:


    	 
    	 
    	 case MAP:
    	 
    	default:
		break;
    		 
     
     }
     
	}

	
		
	
	public static void refreshScene() {}
	
}