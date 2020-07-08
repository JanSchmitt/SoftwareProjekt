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
import minispiele.CatchTheBall;
import minispiele.MazeFX;
import minispiele.ZahlenMerken;
import minispiele.jumpAndDuck.JumpGame;
import pong.PongGame;

public class Testing {

	// FX components
	VBox boxHRS, boxTest, boxMinispiele, boxResult, v, z;
	HBox h;
	Label labelTest, labelErgebnis, labelMinispiele, labelPoints, labelTime, labelMinispiele2, labelText;
	Scene sceneTest, sceneErgebnis, sceneMinispiele, sceneRTT, sceneAfter, sceneZwischen;
	Button buttonStartTestHRS, buttonWeiter, buttonMinispieleWeiter, buttonNext, buttonOhneTest;
	Stage window;
	Timeline t;
	
	// used classes for the tests
	Port hrs = new Port();
	Data db;
	Initialization ini = new Initialization();
	Reaktionstest rtt;
	MazeFX mazeTest;
	ZahlenMerken merkenTest;
	JumpGame jump;
	PongGame pong;
	CatchTheBall catchBall;
	SerialPort sp;
	
	// variables
	int result, points, time, resultTest;
	int i = 0, curr, sum = 0, chosenP;
	
	AnchorPane minispielPane;

	// TESTING AREA
	
	// creates a test for the heart rate sensor
	public void createHRSTest(Stage window, int hfmax) {
		db = new Data();
		boxHRS = new VBox(20);
		
		// option to start the test
		buttonStartTestHRS = new Button("Drücken um Test des HRS zu starten");
		buttonStartTestHRS.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent ae) {
				chosenP = hrs.selectPort();
				ini.updatePort(chosenP);
				ini.updateHRSUSage(1);
				sp = hrs.usePort(ini.getPort());
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

				//database table is created
				try {
					db.createTableForTest(ini.getID()); // DB
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		// option to skip the heart rate test
		buttonOhneTest = new Button("Ohne HRS fortfahren");
		buttonOhneTest.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg) {
				// TODO Auto-generated method stub
				result = hrs.getHeartRate();
				ini.updateRuhepuls(result);
				ini.updateHRSUSage(0);

				// database table is created
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

		// border is updated in the init file
		try {
			Wini init = new Wini(new File("src/application/settings.ini"));
			init.put("OpSettings", "Grenze", hfmax);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// sets the result scene, mini game test are started after button press
	public void setResultScene(int result, Stage window) {
		buttonWeiter = new Button("Weiter zum Test der Minispiele");
		buttonWeiter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeb) {
				createMinispielTest(window);
			}
		});
		labelErgebnis = new Label("" + result);
		boxResult = new VBox(20);
		boxResult.getChildren().addAll(labelErgebnis, buttonWeiter);
		sceneErgebnis = new Scene(boxResult, 400, 400);
		window.setScene(sceneErgebnis);
	}

