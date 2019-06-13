package GUI;

import Communication.ClientConsole;
import Defines.MemLvl;
import Defines.SceneName;
import Defines.Dimensions;
import DB_classes.CityMap;
import DB_classes.Place;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class BuyView extends BaseView{
	
	public static String myCity;
	
	StackPane root = new StackPane();

    static Button oneTime = new Button();
    static Button periodTime = new Button();

	public BuyView(ClientConsole aChat) {

		super(aChat);

		System.out.println(myCity);

		goBack.setTranslateY(-150);
		periodTime.setTranslateY(-200);
		oneTime.setTranslateY(-250);
	
		root.setBackground(new Background(myBIc1));
		root.getChildren().addAll(oneTime, periodTime, goBack);

		mScene = new Scene(root, Dimensions.mWith, Dimensions.mheight);

	}

	public static void refreshScene() {
		oneTime.setText("One Time Pruchase- Price"+ GUI.Main.Price1+"$, Maps of "+myCity);
		periodTime.setText("Pruchase for 6 months - Price"+GUI.Main.Price2+ "$,Maps of "+myCity);

	}

}
