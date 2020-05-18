import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.sound.sampled.*;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class Reaktionstest extends Application implements EventHandler<KeyEvent> {
	Stage win;
	Scene s, end;
	Timeline timeline;
	BorderPane b;
	Label l1, l2, l3;
	Circle c;
	Timer timer1, timer2, timer3;
	TimerTask task;
	int i = 0;
	int points, V1, V2, V3;
	File Beep = new File("Beep.wav");
	boolean key = false;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage reactio) {
		win = reactio;
		newScene();
		
		
		
		
		
		task = new TimerTask() {
			@Override
			public void run() {
				checkForKey();
				if(key == true) {
					timer1.cancel();
					//task.cancel();
					System.out.println("Hier");
					i++;
					time();
				} else {
					points = points - 1;
				}	
			}
		};
	}
	
	public void checkForKey() {
		key  = false;
		new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if ((event.getCode() == KeyCode.ENTER) && (i <= 3)) {
					if (i == 1) {
						System.out.println("Hier");
						V1 = points;
					} else if (i == 2) {
						V2 = points;
					} else if (i == 3) {
						V3 = points;
					}
					key = true;
				} else {
					key = false;
				}
			}
		};
	}

	public void time() {
		timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				timeline.stop();
				newScene();
			}
		}));
		timeline.play();
	};

	public void newScene() {
		if (i == 0) {
			win.setTitle("Reaktionstest");
			b = new BorderPane();
			c = new Circle(100);
			c.setStroke(Color.BLACK);
			c.setFill(Color.WHITE);
			b.setCenter(c);
			s = new Scene(b, 400, 400);
			win.setScene(s);
			win.show();
			i++;
			time();
		} else if (i == 1) {
			c.setFill(Color.GREEN);
			timer1 = new Timer();
			System.out.println("Hier");
			points = 1000;
			timer1.scheduleAtFixedRate(task, 1, 1);
			win.setScene(s);
		} else if (i == 2) {
			playMusic(Beep);
			timer2 = new Timer();
			points = 1000;
			timer2.scheduleAtFixedRate(task, 1, 1);
		} else if (i == 3) {
			playMusic(Beep);
			c.setFill(Color.BLUE);
			timer3 = new Timer();
			points = 1000;
			timer3.scheduleAtFixedRate(task, 1, 1);
		} else if (i == 4) {
			timeline.stop();
			VBox v = new VBox(8);
			v.getChildren().addAll(l1, l2, l3);
			end = new Scene(v, 400, 400);
		} else if (i == 5) {
			win.close();
		}
	}

	public static void playMusic(File sound) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();

			Thread.sleep(clip.getMicrosecondLength() / 1000);
		} catch (Exception e) {
			System.out.println("Fehler");
		}
	}

	@Override
	public void handle(KeyEvent event) {

		if ((event.getCode() == KeyCode.ENTER) && (i <= 3)) {
			if (i == 1) {
				timer1.cancel();
				System.out.println("Hier");
				V1 = points;

			} else if (i == 2) {
				V2 = points;
			} else if (i == 3) {
				V3 = points;
			}
		}
		i++;
		time();
	}

}
