import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class Reaktionstest extends Application {
	Stage win;
	Scene s, end;
	Timeline timeline;
	BorderPane b;
	int counter;
	Button button;
	Label l1, l2, l3;
	Circle c;
	Timer timer1, timer2, timer3, timer;
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
		win.setTitle("Reaktionstest");
		b = new BorderPane();
		c = new Circle(100);
		c.setStroke(Color.BLACK);
		c.setFill(Color.WHITE);
		b.setCenter(c);
		s = new Scene(b, 400, 400);
		win.setScene(s);
		win.show();
		i = 1;
		time();
	}
	
	public void newScene() {
		b = new BorderPane();
		b.setCenter(c);
		s = new Scene(b, 400, 400);
		if (i == 1) {
			c.setFill(Color.GREEN);
			System.out.println("Scene1");
			points = 1000;
			createTimer();
			win.setScene(s);
		} else if (i == 2) {
			playMusic(Beep);
			System.out.println("Scene2");
			points = 1000;
			createTimer();
		} else if (i == 3) {
			playMusic(Beep);
			c.setFill(Color.BLUE);
			System.out.println("Scene2");
			points = 1000;
			createTimer();
			win.setScene(s);
		} else if (i == 4) {
			VBox v = new VBox(8);
			v.getChildren().addAll(l1, l2, l3);
			end = new Scene(v, 400, 400);
			timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					timeline.stop();
					win.close();
				}
			}));
			timeline.play();	
		}
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
	}

	public void createTimer() {

		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				/*button.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent ke) {
						if(ke.getCode() == KeyCode.ENTER) {
							key = true;
						} else {
							key = false;
						}
					}	
				});*/
				checkForKey();
				if (key == true) {
					timer.cancel();
					System.out.println("Timer" + i + " gecancelt");
					i++;
					time();
				} else {
					points = points - 1;
				}
				if(points <= -1000) {
					timer.cancel();
					System.out.println("Timer" + i + " gecancelt");
					i++;
					time();
				}
			}
		};
		timer.scheduleAtFixedRate(task, 1, 1);
	}

	public void checkForKey() {
		key = false;
		new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {

				if (event.getCode() == KeyCode.ENTER) {
					if (i == 1) {
						System.out.println("Punkte 1");
						V1 = points;
					} else if (i == 2) {
						System.out.println("Punkte 2");
						V2 = points;
					} else if (i == 3) {
						System.out.println("Punkte 3");
						V3 = points;
					}
					key = true;
					System.out.println("Enter");
				} else {
					key = false;
				}

			}
		};

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
}
