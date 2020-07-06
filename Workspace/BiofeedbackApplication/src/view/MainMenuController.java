package view;

//import java.io.IOException;

import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
/*import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;*/
import javafx.stage.Stage;

public class MainMenuController {

	
	//@FXML 
	//private Button START_BUTTON;
	
	//Change scene when method is called
	public void StartButtonPushed(ActionEvent event) throws Exception {
		System.out.println("Pressed");
		
		//get Stage information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow(); //tableViewParent dont know what content is in getSource, casting to Node makes us able to get scene
		GameViewManager gameManager = new GameViewManager();
		gameManager.createNewGame(window);
		
	}


	
}
