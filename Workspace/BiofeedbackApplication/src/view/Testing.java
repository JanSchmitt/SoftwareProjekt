package view;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.ini4j.Wini;

import com.fazecast.jSerialComm.SerialPort;

import HRS.Port;
import application.Game;
import application.Initialization;
import data.Data;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import minispiele.Reaktionstest;
import minispiele.MazeFX;
import minispiele.ZahlenMerken;
import minispiele.jumpAndDuck.JumpGame;
import pong.PongGame;

public class Testing {

	VBox boxHRS, boxTest, boxMinispiele, boxResult, v, z;
	HBox h;
	Port hrs = new Port();
	Data db;
	Initialization ini = new Initialization();
	Label labelTest, labelErgebnis, labelMinispiele, labelPoints, labelTime, labelMinispiele2, labelText;
	Scene sceneTest, sceneErgebnis, sceneMinispiele, sceneRTT, sceneAfter, sceneZwischen;
	Button buttonStartTestHRS, buttonWeiter, buttonMinispieleWeiter, buttonNext, buttonOhneTest;
	int result, points, time, resultTest;
	Stage window;
	Reaktionstest rtt;
	MazeFX mazeTest;
	ZahlenMerken merkenTest;
	JumpGame jump;
	PongGame pong;
	SerialPort sp;
	int i = 0, curr, sum = 0, chosenP;
	Timeline t;
	AnchorPane minispielPane;

