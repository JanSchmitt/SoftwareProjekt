package minispiele;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import sound.MusicLoader;

import javax.sound.sampled.*;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class Reaktionstest {
	// components for FX
	Stage win;
	Scene s, end, scenePoints;
	Timeline timeline;
	BorderPane b;
	Label l1, l2, l3, l1a, l2a, l3a, labelPunkte;
	Rectangle c;
	Timer timer1;
	Timer ttest;
	Timer ptest;
	TimerTask task, tttest, pptest;
	
	// variables
	int i = 0;
	int testpoints = 0, endpoints;
	int testtime = 5, time;
	int points, V1, V2, V3;
	File Beep = new File("Beep.wav");
	int k1, k2, k3;
	boolean ended = false;
	
	MusicLoader m;
	private AnchorPane minispielPane;
	
	// constructor for class Reaktionstest used in the actual application
	// needs anchorpane to set placement in application
	public Reaktionstest(AnchorPane mP){
		this.minispielPane = mP;
	}
	
	// constructor for Reaktionstest test version before the actual game starts
	public Reaktionstest() {
		System.out.println("Konstruktor für Test");
	}
	
	

	// method to start reaktion time game 
	public void start(MusicLoader music) {
		// time when scene changes
		m = music;
		k1 = 12;
		k2 = 15;
		k3 = 22;
		newScene();
	}
	
	// stops Reaktionstest
	public void stop() {
		minispielPane.getChildren().clear();
	}
	
	
	// test function is used for Reaktionstest test version
	public void test(HBox h, Scene test, Stage win) {
		Rectangle r = new Rectangle(400,400);
		r.setFill(Color.ORANGE);
		h.getChildren().addAll(r);
		ttest = new Timer();	// Timer used for the time-to-change
		ptest = new Timer();	// Timer used for the points
		// timertask for points; every millisecond passed a point is subtracted
		pptest = new TimerTask() {
			@Override
			public void run() {
				testpoints = testpoints -1;
			}			
		};
		// timertask for time-to-change; every millisecond  time variable is reduced; when reaching 0 the scene 
		// switches (and the point timer starts)
		tttest = new TimerTask() {
			@Override
			public void run() {
				testtime = testtime -1;
				if(testtime == 0) {
					r.setFill(Color.BLACK);
					testpoints = 1000;
					ptest.scheduleAtFixedRate(pptest, 1, 1);
				}				
			}			
		};
		win.setScene(test);
		ttest.scheduleAtFixedRate(tttest, 1000, 1000);
	}
	
	// when key is pressed during test run the testreact stops the timer and timertasks 
	// final points  and time-to-reaction are calculated
	public void testReact() {
		pptest.cancel();
		endpoints = testpoints;
		tttest.cancel();
		time = 1000 - endpoints;
	}
	
	/*// ??????????????
	public boolean isEnded() {
		return ended;
	}*/
	
	// returns the value of endpoints of test run
	public int getPoints() {
		return endpoints;
	}

	// sets up timer for points being reduced 
	// has to be called in every reaction stage
	public void point() {
		System.out.println("Da");
		timer1 = new Timer();
		points = 1000;
		task = new TimerTask() {
			@Override
			public void run() {
				points = points - 1;
			}
		};
		timer1.scheduleAtFixedRate(task, 1, 1);
	}

	// is called when the scene switches to a new stage
	// different values of k set the time-to-change to a different value
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

	// saves points gained during the application run
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
	
	// this function is used to set the scenes for the different reacting stages
	// all scenes use a different trigger and a different layout
	public void newScene() {
		m.load("src/sound/resources/Beep.wav");
		if (i == 0) {
			//win.setTitle("Reaktionstest");
			b = new BorderPane();
			c = new Rectangle(400, 400);
			c.setStroke(Color.BLACK);
			c.setFill(Color.ORANGE);
			b.setCenter(c);
			s = new Scene(b, 400, 400);
			//react();
			minispielPane.getChildren().add(b);
			time(k1);
		} else if (i == 1) {
			c.setFill(Color.GREEN);
			//win.setScene(s);
			System.out.println("Hier");
			point();
			time(k2);
		} else if (i == 2) {
			m.play();
			//playMusic(Beep);
			point();
			time(k3);
		} else if (i == 3) {
			c.setFill(Color.BLUE);
			m.play();
			//playMusic(Beep);
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
			minispielPane.getChildren().clear();
			minispielPane.getChildren().add(v);
		} else if (i == 5) {
			minispielPane.getChildren().clear();
		}
	}

	
	// playMusic plays auditive  signal to  react
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
