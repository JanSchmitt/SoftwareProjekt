package score;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Score extends Application implements EventHandler<ActionEvent> {

	Button buttonAdd;
	Button buttonStart;
	Button buttonEnd;
	Stage window;
	int score = 0;
	int highscore = 0;
	Scene scene;
	AnimationTimer timer;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void startTime() {
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				score = score + 100;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					
				}
				SceneSet();
			}	
		};
		timer.start();
	}
	
	public void handle(ActionEvent event) {
		if(event.getSource() == buttonStart) {
			startTime();
		}
		
		if(event.getSource() == buttonEnd) {
			timer.stop();
			
			if(score > highscore) {
				highscore = score;
				score = 0;
			} else {
				score = 0;
			}
			SceneSet();
		}
		
		if(event.getSource() == buttonAdd)
		{
			add10();
			SceneSet();
		}
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		SceneSet();
	}

	public void add10() {
		score = score + 10;
	}
	
	public void SceneSet() {
		VBox box1 = new VBox(20);
		buttonAdd = new Button("Add Points");
		buttonStart = new Button("Start");
		buttonEnd = new Button("End");
		Label label1 = new Label("Your current score: " + score);
		Label label2 = new Label("Highscore: " + highscore);
		box1.getChildren().addAll(buttonAdd, buttonStart, buttonEnd, label1, label2);
		scene = new Scene(box1, 400, 400);
		window.setScene(scene);
		buttonAdd.setOnAction(this);
		buttonStart.setOnAction(this);
		buttonEnd.setOnAction(this);
		window.show();
	}
}

