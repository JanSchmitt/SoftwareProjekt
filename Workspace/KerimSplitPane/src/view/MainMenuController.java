package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainMenuController {

	
	//@FXML 
	//private Button START_BUTTON;
	
	//Change scene when method is called
	public void StartButtonPushed(ActionEvent event) throws Exception {
		System.out.println("Pressed");
		//Parent gameViewParent = FXMLLoader.load(getClass().getResource("/view/GameView.fxml"));
		//Scene gameScene = new Scene(gameViewParent); 
		
		//get Stage information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow(); //tableViewParent dont know what content is in getSource, casting to Node makes us able to get scene
		//window.setScene(gameScene);
		GameViewManager gameManager = new GameViewManager();
		gameManager.createNewGame(window);
		
		
		//window.setScene(gameScene);
		//window.setResizable(false);
		//window.show();
		
	}


	
}
