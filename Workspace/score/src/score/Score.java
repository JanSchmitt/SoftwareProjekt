package score;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
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
	Button buttonRemove;
	Stage window;
	int score = 0;
	int highscore = 0;
	Scene scene;
	AnimationTimer timer;
	boolean running = false;
	int counter = 1;
	int gain = 100;
	
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
				gain = 100;
			}	
		};
		timer.start();
	}
	
	public void handle(ActionEvent event) {
		if(event.getSource() == buttonStart) {
			startTime();
			running = true;
		}
		
		if(event.getSource() == buttonEnd && running == true) {
			timer.stop();
			
			if(score > highscore) {
				highscore = score;
				score = 0;
			} else {
				score = 0;
			}
			SceneSet();
		} 
		
		if(event.getSource() == buttonEnd && running == false) {
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
		
		if(event.getSource() == buttonRemove) {
			remove();
			SceneSet();
		}
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		SceneSet();
	}

	public void add10() {
		score = score + 234;
		gain = gain + 234;
	}
	
	public void remove() {	
			score = score - (counter*250);
			counter = counter + 1;
			gain = gain - (counter*250);
	}
	
	public void SceneSet() {
		VBox box1 = new VBox(20);
		buttonAdd = new Button("Add Points");
		buttonRemove = new Button("Remove Points");
		buttonStart = new Button("Start");
		buttonEnd = new Button("End");
		Label label1 = new Label("Your current score: " + score);
		Label label2 = new Label("Highscore: " + highscore);
		Label  label3 = new Label("Points this round: " + gain);
		box1.getChildren().addAll(buttonAdd, buttonRemove, buttonStart, buttonEnd, label1, label2, label3);
		scene = new Scene(box1, 400, 400);
		window.setScene(scene);
		buttonAdd.setOnAction(this);
		buttonRemove.setOnAction(this);
		buttonStart.setOnAction(this);
		buttonEnd.setOnAction(this);
		
		window.show();
	}
}

