package minispiele;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;



public class ZahlenMerken{

	boolean leicht = true;
	Timeline timeline, tlWhite, tlLeicht, tlSchwer, tlEnd, zeitleiste;
	VBox box1, box2, box3, box4, box5, box6, box7, box8;
	Label label1, label2, label3, label4, label5, label6, label7, label8, labelNum;
	Scene scene1, scene2, scene3, scene4, scene5, scene6, scene7, scene8, sceneTest;
	int vorherigeZahl, match;
	int score = 0, endscore;
	
	int counter = 1, zahl = 0;
	String t = " ";
	String checker, numbers;
	TextField text ;
	int zahl1 = 1653;
	int zahl2 = 7209;
	int zahl3 = 6682;
	int zahl4 = 4005;
	int zahl5 = 2508;
	int numCounter = 0;
	Rectangle r;

	File sound2 = new File("7209.wav");
	File sound4 = new File("4005.wav");
	File sound5 = new File("2508.wav");

	private AnchorPane minispielPane;
	
	public ZahlenMerken(AnchorPane mP){
		this.minispielPane = mP;
	}
	
	public ZahlenMerken() {
		
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

	public void start() {

		//counter = 1;
		//window = primaryScene;
		
		box1 = new VBox(20);
		label1 = new Label("Neue Zahl: ");
		label1.setTextFill(Color.web("1968EB"));
		label1.setFont(new Font("Cambria", 41)); //41
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
		//box1.styleProperty().set("-fx-background-image: url(minispiele/resources/1.jpg)");
		minispielPane.styleProperty().set("-fx-background-image: url(minispiele/resources/1.jpg)");
		box1.getChildren().addAll(label1, label2, label3, label4, r);
		
		
		minispielPane.getChildren().add(box1);
		startWaitTime();
		zeitLeiste();
	}
	
	public void stop() {
		minispielPane.getChildren().remove(0);
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
		//box2.styleProperty().set("-fx-background-image: url(minispiele/resources/1.jpg)");
		minispielPane.styleProperty().set("-fx-background-image: url(minispiele/resources/1.jpg)");
		box2.getChildren().addAll(labelWait, r);
		minispielPane.getChildren().remove(0);
		minispielPane.getChildren().add(box2);

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
		///////////////////////////////////////////////	
		//text.setFocusTraversable(false);
		///////////////////////////////////////////////
		text.clear();
		t = " ";
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
		//box4.styleProperty().set("-fx-background-image: url(minispiele/resources/1.jpg)");
		//minispielPane.styleProperty().set("-fx-background-image: url(minispiele/resources/1.jpg)");
		box4.getChildren().addAll(labelWait, text, label3, label4, r);
		
		minispielPane.getChildren().remove(0);
		minispielPane.getChildren().add(box4);
		
		//scene2 = new Scene(box4, 500, 500);
		//window.setScene(scene2);

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
///////////////////////////////////////////////
		text = new TextField();
		//text.setFocusTraversable(false);
///////////////////////////////////////////////
		text.clear();
		t = " ";
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
		//minispielPane.styleProperty().set("-fx-background-image: url(minispiele/resources/1.jpg)");
		//box5.styleProperty().set("-fx-background-image: url(minispiele/resources/1.jpg)");
		box5.getChildren().addAll(labelEnd, r);
		

		minispielPane.getChildren().remove(0);
		minispielPane.getChildren().add(box5);
		
		
		tlEnd = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tlEnd.stop();
				// closing the window has to be taken out in final game
				minispielPane.getChildren().clear();
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
			//////////////////////////////////////
			//////////////////////////////////////
			text.clear();
			t = " ";
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
			//box3.styleProperty().set("-fx-background-image: url(minispiele/resources/1.jpg)");
			box3.getChildren().addAll(label1, label2, label5, text, label3, label4, r);
			
			minispielPane.getChildren().remove(0);
			minispielPane.getChildren().add(box3);
			//scene3 = new Scene(box3, 500, 500);
			//window.setScene(scene3);
			

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
			//box6.styleProperty().set("-fx-background-image: url(minispiele/resources/1.jpg)");
			box6.getChildren().addAll(label1, label2, label3, label4, r);
			
			minispielPane.getChildren().remove(0);
			minispielPane.getChildren().add(box6);

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
			text.clear();
			t = " ";
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
			//box7.styleProperty().set("-fx-background-image: url(minispiele/resources/1.jpg)");
			box7.getChildren().addAll(label1, label2, label5, text, label3, label4, r);
			minispielPane.getChildren().remove(0);
			minispielPane.getChildren().add(box7);

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
			text.clear();
			t = " ";
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
			//box8.styleProperty().set("-fx-background-image: url(minispiele/resources/1.jpg)");
			box8.getChildren().addAll(labelWait, text, r);
			minispielPane.getChildren().remove(0);
			minispielPane.getChildren().add(box8);

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
	
	public void writeNumber(int zahl) {
		t = t + zahl;
		text.setText(t);
	}
	
	public void test() {
		box1 = new VBox(20);
		label1 = new Label("Neue Zahl: ");
		label1.setTextFill(Color.web("1968EB"));
		label1.setFont(new Font("Cambria", 41)); //41
		label2 = new Label("1234");
		label2.setFont(new Font("Cambria", 41));
		r = new Rectangle();
		r.setHeight(30);
		r.setWidth(0);
		r.setFill(Color.LAWNGREEN);	
		//box1.styleProperty().set("-fx-background-image: url(minispiele/resources/1.jpg)");
		box1.getChildren().addAll(label1, label2, r);
		minispielPane.getChildren().add(box1);
		
		timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				timeline.stop();
				System.out.println("waiting");
				testWait(1);
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		zeitLeiste();
	}
	
	public void testWait(int durchgang) {
		box2 = new VBox(20);
		Label labelWait = new Label("Wartezeit! Nicht ablenken lassen!");
		labelWait.setTextFill(Color.web("1968EB"));
		labelWait.setFont(new Font("Cambria", 25));
		r = new Rectangle();
		r.setHeight(30);
		r.setWidth(0);
		r.setFill(Color.LAWNGREEN);	
		//box2.styleProperty().set("-fx-background-image: url(minispiele/resources/1.jpg)");
		box2.getChildren().addAll(labelWait, r);
		minispielPane.getChildren().remove(0);
		minispielPane.getChildren().add(box2);

		tlWhite = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tlWhite.stop();
				System.out.println("wait is over");
				if(durchgang == 1) {
				sceneTest2();
				} 
				if(durchgang == 2) {
					sceneTest3();
				}
			}
		}));
		tlWhite.setCycleCount(Timeline.INDEFINITE);
		tlWhite.play();
		zeitLeiste();
	}
	
	public void sceneTest2() {
		box4 = new VBox(20);
		Label labelWait = new Label("Geben Sie die vorherige Zahl ein: ");
		labelWait.setTextFill(Color.web("1968EB"));
		labelWait.setFont(new Font("Cambria", 25));
		label3 = new Label("Neue Zahl: 0987");
		label3.setTextFill(Color.web("1968EB"));
		label3.setFont(new Font("Cambria", 25));
		text = new TextField();
		text.clear();
		t = " ";
		text.setFont(new Font("Cambria", 30));
		text.setFocusTraversable(false);
		text.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				text.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		r = new Rectangle();
		r.setHeight(30);
		r.setWidth(0);
		r.setFill(Color.LAWNGREEN);	
		box4.getChildren().addAll(labelWait, text, label3, r);
		minispielPane.getChildren().remove(0);
		minispielPane.getChildren().add(box4);

		timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				timeline.stop();
				System.out.println("Last Scene");
				if (text.getText().isEmpty() == true) {
					score = 0;
				} else if(text.getText() == "1234") {
						score = 1000;
				} else {
					score = 100;
				}
				testWait(2);
			}
			
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		zeitLeiste();
	}
	
	public void sceneTest3() {
		box4 = new VBox(20);
		text = new TextField();
		text.clear();
		t = " ";
		text.setFont(new Font("Cambria", 30));
		text.setFocusTraversable(false);
		text.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				text.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		label2 = new Label("Der Test für dieses Minispiel ist vorbei ist vorbei");
		label3 = new Label("Drücken Sie die Taste ENTER um fortzufahren!");
		box4.getChildren().addAll(text, label2, label3);
		minispielPane.getChildren().remove(0);
		minispielPane.getChildren().add(box4);
	}
}
