package timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Score {

	public int time = 0;
	public int score = 0;
	int highscore = 0;
	public int collisionCounter = 1;
	
	
	public void startTimer() {
		 Timeline timl_1s = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

			  @Override
			  public void handle(ActionEvent event) {
				  time++;
				  
				  if(time % 5 == 0) { //every 5 seconds the score is to increase by 50
					  score = score + 50;
				  }
				  if(time % 15 == 0) {
					  if(collisionCounter > 1) {
						  collisionCounter--;
					  }
				  }
				  System.out.println(collisionCounter);
				  //System.out.println("this is called every 1 seconds on UI thread " + score);
				  
			  }
			}));
		 timl_1s.setCycleCount(Timeline.INDEFINITE);
		 timl_1s.play();
              
	}
	
	public int getTime() {
		return time;
	}
	
}

