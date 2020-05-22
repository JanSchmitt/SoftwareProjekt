import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
<<<<<<< HEAD
	int counter;
	Button button;
	Label l1, l2, l3;
	Circle c;
	Timer timer1, timer2, timer3, timer;
=======
	Label l1, l2, l3, l1a, l2a, l3a;
	Rectangle c;
	Timer timer1;
>>>>>>> 521350dd00225b5cc06fe26ed8d2282a9f839c83
	TimerTask task;
	int i = 0;
	int points, V1, V2, V3;
	File Beep = new File("Beep.wav");
	int k1, k2, k3;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage reactio) {
		//Werte aus Config file einsetzen für k1, k2, k3
		k1 = 5;
		k2 = 5;
		k3 = 5;
		
		win = reactio;
<<<<<<< HEAD
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

=======
		newScene();
	}

	public void point() {
		System.out.println("Da");
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

	public void createButton() {
		s.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ev) {
				if (ev.getCode() == KeyCode.ENTER) {
					System.out.println("HAllo");
					task.cancel();
					if(i == 1) {
						V1 = points;
					}else if (i == 2) {
						V2 = points;
					}else if (i == 3) {
						V3 = points;
					}
				}else {
					task.cancel();
				}
			}
		});
	}

	public void newScene() {
		if (i == 0) {
			win.setTitle("Reaktionstest");
			b = new BorderPane();
			c = new Rectangle(400,400);
			c.setStroke(Color.BLACK);
			c.setFill(Color.ORANGE);
			b.setCenter(c);
			s = new Scene(b, 400, 400);
			createButton();
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
			l1a = new Label("Reaktionszeit Versuch 1: " + (2000 - V1) +" ms");
			l2 = new Label("Punkte Versuch 2: " + V2);
			l2a = new Label("Reaktionszeit Versuch 2: " + (2000 - V2)+ "ms");
			l3 = new Label("Punkte Versuch 3: " + V3);
			l3a = new Label("Reaktionszeit Versuch 3: " + (2000 - V3)+ "ms");
			v.getChildren().addAll(l1, l1a,l2,l2a, l3,l3a);
			time(10);
			end = new Scene(v, 400, 400);
			win.setScene(end);
		} else if (i == 5) {
			win.close();
		}
>>>>>>> 521350dd00225b5cc06fe26ed8d2282a9f839c83
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
<<<<<<< HEAD
=======

>>>>>>> 521350dd00225b5cc06fe26ed8d2282a9f839c83
}
