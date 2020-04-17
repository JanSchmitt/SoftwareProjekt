package timer;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Timer extends Application implements EventHandler<ActionEvent> {

	public static void main(String[] args) {
		launch(args);
	}
	
	Button buttonStart;
	Stage window;
	Scene scene;
	int score = 0;
	Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		public void run() {
			score = score + 100;
			SceneSet();
		}
	};

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() == buttonStart) {
			timer.scheduleAtFixedRate(task, 1000, 1000);
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		SceneSet();
	} 
	public void SceneSet() {
		VBox box1 = new VBox(20);
		buttonStart = new Button("Start");
		Label label1 = new Label("Your current score: " + score);
		box1.getChildren().addAll(buttonStart, label1);
		scene = new Scene(box1, 400, 400);
		window.setScene(scene);
		buttonStart.setOnAction(this);
		window.show();
	}
	
}
