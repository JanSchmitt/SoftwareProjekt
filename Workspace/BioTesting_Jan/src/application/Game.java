package application;

	
import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewManager;


public class Game extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			ViewManager manager = new ViewManager();
			primaryStage = manager.getMainStage();
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void main(String[] args) {
		launch(args);
	}
}
