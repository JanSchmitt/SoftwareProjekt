package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class ViewManager {
	
	
	private Stage mainStage;
    public ViewManager() throws Exception{
    	Parent root = FXMLLoader.load(getClass().getResource("/view/View.fxml"));
    	Scene scene = new Scene(root);
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