	// sets the scene before the mini game tests
	public void createMinispielTest(Stage window) {
		boxMinispiele = new VBox(20);
		labelMinispiele = new Label("Hier können alle vorkommenden Minispiele getestet");
		labelMinispiele2 = new Label("werden. Nächster Test: Maze");
		buttonMinispieleWeiter = new Button("Zum Teststart");
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

	// creates the scene for the mini game maze
	public void createMazeTest(Stage window) {
		minispielPane = new AnchorPane();
		mazeTest = new MazeFX(minispielPane);
		mazeTest.test();
		buttonMinispieleWeiter = new Button("Weiter zum nächsten Test");
		buttonMinispieleWeiter.setFocusTraversable(false);
		
		//exit maze test with button press
		buttonMinispieleWeiter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeMST) {
				zwischenScene1(window);
			}
		});
		minispielPane.getChildren().addAll(buttonMinispieleWeiter);
		sceneMinispiele = new Scene(minispielPane, 400, 400);
		window.setScene(sceneMinispiele);
		
		//KeyCode Listener for maze movement
		sceneMinispiele.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent argm) {
				if (argm.getCode() == KeyCode.LEFT || argm.getCode() == KeyCode.KP_LEFT) {
					mazeTest.moveLeftTest(window);
					window.setScene(sceneMinispiele);
				}
				if (argm.getCode() == KeyCode.RIGHT || argm.getCode() == KeyCode.KP_RIGHT) {
					mazeTest.moveRightTest(window);
					window.setScene(sceneMinispiele);
				}
				if (argm.getCode() == KeyCode.UP || argm.getCode() == KeyCode.KP_UP) {
					mazeTest.moveUpTest(window);
					window.setScene(sceneMinispiele);
				}
				if (argm.getCode() == KeyCode.DOWN || argm.getCode() == KeyCode.KP_DOWN) {
					mazeTest.moveDownTest(window);
					window.setScene(sceneMinispiele);
				}
			}

		});
	}

	// sets the scene after the maze test
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
	}

 	// creates the scene for the reaction time test
	public void createReaktionsTest(Stage window) {
		h = new HBox();
		sceneRTT = new Scene(h, 400, 400);
		rtt = new Reaktionstest();
		rtt.test(h, sceneRTT, window);
		
		// press ENTER key when reacting
		sceneRTT.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent keRTT) {
				if (keRTT.getCode() == KeyCode.ENTER) {
					rtt.testReact();
					points = rtt.getPoints();
					time = 1000 - points;
					zwischenScene2(window);
				}
			}

		});
	}

	// sets the scene after the reaction time test
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
	}

	// creates the scene for the zahlen merken test
	public void createMerkenTest(Stage window) {
		minispielPane = new AnchorPane();
		merkenTest = new ZahlenMerken(minispielPane);
		merkenTest.test();
		minispielPane.getChildren().addAll();
		sceneMinispiele = new Scene(minispielPane, 500, 500);
		sceneMinispiele.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				// KeyCode Listener for exiting the test
				if (e.getCode() == KeyCode.ENTER) {
					merkenTest.testStop();
					zwischenScene3(window);
				}
				// KeyCode Listener for the mini game test
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
	
	// sets the scene after the zahlen merken test
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
	}
	
	// creates the scene for the jump&duck test
	public void createJumpTest(Stage window) {
		minispielPane = new AnchorPane();
		jump = new JumpGame(minispielPane);
		jump.testRun();
		sceneMinispiele = new Scene(minispielPane, 800, 400);
		window.setScene(sceneMinispiele);

		// KeyCode Listener for the jump&duck test
		sceneMinispiele.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent argj) {
				if (argj.getCode() == KeyCode.UP || argj.getCode() == KeyCode.KP_UP) {
					jump.jump();
				}
				if (argj.getCode() == KeyCode.DOWN || argj.getCode() == KeyCode.KP_DOWN) {
					jump.duck();
				}
				
				//KeyCode Listener for exiting the mini game test
				if(argj.getCode() == KeyCode.ENTER) {
					jump.stop();
					zwischenScene4(window);
				}
			}

		});
	}
	
	// sets the scene after the jump&duck test
	public void zwischenScene4(Stage window) {
		boxMinispiele = new VBox(20);
		labelText = new Label("Nächster Test: Jump&Duck");
		buttonMinispieleWeiter = new Button("Weiter zum nächsten Test");
		buttonMinispieleWeiter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeMST) {
				createPongTest(window);
			}
		});
		boxMinispiele.getChildren().addAll(labelText, buttonMinispieleWeiter);
		sceneZwischen = new Scene(boxMinispiele, 400, 400);
		window.setScene(sceneZwischen);
	}
	
	// creates the scene for the pong test
	public void createPongTest(Stage window) {
		minispielPane = new AnchorPane();
		pong = new PongGame(minispielPane);
		pong.testRun();
		sceneMinispiele = new Scene(minispielPane, 700, 700);
		window.setScene(sceneMinispiele);

		// KeyCode Listener for the pong test
		sceneMinispiele.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent argj) {
				if (argj.getCode() == KeyCode.LEFT || argj.getCode() == KeyCode.KP_LEFT) {
					pong.movePlayerLeft();
				}
				if (argj.getCode() == KeyCode.RIGHT || argj.getCode() == KeyCode.KP_RIGHT) {
					pong.movePlayerRight();
				}
				// KeyCode Listener for exiting the mini game
				if(argj.getCode() == KeyCode.ENTER) {
					pong.stop();
					zwischenScene5(window);
				}
			}

		});
	}
	
	// sets the scene after the pong test
	public void zwischenScene5(Stage window) {
		boxMinispiele = new VBox(20);
		labelText = new Label("Nächster Test: Catch the Ball");
		buttonMinispieleWeiter = new Button("Weiter zum nächsten Test");
		buttonMinispieleWeiter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeMST) {
				createCatchTest(window);
			}
		});
		boxMinispiele.getChildren().addAll(labelText, buttonMinispieleWeiter);
		sceneZwischen = new Scene(boxMinispiele, 400, 400);
		window.setScene(sceneZwischen);
		// window.show();
	}
	
	//creates the scene for the catch mini game test
	public void createCatchTest(Stage window) {
		minispielPane = new AnchorPane();
		catchBall = new CatchTheBall(minispielPane);
		catchBall.testRun();
		sceneMinispiele = new Scene(minispielPane, 500, 500);
		window.setScene(sceneMinispiele);

		// KeyCode Listener for the catch test
		sceneMinispiele.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent argj) {
				if (argj.getCode() == KeyCode.LEFT || argj.getCode() == KeyCode.KP_LEFT) {
					catchBall.moveRectLeft();
				}
				if (argj.getCode() == KeyCode.RIGHT || argj.getCode() == KeyCode.KP_RIGHT) {
					catchBall.moveRectRight();
				}
				// KeyCode Listener for exiting the catch test
				if(argj.getCode() == KeyCode.ENTER) {
					catchBall.stop();
					sceneAfterTests(window);
				}
			}

		});
	}
	
	// sets the scene after the catch test (after all tests are done)
	public void sceneAfterTests(Stage window) {
		v = new VBox(20);
		labelPoints = new Label("Test der Minispiele ist beendet");
		labelTime = new Label("Nun können nochmal Fragen gestellt werden");
		buttonNext = new Button("Zum Hauptmenü");
		buttonNext.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeRTT) {
				window.close();
				//new main game is created 
				Game game = new Game();
				try {
					game.start(window);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		v.getChildren().addAll(labelPoints, labelTime, buttonNext);
		sceneAfter = new Scene(v, 400, 400);
		window.setScene(sceneAfter);
	}

}
