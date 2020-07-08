package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class ViewManager {
	
	
	private Stage mainStage;
    public ViewManager() throws Exception{
    	Parent root = FXMLLoader.load(getClass().getResource("/view/View.fxml")); //gets the scenecontents namely its panes and all nodes created in SceneBuilder
    	Scene scene = new Scene(root); //sets the contents of root to the secene
    	scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm()); 
    	
    	//GameViewManager gm = new GameViewManager();
    	mainStage = new Stage();
    	mainStage.setScene(scene);
    	mainStage.setTitle("Biofeedback Anwendung");
    	
    }
    
	public Stage getMainStage() {
		return mainStage;
	}
	
}
