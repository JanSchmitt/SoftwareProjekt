package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application{
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			PongGame game=new PongGame(primaryStage);
			game.run();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//converts KeyEvent into player movement
	
	public static void main(String[] args) {
		launch(args);
	}
}