	public void createHRSTest(Stage window, int hfmax) {
		db = new Data();
		boxHRS = new VBox(20);
		// hrs = new Port();
		buttonStartTestHRS = new Button("Drücken um Test des HRS zu starten");
		buttonStartTestHRS.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent ae) {
				chosenP = hrs.selectPort();
				ini.updatePort(chosenP);
				sp = hrs.usePort(ini.getPort());
				// chosenP = hrs.getChosenPort();
				t = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent te) {
						time++;
						if (time % 1 == 0 && time <= 10) {
							curr = hrs.getHeartR(sp);
							System.out.println("" + curr);
							sum = sum + curr;
						}
						if (time > 10) {
							t.stop();
							hrs.close(sp);
							resultTest = sum / 10;
							System.out.println("" + resultTest);
							ini.updateRuhepuls(resultTest);
							setResultScene(resultTest, window);
						}
					}

				}));
				t.setCycleCount(Timeline.INDEFINITE);
				t.play();

				try {
					db.createTableForTest(ini.getID()); // DB
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		buttonOhneTest = new Button("Ohne HRS fortfahren");
		buttonOhneTest.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg) {
				// TODO Auto-generated method stub
				result = hrs.getHeartRate();
				ini.updateRuhepuls(result);

				try {
					db.createTableForTest(ini.getID());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				setResultScene(result, window);
			}

		});

		labelTest = new Label("Kurzer Test des Herzratensensors!");
		boxHRS.getChildren().addAll(labelTest, buttonStartTestHRS, buttonOhneTest);
		sceneTest = new Scene(boxHRS, 400, 400);
		window.setScene(sceneTest);
		window.show();

		try {
			Wini init = new Wini(new File("src/application/settings.ini"));
			init.put("OpSettings", "Grenze", hfmax);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setResultScene(int result, Stage window) {
		buttonWeiter = new Button("Weiter zum Test der Minispiele");
		buttonWeiter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeb) {
				createMinispielTest(window);
				// hrs.start();
			}
		});
		labelErgebnis = new Label("" + result);
		boxResult = new VBox(20);
		boxResult.getChildren().addAll(labelErgebnis, buttonWeiter);
		sceneErgebnis = new Scene(boxResult, 400, 400);
		window.setScene(sceneErgebnis);
	}

	public void createMinispielTest(Stage window) {
		boxMinispiele = new VBox(20);
		labelMinispiele = new Label("Hier können alle vorkommenden Minispiele getestet");
		labelMinispiele2 = new Label("werden. Nächster Test: Maze");
		buttonMinispieleWeiter = new Button("Zum Spielstart");
		buttonMinispieleWeiter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeMST) {
				createMazeTest(window);
			}
		});
		boxMinispiele.getChildren().addAll(labelMinispiele, labelMinispiele2, buttonMinispieleWeiter);
		sceneMinispiele = new Scene(boxMinispiele, 400, 400);
		window.setScene(sceneMinispiele);
	}

	public void createMazeTest(Stage window) {
		minispielPane = new AnchorPane();
		mazeTest = new MazeFX(minispielPane);
		mazeTest.test();
		buttonMinispieleWeiter = new Button("Weiter zum nächsten Test");
		buttonMinispieleWeiter.setFocusTraversable(false);
		buttonMinispieleWeiter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeMST) {
				zwischenScene1(window);
			}
		});
		minispielPane.getChildren().addAll(buttonMinispieleWeiter);
		sceneMinispiele = new Scene(minispielPane, 400, 400);
		window.setScene(sceneMinispiele);
		// window.show();

		sceneMinispiele.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent argm) {
				if (argm.getCode() == KeyCode.LEFT || argm.getCode() == KeyCode.KP_LEFT) {
					mazeTest.moveLeftTest(window);
				}
				if (argm.getCode() == KeyCode.RIGHT || argm.getCode() == KeyCode.KP_RIGHT) {
					mazeTest.moveRightTest(window);
				}
				if (argm.getCode() == KeyCode.UP || argm.getCode() == KeyCode.KP_UP) {
					mazeTest.moveUpTest(window);
				}
				if (argm.getCode() == KeyCode.DOWN || argm.getCode() == KeyCode.KP_DOWN) {
					mazeTest.moveDownTest(window);
				}
			}

		});
	}

	public void zwischenScene1(Stage window) {
		z = new VBox(20);
		labelText = new Label("Nächster Test: Reaktionszeit");
		buttonMinispieleWeiter = new Button("Weiter zum nächsten Test");
		buttonMinispieleWeiter.setFocusTraversable(false);
		buttonMinispieleWeiter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeMST) {
				createReaktionsTest(window);
			}
		});
		z.getChildren().addAll(labelText, buttonMinispieleWeiter);
		sceneZwischen = new Scene(z, 400, 400);
		window.setScene(sceneZwischen);
		// window.show();
	}

	public void createReaktionsTest(Stage window) {
		h = new HBox();
		sceneRTT = new Scene(h, 400, 400);
		rtt = new Reaktionstest();
		rtt.test(h, sceneRTT, window);
		sceneRTT.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent keRTT) {
				if (keRTT.getCode() == KeyCode.ENTER) {
					rtt.testReact();
					points = rtt.getPoints();
					time = 1000 - points;
					/*
					 * try { //DB db.saveDataInTable(ini.getID(), 0, hrs.getHeartRate(), points,
					 * "test", "test"); } catch (SQLException e) { // TODO Auto-generated catch
					 * block e.printStackTrace(); }
					 */
					zwischenScene2(window);
				}
			}

		});
	}

	public void zwischenScene2(Stage window) {
		boxMinispiele = new VBox(20);
		labelText = new Label("Nächster Test: Zahlen Merken");
		buttonMinispieleWeiter = new Button("Weiter zum nächsten Test");
		buttonMinispieleWeiter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeMST) {
				createMerkenTest(window);
			}
		});
		boxMinispiele.getChildren().addAll(labelText, buttonMinispieleWeiter);
		sceneZwischen = new Scene(boxMinispiele, 400, 400);
		window.setScene(sceneZwischen);
		// window.show();
	}

	public void createMerkenTest(Stage window) {
		minispielPane = new AnchorPane();
		merkenTest = new ZahlenMerken(minispielPane);
		merkenTest.test();
		/*
		 * buttonMinispieleWeiter = new Button("Weiter zum nächsten Test");
		 * buttonMinispieleWeiter.setFocusTraversable(false);
		 * buttonMinispieleWeiter.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent aeMST) { sceneAfterTests(window); }
		 * });
		 */
		// minispielPane.getChildren().addAll();
		minispielPane.getChildren().addAll();
		sceneMinispiele = new Scene(minispielPane, 500, 500);
		sceneMinispiele.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.ENTER) {
					sceneAfterTests(window);
				}
				if (e.getCode() == KeyCode.DIGIT0 || e.getCode() == KeyCode.NUMPAD0) {
					merkenTest.writeNumber(0);
				}
				if (e.getCode() == KeyCode.DIGIT1 || e.getCode() == KeyCode.NUMPAD1) {
					merkenTest.writeNumber(1);
				}
				if (e.getCode() == KeyCode.DIGIT2 || e.getCode() == KeyCode.NUMPAD2) {
					merkenTest.writeNumber(2);
				}
				if (e.getCode() == KeyCode.DIGIT3 || e.getCode() == KeyCode.NUMPAD3) {
					merkenTest.writeNumber(3);
				}
				if (e.getCode() == KeyCode.DIGIT4 || e.getCode() == KeyCode.NUMPAD4) {
					merkenTest.writeNumber(4);
				}
				if (e.getCode() == KeyCode.DIGIT5 || e.getCode() == KeyCode.NUMPAD5) {
					merkenTest.writeNumber(5);
				}
				if (e.getCode() == KeyCode.DIGIT6 || e.getCode() == KeyCode.NUMPAD6) {
					merkenTest.writeNumber(6);
				}
				if (e.getCode() == KeyCode.DIGIT7 || e.getCode() == KeyCode.NUMPAD7) {
					merkenTest.writeNumber(7);
				}
				if (e.getCode() == KeyCode.DIGIT8 || e.getCode() == KeyCode.NUMPAD8) {
					merkenTest.writeNumber(8);
				}
				if (e.getCode() == KeyCode.DIGIT9 || e.getCode() == KeyCode.NUMPAD9) {
					merkenTest.writeNumber(9);
				}
			}
		});
		window.setScene(sceneMinispiele);
		window.show();
	}
	
	public void zwischenScene3(Stage window) {
		boxMinispiele = new VBox(20);
		labelText = new Label("Nächster Test: Jump&Duck");
		buttonMinispieleWeiter = new Button("Weiter zum nächsten Test");
		buttonMinispieleWeiter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeMST) {
				createJumpTest(window);
			}
		});
		boxMinispiele.getChildren().addAll(labelText, buttonMinispieleWeiter);
		sceneZwischen = new Scene(boxMinispiele, 400, 400);
		window.setScene(sceneZwischen);
		// window.show();
	}
	
	public void createJumpTest(Stage window) {
		minispielPane = new AnchorPane();
		jump = new JumpGame(minispielPane);
		jump.testRun();
		sceneMinispiele = new Scene(minispielPane, 400, 400);
		window.setScene(sceneMinispiele);

		sceneMinispiele.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent argj) {
				if (argj.getCode() == KeyCode.UP || argj.getCode() == KeyCode.KP_UP) {
					jump.jump();
				}
				if (argj.getCode() == KeyCode.DOWN || argj.getCode() == KeyCode.KP_DOWN) {
					jump.duck();
				}
				if(argj.getCode() == KeyCode.ENTER) {
					zwischenScene4(window);
				}
			}

		});
	}
	
	public void zwischenScene4(Stage window) {
		boxMinispiele = new VBox(20);
		labelText = new Label("Nächster Test: Jump&Duck");
		buttonMinispieleWeiter = new Button("Weiter zum nächsten Test");
		buttonMinispieleWeiter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeMST) {
				createJumpTest(window);
			}
		});
		boxMinispiele.getChildren().addAll(labelText, buttonMinispieleWeiter);
		sceneZwischen = new Scene(boxMinispiele, 400, 400);
		window.setScene(sceneZwischen);
		// window.show();
	}
	
	public void createPongTest(Stage window) {
		minispielPane = new AnchorPane();
		pong = new PongGame(minispielPane);
		pong.testRun();
		sceneMinispiele = new Scene(minispielPane, 400, 400);
		window.setScene(sceneMinispiele);

		sceneMinispiele.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent argj) {
				if (argj.getCode() == KeyCode.LEFT || argj.getCode() == KeyCode.KP_LEFT) {
					pong.movePlayerLeft();
				}
				if (argj.getCode() == KeyCode.RIGHT || argj.getCode() == KeyCode.KP_RIGHT) {
					pong.movePlayerRight();
				}
				if(argj.getCode() == KeyCode.ENTER) {
					sceneAfterTests(window);
				}
			}

		});
	}

	public void sceneAfterTests(Stage window) {
		v = new VBox(20);
		labelPoints = new Label("Test der Minispiele ist beendet");
		labelTime = new Label("Nun können nochmal Fragen gestellt werden");
		buttonNext = new Button("Zum Hauptmenü");
		buttonNext.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeRTT) {
				window.close();
				Game game = new Game();
				try {
					game.start(window);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// hrs.start();
			}

		});
		v.getChildren().addAll(labelPoints, labelTime, buttonNext);
		sceneAfter = new Scene(v, 400, 400);
		window.setScene(sceneAfter);
	}

}
