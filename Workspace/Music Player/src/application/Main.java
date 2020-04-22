package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;


public class Main extends Application {
	MusicLoader player;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			player = new MusicLoader();			
			
			//define GridPane layout
			GridPane grid = new GridPane();
			grid.setPadding(new Insets(50,50,50,50));
			grid.setVgap(15);
			grid.setHgap(10);
			
			//add buttons
			Button button1=new Button("This is getting out of hand");
			GridPane.setConstraints(button1, 0, 0);
			button1.setOnAction(e -> {
				if(player.playing==true) {
					player.stop();
				}
				player.load("music/thief in the night.wav");
				player.play();
			});
			
			Button button2=new Button("Focused");
			GridPane.setConstraints(button2, 1, 0);
			button2.setOnAction(e -> {
				if(player.playing==true) {
					player.stop();
				}
				player.load("music/funk warte.wav");
				player.play();
			});
			
			Button button3=new Button("I'm falling asleep");
			GridPane.setConstraints(button3, 2, 0);
			button3.setOnAction(e -> {
				if(player.playing==true) {
					player.stop();
				}
				player.load("music/rage.wav");
				player.play();
			});
			
			Button stopButton=new Button("STOP");
			GridPane.setConstraints(stopButton,0,1);
			stopButton.setOnAction(e -> {
				if(player.playing==true) {
					player.stop();
				}
			});
			stopButton.getStyleClass().add("button-red");
			grid.getChildren().addAll(button1, button2, button3, stopButton);
			
			//show layout as scene
			Scene scene = new Scene(grid,600,200);
			scene.getStylesheets().add("coolStyle.css");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//start application
		launch(args);
	}
}
