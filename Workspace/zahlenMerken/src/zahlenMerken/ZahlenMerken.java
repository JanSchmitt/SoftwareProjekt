package zahlenMerken;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ZahlenMerken extends Application {

	boolean leicht = false;
	Timeline timeline, tlWhite, tlLeicht, tlSchwer, tlEnd, zeitleiste;
	VBox box1, box2, box3, box4, box5, box6, box7, box8;
	Label label1, label2, label3, label4, label5, label6, label7, label8;
	Scene scene1, scene2, scene3, scene4, scene5, scene6, scene7, scene8;
	int vorherigeZahl, match;
	int score = 0, endscore;
	Stage window;
	int counter = 1, zahl = 0;
	String checker;
	TextField text;
	int zahl1 = 1653;
	int zahl2 = 7209;
	int zahl3 = 6682;
	int zahl4 = 4005;
	int zahl5 = 2508;
	Rectangle r;

	File sound2 = new File("7209.wav");
	File sound4 = new File("4005.wav");
	File sound5 = new File("2508.wav");

	public static void main(String[] args) {
		launch(args);
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
	
	public void zeitLeiste() {
		zeitleiste = new Timeline();
		zeitleiste.getKeyFrames().add(new KeyFrame(
				Duration.seconds(5), 
				new KeyValue(r.widthProperty() , 500)
				));
		zeitleiste.play();
	}

	@Override
	public void start(Stage primaryScene) throws Exception {

		counter = 1;
		window = primaryScene;
		box1 = new VBox(20);
		label1 = new Label("Neue Zahl: ");
		label1.setTextFill(Color.web("1968EB"));
		label1.setFont(new Font("Cambria", 41));
		label2 = new Label("       " + zahl1);
		label2.setFont(new Font("Cambria", 41));
		label3 = new Label("Punktestand: ");
		label3.setTextFill(Color.web("1968EB"));
		label3.setFont(new Font("Cambria", 41));
		label4 = new Label("       " + score);
		label4.setFont(new Font("Cambria", 41));
		r = new Rectangle();
		r.setHeight(30);
		r.setWidth(0);
		r.setFill(Color.LAWNGREEN);	
		box1.styleProperty().set("-fx-background-image: url(/zahlenMerken/1.jpg)");
		box1.getChildren().addAll(label1, label2, label3, label4, r);
		scene1 = new Scene(box1, 500, 500);
		window.setScene(scene1);
		window.setTitle("Zahlen Merken");
		window.show();

		startWaitTime();
		zeitLeiste();
	}

	public void startWaitTime() {
		timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				timeline.stop();
				System.out.println("waiting");
				setWhiteScene();
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	public void setWhiteScene() {
		box2 = new VBox(20);
		Label labelWait = new Label("Wartezeit! Nicht ablenken lassen!");
		labelWait.setTextFill(Color.web("1968EB"));
		labelWait.setFont(new Font("Cambria", 25));
		r = new Rectangle();
		r.setHeight(30);
		r.setWidth(0);
		r.setFill(Color.LAWNGREEN);	
		box2.styleProperty().set("-fx-background-image: url(/zahlenMerken/1.jpg)");
		box2.getChildren().addAll(labelWait, r);
		scene2 = new Scene(box2, 500, 500);
		window.setScene(scene2);

		tlWhite = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tlWhite.stop();
				System.out.println("wait is over");
				if (leicht == true) {
					if (counter < 5) {
						if (counter >= 2) {
							if (text.getText().isEmpty() == true) {
								score -= 1000;
							} else {
								checker = text.getText();
								checkLeicht(checker);
							}
						}
						sceneLeicht();
					} else {
						setLastScene();
					}
				} else {
					if (counter < 5) {
						if (counter < 2) {
							sceneVorSchwer();
						} else if (counter == 2) {
							sceneSchwer();
						} else if (counter > 2) {
							if (text.getText().isEmpty() == true) {
								score -= 1000;
							} else {
								checker = text.getText();
								checkSchwer(checker);
							}
							sceneSchwer();
						}
					} else {
						sceneNachSchwer();
					}
				}
			}
		}));
		tlWhite.setCycleCount(Timeline.INDEFINITE);
		tlWhite.play();
		zeitLeiste();
	}

	public void setLastScene() {
		box4 = new VBox(20);
		Label labelWait = new Label("Geben Sie die vorherige Zahl ein: ");
		labelWait.setTextFill(Color.web("1968EB"));
		labelWait.setFont(new Font("Cambria", 25));
		label3 = new Label("Punktestand: ");
		label3.setTextFill(Color.web("1968EB"));
		label3.setFont(new Font("Cambria", 41));
		label4 = new Label("       " + score);
		label4.setFont(new Font("Cambria", 41));
		text = new TextField();
		text.setFont(new Font("Cambria", 30));
		text.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				text.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		r = new Rectangle();
		r.setHeight(30);
		r.setWidth(0);
		r.setFill(Color.LAWNGREEN);	
		box4.styleProperty().set("-fx-background-image: url(/zahlenMerken/1.jpg)");
		box4.getChildren().addAll(labelWait, text, label3, label4, r);
		scene2 = new Scene(box4, 500, 500);
		window.setScene(scene2);

		tlLeicht = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tlLeicht.stop();
				System.out.println("Last Scene");
				if (text.getText().isEmpty() == true) {
					score -= score;
				} else {
					checker = text.getText();
					match = Integer.parseInt(checker);
					if (match == zahl5) {
						score += 500;
					} else {
						score -= 500;
					}
				}
				setEndScene();
			}
		}));
		tlLeicht.setCycleCount(Timeline.INDEFINITE);
		tlLeicht.play();
		zeitLeiste();
	}

	public void setEndScene() {
		box5 = new VBox(20);
		endscore = score;
		Label labelEnd = new Label("Endpunkte: " + endscore);
		labelEnd.setTextFill(Color.web("010203"));
		labelEnd.setFont(new Font("Cambria", 50));
		text = new TextField();
		text.setFont(new Font("Cambria", 30));
		text.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				text.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		r = new Rectangle();
		r.setHeight(30);
		r.setWidth(0);
		r.setFill(Color.LAWNGREEN);	
		box5.styleProperty().set("-fx-background-image: url(/zahlenMerken/1.jpg)");
		box5.getChildren().addAll(labelEnd, r);
		scene2 = new Scene(box5, 500, 500);
		window.setScene(scene2);

		tlEnd = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tlEnd.stop();
				// closing the window has to be taken out in final game
				window.close();
			}
		}));
		tlEnd.setCycleCount(Timeline.INDEFINITE);
		tlEnd.play();
		zeitLeiste();
	}

	public void checkLeicht(String checker) {
		if (leicht == true) {
			match = Integer.parseInt(checker);
			if (counter == 2) {
				if (match == zahl1) {
					score += 500;
				} else {
					score -= 500;
				}
			} else if (counter == 3) {
				if (match == zahl2) {
					score += 500;
				} else {
					score -= 500;
				}
			} else if (counter == 4) {
				if (match == zahl3) {
					score += 500;
				} else {
					score -= 500;
				}
			} else if (counter == 5) {
				if (match == zahl4) {
					score += 500;
				} else {
					score -= 500;
				}
			}
		}
	}

	public void checkSchwer(String checker) {
		if (leicht == false) {
			match = Integer.parseInt(checker);
			if (counter == 3) {
				if (match == zahl1) {
					score += 500;
				} else {
					score -= 500;
				}
			} else if (counter == 4) {
				if (match == zahl2) {
					score += 500;
				} else {
					score -= 500;
				}
			}
		}
	}

	public void sceneLeicht() {
		if (leicht == true) {
			counter += 1;
			box3 = new VBox(20);

			if (counter == 2) {
				zahl = zahl2;
				vorherigeZahl = zahl1;
			} else if (counter == 3) {
				zahl = zahl3;
				vorherigeZahl = zahl2;
			} else if (counter == 4) {
				zahl = zahl4;
				vorherigeZahl = zahl3;
			} else if (counter == 5) {
				zahl = zahl5;
				vorherigeZahl = zahl4;
			}

			label1 = new Label("Neue Zahl: ");
			label1.setTextFill(Color.web("1968EB"));
			label1.setFont(new Font("Cambria", 40));
			if (zahl == zahl2) {
				playMusic(sound2);
				label2 = new Label("Gut zuhören");
				label2.setFont(new Font("Cambria", 40));
			}
			if (zahl == zahl4) {
				playMusic(sound4);
				label2 = new Label("Gut zuhören");
				label2.setFont(new Font("Cambria", 40));
			}
			if (zahl == zahl5) {
				playMusic(sound5);
				label2 = new Label("Gut zuhören");
				label2.setFont(new Font("Cambria", 40));
			}
			if (zahl == zahl3 || zahl == zahl5) {
				label2 = new Label("      " + zahl);
				label2.setFont(new Font("Cambria", 40));
			}

			label5 = new Label("Geben Sie hier die vorherige Zahl ein: ");
			label5.setTextFill(Color.web("1968EB"));
			label5.setFont(new Font("Cambria", 25));
			text = new TextField();
			text.setFont(new Font("Cambria", 30));
			text.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!newValue.matches("\\d*")) {
					text.setText(newValue.replaceAll("[^\\d]", ""));
				}
			});
			r = new Rectangle();
			r.setHeight(30);
			r.setWidth(0);
			r.setFill(Color.LAWNGREEN);	
			label3 = new Label("Punktestand: ");
			label3.setTextFill(Color.web("1968EB"));
			label3.setFont(new Font("Cambria", 40));
			label4 = new Label("      " + score);
			label4.setFont(new Font("Cambria", 40));
			box3.styleProperty().set("-fx-background-image: url(/zahlenMerken/1.jpg)");
			box3.getChildren().addAll(label1, label2, label5, text, label3, label4, r);
			scene3 = new Scene(box3, 500, 500);
			window.setScene(scene3);

			startWaitTime();
			zeitLeiste();
		}
	}

	public void sceneVorSchwer() {
		if (leicht == false) {
			counter = counter + 1;
			box6 = new VBox(20);
			label1 = new Label("Neue Zahl: ");
			label1.setTextFill(Color.web("1968EB"));
			label1.setFont(new Font("Cambria", 41));
			playMusic(sound2);
			label2 = new Label("Gut zuhören");
			label2.setFont(new Font("Cambria", 40));
			label3 = new Label("Punktestand: ");
			label3.setTextFill(Color.web("1968EB"));
			label3.setFont(new Font("Cambria", 41));
			label4 = new Label("       " + score);
			label4.setFont(new Font("Cambria", 41));
			r = new Rectangle();
			r.setHeight(30);
			r.setWidth(0);
			r.setFill(Color.LAWNGREEN);	
			box6.styleProperty().set("-fx-background-image: url(/zahlenMerken/1.jpg)");
			box6.getChildren().addAll(label1, label2, label3, label4, r);
			scene6 = new Scene(box6, 500, 500);
			window.setScene(scene6);
			window.setTitle("Zahlen Merken");

			startWaitTime();
			zeitLeiste();
		}
	}

	public void sceneSchwer() {
		if (leicht == false) {
			counter = counter + 1;
			box7 = new VBox(20);

			if (counter == 3) {
				zahl = zahl3;
				vorherigeZahl = zahl1;
			} else if (counter == 4) {
				zahl = zahl4;
				vorherigeZahl = zahl2;
			} else if (counter == 5) {
				zahl = zahl5;
				vorherigeZahl = zahl3;
			}

			label1 = new Label("Neue Zahl: ");
			label1.setTextFill(Color.web("1968EB"));
			label1.setFont(new Font("Cambria", 40));
			if (zahl == zahl4) {
				playMusic(sound4);
				label2 = new Label("Gut zuhören");
				label2.setFont(new Font("Cambria", 40));
			}
			if (zahl == zahl5) {
				playMusic(sound5);
				label2 = new Label("Gut zuhören");
				label2.setFont(new Font("Cambria", 40));
			}
			if (zahl == zahl3 || zahl == zahl5) {
				label2 = new Label("      " + zahl);
				label2.setFont(new Font("Cambria", 40));
			}
			label5 = new Label("Geben Sie hier die vorherige Zahl ein: ");
			label5.setTextFill(Color.web("1968EB"));
			label5.setFont(new Font("Cambria", 40));
			text = new TextField();
			text.setFont(new Font("Cambria", 30));
			text.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!newValue.matches("\\d*")) {
					text.setText(newValue.replaceAll("[^\\d]", ""));
				}
			});
			r = new Rectangle();
			r.setHeight(30);
			r.setWidth(0);
			r.setFill(Color.LAWNGREEN);	
			label3 = new Label("Punktestand: ");
			label3.setTextFill(Color.web("1968EB"));
			label3.setFont(new Font("Cambria", 40));
			label4 = new Label("      " + score);
			label4.setFont(new Font("Cambria", 40));
			box7.styleProperty().set("-fx-background-image: url(/zahlenMerken/1.jpg)");
			box7.getChildren().addAll(label1, label2, label5, text, label3, label4, r);
			scene7 = new Scene(box7, 500, 500);
			window.setScene(scene7);

			startWaitTime();
			zeitLeiste();
		}
	}

	public void sceneNachSchwer() {
		if (leicht == false) {
			box8 = new VBox(20);
			Label labelWait = new Label("Geben Sie die vorherige Zahl ein: ");
			labelWait.setTextFill(Color.web("1968EB"));
			labelWait.setFont(new Font("Cambria", 41));
			text = new TextField();
			text.setFont(new Font("Cambria", 30));
			text.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!newValue.matches("\\d*")) {
					text.setText(newValue.replaceAll("[^\\d]", ""));
				}
			});
			r = new Rectangle();
			r.setHeight(30);
			r.setWidth(0);
			r.setFill(Color.LAWNGREEN);	
			box8.styleProperty().set("-fx-background-image: url(/zahlenMerken/1.jpg)");
			box8.getChildren().addAll(labelWait, text, r);
			scene8 = new Scene(box8, 500, 500);
			window.setScene(scene8);

			tlSchwer = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					tlSchwer.stop();
					System.out.println("Scene after hard");
					if (text.getText().isEmpty() == true) {
						score -= score;
					} else {
						checker = text.getText();
						match = Integer.parseInt(checker);
						if (match == zahl4) {
							score += 500;
						} else {
							score -= 500;
						}
					}
					setLastScene();
				}
			}));
			tlSchwer.setCycleCount(Timeline.INDEFINITE);
			tlSchwer.play();
			zeitLeiste();
		}
	}
}
