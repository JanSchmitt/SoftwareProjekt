import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.sound.sampled.*;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class Reaktionstest {
	Stage win;
	Scene s, end;
	Timeline timeline;
	BorderPane b;
	Label l1, l2, l3, l1a, l2a, l3a;
	Rectangle c;
	Timer timer1;
	TimerTask task;
	int i = 0;
	int testpoints = 1000;
	int testtime = 5;
	int points, V1, V2, V3;
	File Beep = new File("Beep.wav");
	int k1, k2, k3;

	public void start() {
		// Werte aus Config file einsetzen für k1, k2, k3
		k1 = 5;
		k2 = 5;
		k3 = 5;
		newScene();
	}
	
	public void test() {
		HBox h = new HBox();
		Rectangle r = new Rectangle(400,400);
		r.setFill(Color.ORANGE);
		h.getChildren().addAll(c);
		Scene test = new Scene(h,400,400);
		Timer ttest = new Timer();
		Timer ptest = new Timer();
		TimerTask pptest = new TimerTask() {

			@Override
			public void run() {
				testpoints = testpoints -1;
				
			}
			
		};
		TimerTask tttest = new TimerTask() {
			@Override
			public void run() {
				testtime = testtime -1;
				if(testtime == 0) {
					r.setFill(Color.BLACK);
					ptest.scheduleAtFixedRate(pptest, 1, 1);
				}
				
			}
			
		};
		win.setScene(test);
		win.show();
		ttest.scheduleAtFixedRate(tttest, 1000, 1000);
		test.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				if(arg0.getCode() == KeyCode.ENTER) {
					pptest.cancel();
					tttest.cancel();
					Label testlabel = new Label("Punkte im Test: "+ testpoints);
					h.getChildren().add(testlabel);
					win.setScene(test);
				}
				
			}
			
		});
		
	}

	public void point() {
		timer1 = new Timer();
		points = 2000;
		task = new TimerTask() {
			@Override
			public void run() {
				points = points - 1;
			}
		};
		timer1.scheduleAtFixedRate(task, 1, 1);
	}

	public void time(int k) {
		timeline = new Timeline(new KeyFrame(Duration.seconds(k), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				timeline.stop();
				i++;
				newScene();

			}
		}));
		timeline.play();
	}

	public void react() {
		task.cancel();
		if (i == 1) {
			V1 = points;
		} else if (i == 2) {
			V2 = points;
		} else if (i == 3) {
			V3 = points;
		}
	}

	public void newScene() {
		if (i == 0) {
			win.setTitle("Reaktionstest");
			b = new BorderPane();
			c = new Rectangle(400, 400);
			c.setStroke(Color.BLACK);
			c.setFill(Color.ORANGE);
			b.setCenter(c);
			s = new Scene(b, 400, 400);
			react();
			win.setScene(s);
			win.show();
			time(k1);
		} else if (i == 1) {
			c.setFill(Color.GREEN);
			win.setScene(s);
			System.out.println("Hier");
			point();
			time(k2);
		} else if (i == 2) {
			playMusic(Beep);
			point();
			time(k3);
		} else if (i == 3) {
			c.setFill(Color.BLUE);
			playMusic(Beep);
			point();
			time(5);
			System.out.println("Wir sind hier");
		} else if (i == 4) {
			timeline.stop();
			VBox v = new VBox(8);
			l1 = new Label("Punkte Versuch 1: " + V1);
			l1a = new Label("Reaktionszeit Versuch 1: " + (2000 - V1) + " ms");
			l2 = new Label("Punkte Versuch 2: " + V2);
			l2a = new Label("Reaktionszeit Versuch 2: " + (2000 - V2) + "ms");
			l3 = new Label("Punkte Versuch 3: " + V3);
			l3a = new Label("Reaktionszeit Versuch 3: " + (2000 - V3) + "ms");
			v.getChildren().addAll(l1, l1a, l2, l2a, l3, l3a);
			time(10);
			end = new Scene(v, 400, 400);
			win.setScene(end);
		} else if (i == 5) {
			win.close();
		}
	}

	public static void playMusic(File sound) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
		} catch (Exception e) {
			System.out.println("Fehler");
		}
	}

}
