package view;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.fazecast.jSerialComm.SerialPort;

import HRS.Port;
import application.Initialization;
import data.Data;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
//import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
//import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import minispiele.ZahlenMerken;
import minispiele.CatchTheBall;
import minispiele.MazeFX;
import minispiele.Reaktionstest;
import minispiele.jumpAndDuck.*;
import model.InfoLabel;
import pong.PongGame;
import timer.Score;

public class GameViewManager {

	// constants
	// {
	// Window size

	private static final int WINDOW_WIDTH = 1440;
	private static final int WINDOW_HEIGHT = 800;

	private static final int GAME_WIDTH = 1008; // 0.7*WINDOW_WIDTH
	private static final int GAME_HEIGHT = 800;

	private static final int RIGHT_PANE_WIDTH = WINDOW_WIDTH - GAME_WIDTH; // 0.3*WINDOW_WIDTH = //432
	private static final int RIGHT_PANE_HEIGHT = 800;
	// Entities size
	private static final int ENTITIES_SIZE = 60;
	// }

	// Ebenen
	public AnchorPane anchor0;
	public AnchorPane gamePane;
	public AnchorPane rightPane;
	public AnchorPane minispielPane;
	public Scene gameScene;
	public Stage gameStage;
	public Stage menuStage;

	// Gridpane for Background IMG
	private GridPane gridPane1;
	private GridPane gridPane2;

	// Timer
	public AnimationTimer gameTimer;
	public int time;
	public int tl_time = 0;
	int timecounter = 1;

	Random RAND;
	private int angle;

	// Database variables
	public String spiel;
	public int ID;
	public int currentScore;
	String currentGame = "Hauptspiel";
	int mode;
	String currentMode;
	String stressLevel;
	String currentStressLevel = "normal";
	int alter;
	int gewicht;
	String sportlichkeit;
	String sysTime;
	
	// heart rate sensors
	public int heartrate;
	int hrcounter = 1;
	int hrFromSensor;
	SerialPort sp;
	int iniPort;
	int sl;

	// KeyEvents
	private boolean isLeftKeyPressed;
	private boolean isRigtKeyPressed;
	private boolean shooting;

	// IMG URL
	public final static String BACKGROUND_IMG = "view/resources/blue.png";
	public final static String RED_BACKGROUND_IMG = "view/resources/red.png";
	public final static String GREEN_BACKGROUND_IMG = "view/resources/green.png";
	private final static String METEOR_GREY_IMG = "view/resources/meteorGrey_med1.png";
	private final static String METEOR_BROWN_IMG = "view/resources/meteorBrown_med1.png";
	private final static String LASER_IMG = "view/resources/laserGreen04.png";

	// IMG Objects to create
	private ImageView player;
	private ImageView[] brownMeteors;
	private ImageView[] greyMeteors;
	private ImageView laserShot;
	private ArrayList<Node> lasers = new ArrayList<Node>();
	private InfoLabel scoreLabel, heartLabel;
	private Score sc;
	
	// mini games 
	private ZahlenMerken zahlenMerken;
	private boolean zahlenMerkenOn = false;
	private MazeFX maze;
	private boolean mazeOn = false;
	private Reaktionstest reaktionstest;
	private boolean reaktionstestOn = false;
	private JumpGame jumpGame;
	private boolean jumpGameOn = false;
	private CatchTheBall ballcatch;
	boolean ballcatchOn = false;
	private PongGame pong;
	private boolean pongOn = false;

	// Biofeedback manager
	private FeedbackManager feedback = new FeedbackManager(this);
	private Initialization ini = new Initialization();
	private Port sensor = new Port();
	private Data database = new Data();
	private Text scoreDecrease[] = { null, null, null };

	// text example
	Text bsp;

	public GameViewManager() {
		if (ini.getHRSUSage() == 1) {
			sp = sensor.usePort(ini.getPort());
		}
		initializeStage();
		createKeyListener();
		RAND = new Random();
	}

	private void createKeyListener() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.A) { // LEFT
					isLeftKeyPressed = true;
				} else if (e.getCode() == KeyCode.D) { // RIGHT
					isRigtKeyPressed = true;
				} else if (e.getCode() == KeyCode.SPACE) {
					if (!shooting) { // wenn leertaste losgelassen wurde
						if (sc.time % 2 == 0) {
							laserShot = new ImageView(LASER_IMG);
							Node newLaserShot = laserShot;
							newLaserShot.relocate(player.getLayoutX() + ENTITIES_SIZE / 2 - 13 / 2,
									player.getLayoutY());
							lasers.add(newLaserShot);
							gamePane.getChildren().add(newLaserShot);
							System.out.println("size: " + lasers.size());
							shooting = true;
						}

					}
				} 
				if (e.getCode() == KeyCode.MINUS) {
					// Entspannt
					stressLevel = "entspannt";
					// Mitwirkend
					if (ini.getMode() == 2) {
						// grünes Bild
						feedback.setMode(1);
					}
					// Entgegenwirkend
					if (ini.getMode() == 1) {
						// rotes Bild
						feedback.setMode(2);
					}
				} else if (e.getCode() == KeyCode.N) {
					// Normal
					stressLevel = "normal";
					feedback.setMode(0);
				} else if (e.getCode() == KeyCode.PLUS) {
					// Gestresst
					stressLevel = "gestresst";
					// Mitwirkend
					if (ini.getMode() == 2) {
						// rotes Bild
						feedback.setMode(2);
					}
					// Entgegenwirkend
					if (ini.getMode() == 1) {
						// grünes Bild
						feedback.setMode(1);
					}
				}
				// KeyCode Listener for mini game maze
				if (mazeOn == true) {
					if (e.getCode() == KeyCode.LEFT) {
						maze.moveLeft();
					}
					if (e.getCode() == KeyCode.RIGHT) {
						maze.moveRight();
					}
					if (e.getCode() == KeyCode.DOWN) {
						maze.moveDown();
					}
					if (e.getCode() == KeyCode.UP) {
						maze.moveUp();
					}
				}
				// KeyCode Listener for mini game zahlen merken
				if (zahlenMerkenOn == true) {
					// write into textfield
					if (e.getCode() == KeyCode.DIGIT0 || e.getCode() == KeyCode.NUMPAD0) {
						zahlenMerken.writeNumber(0);
					}
					if (e.getCode() == KeyCode.DIGIT1 || e.getCode() == KeyCode.NUMPAD1) {
						zahlenMerken.writeNumber(1);
					}
					if (e.getCode() == KeyCode.DIGIT2 || e.getCode() == KeyCode.NUMPAD2) {
						zahlenMerken.writeNumber(2);
					}
					if (e.getCode() == KeyCode.DIGIT3 || e.getCode() == KeyCode.NUMPAD3) {
						zahlenMerken.writeNumber(3);
					}
					if (e.getCode() == KeyCode.DIGIT4 || e.getCode() == KeyCode.NUMPAD4) {
						zahlenMerken.writeNumber(4);
					}
					if (e.getCode() == KeyCode.DIGIT5 || e.getCode() == KeyCode.NUMPAD5) {
						zahlenMerken.writeNumber(5);
					}
					if (e.getCode() == KeyCode.DIGIT6 || e.getCode() == KeyCode.NUMPAD6) {
						zahlenMerken.writeNumber(6);
					}
					if (e.getCode() == KeyCode.DIGIT7 || e.getCode() == KeyCode.NUMPAD7) {
						zahlenMerken.writeNumber(7);
					}
					if (e.getCode() == KeyCode.DIGIT8 || e.getCode() == KeyCode.NUMPAD8) {
						zahlenMerken.writeNumber(8);
					}
					if (e.getCode() == KeyCode.DIGIT9 || e.getCode() == KeyCode.NUMPAD9) {
						zahlenMerken.writeNumber(9);
					}
				}
				// KeyCode Listener for mini game reaction time
				if (reaktionstestOn == true) {
					if (e.getCode() == KeyCode.ENTER) {
						reaktionstest.react();
					}
				}
				// KeyCode Listener for mini game jump&duck
				if (jumpGameOn == true) {
					if (e.getCode() == KeyCode.UP) {
						jumpGame.jump();
					}
					if (e.getCode() == KeyCode.DOWN) {
						jumpGame.duck();
					}
				}
				// KeyCode Listener for mini game catch the ball
				if (ballcatchOn == true) {
					if (e.getCode() == KeyCode.RIGHT) {
						ballcatch.moveRect();
					}
					if (e.getCode() == KeyCode.LEFT) {
						ballcatch.moveLeft();
					}
				}
				// KeyCode Listener for mini game pong
				if (pongOn == true) {
					if (e.getCode() == KeyCode.LEFT) {
						pong.movePlayerLeft();
					}
					if (e.getCode() == KeyCode.RIGHT) {
						pong.movePlayerRight();
					}
				}
			}
		});

		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.A) {
					isLeftKeyPressed = false;
				} else if (e.getCode() == KeyCode.D) {
					isRigtKeyPressed = false;
				} else if (e.getCode() == KeyCode.SPACE) {
					shooting = false;
				}
			}

		});
	}

	private void initializeStage() {

		anchor0 = new AnchorPane();
		anchor0.setLayoutX(0);
		anchor0.setLayoutY(0);
		anchor0.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT); // breite vom fenster
		anchor0.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		anchor0.setMaxSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		// Linke Pane: SpaceInvadors
		gamePane = new AnchorPane();
		// gamePane.boundsInLocalProperty();
		// gamePane.boundsInParentProperty();
		gamePane.setLayoutX(0);
		gamePane.setLayoutY(0);
		gamePane.setPrefSize(GAME_WIDTH, GAME_HEIGHT); // 1008,800
		gamePane.setFocusTraversable(true);

		// Rechte Pane: Minispiele etc
		rightPane = new AnchorPane();
		rightPane.setLayoutX(GAME_WIDTH);
		rightPane.setLayoutY(0);
		rightPane.setPrefSize(RIGHT_PANE_WIDTH, RIGHT_PANE_HEIGHT); // 432

		minispielPane = new AnchorPane();
		minispielPane.setLayoutX(GAME_WIDTH + 2);
		minispielPane.setLayoutY(100);
		minispielPane.setPrefSize(RIGHT_PANE_WIDTH - 2, RIGHT_PANE_WIDTH - 2);

		// rightPane.getChildren().add(minispielPane);

		anchor0.getChildren().addAll(gamePane, rightPane, minispielPane);

		// gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameScene = new Scene(anchor0);
		gameStage = new Stage();// eine Stage
		gameStage.setScene(gameScene);
		gameStage.setResizable(false);

	}

	public void createNewGame(Stage menuStage) {
		this.menuStage = menuStage;
		// this.menuStage.hide();
		this.menuStage.close();

		sc = new Score();
		sc.startTimer();

		createBackground();
		createPlayer();
		createGameElements();

		startTimer();
		createGameLoop();

		gameStage.setTitle("Biofeedback Anwendung");
		gameStage.show();
	}

	private void createGameLoop() {
		gameTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				moveBackground();
				moveGameElements();
				shootLaser();
				checkIfElemetsAreUnderShip();
				collision();
				moveShip();
				moveScoreDecrease();
				checkTime();
			}
		};
		gameTimer.start();
	}

	// starts the timer for methods that are called every second
	public void startTimer() {
		Timeline timl_1s = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent te) {
				tl_time++;
				updateScoreLabel();
				updateHeartLabel();
				//save();
				//checkHR();

			}
		}));
		timl_1s.setCycleCount(Timeline.INDEFINITE);
		timl_1s.play();
	}

	// checks the Heart Rate and changes the feedback based on it
	public void checkHR() {
		if (ini.getHRSUSage() == 1) {
			if (sensor.getHeartR(sp) > ini.getGrenze()) {
				if (stressLevel != "gestresst") {
					// Mitwirkend
					if (ini.getMode() == 2) {
						// rotes Bild
						feedback.setMode(2);
					}
					// Entgegenwirkend
					if (ini.getMode() == 1) {
						// grünes Bild
						feedback.setMode(1);
					}
					// Neutral
					if (ini.getMode() == 0) {
						feedback.setMode(0);
					}
					// Stresslevel ändern
					stressLevel = "gestresst";
				}
			} else if (sensor.getHeartR(sp) <= Integer.parseInt(ini.getRP()) - 10) {
				if (stressLevel != "entspannt") {
					// Mitwirkend
					if (ini.getMode() == 2) {
						// grünes Bild
						feedback.setMode(1);
					}
					// Entgegenwirkend
					if (ini.getMode() == 1) {
						// rotes Bild
						feedback.setMode(2);
					}
					// Neutral
					if (ini.getMode() == 0) {
						// graues Bild
						feedback.setMode(0);
					}
					stressLevel = "entspannt";
				}
			} else {
				if (stressLevel != "normal") {
					feedback.setMode(0);
				}
				stressLevel = "normal";
			}
		}
	}

	// saves all important data in the database
	public void save() {
		ID = ini.getID();
		time = sc.getTime();
		spiel = currentGame;
		if (ini.getHRSUSage() == 1) {
			heartrate = sensor.getHeartR(sp);
		} else {
			heartrate = sensor.getHeartRate();
		}
		currentScore = sc.score;
		mode = ini.getMode();
		currentMode = " " + mode;
		currentStressLevel = stressLevel;
		alter = ini.getAlter();
		gewicht = ini.getGewicht();
		sportlichkeit = ini.getSportlichkeit();
		sysTime = getSysTime();

		try {
			database.saveDataInTable(ID, sysTime, alter, gewicht, sportlichkeit, time, heartrate, currentScore,
					currentMode, currentStressLevel, currentGame);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// checks time and enables mini games based on it
	public void checkTime() {
		// mini game maze is shown
		if (sc.getTime() == ini.getMinigameTime(1)) {
			maze = new MazeFX(minispielPane);
			maze.start();
			mazeOn = true;
			currentGame = "maze";
		}
		// mini game zahlen merken is shown
		if (sc.getTime() == ini.getMinigameTime(2)) {
			maze.stop();
			sc.score += maze.getPoints();
			mazeOn = false;
			zahlenMerken = new ZahlenMerken(minispielPane);
			zahlenMerken.start();
			zahlenMerkenOn = true;
			currentGame = "ZahlenMerken";
		}
		// mini game reaction time is shown
		if (sc.getTime() == ini.getMinigameTime(3)) {
			zahlenMerken.stop();
			zahlenMerkenOn = false;
			reaktionstest = new Reaktionstest(minispielPane);
			reaktionstest.start();
			reaktionstestOn = true;
			currentGame = "Reaktionszeit";
		}
		// mini game jump&duck is shown
		if (sc.getTime() == ini.getMinigameTime(4)) {
			reaktionstest.stop();
			sc.score = sc.score + reaktionstest.getPoints();
			reaktionstestOn = false;
			jumpGame = new JumpGame(minispielPane);
			jumpGame.run();
			jumpGameOn = true;
			currentGame = "Jump&Duck";
		}
		// mini game catch the ball is shown
		if (sc.getTime() == ini.getMinigameTime(5)) {
			jumpGame.stop();
			jumpGameOn = false;
			sc.score = sc.score + jumpGame.getPoints();
			ballcatch = new CatchTheBall(minispielPane);
			ballcatch.start();
			ballcatchOn = true;
			currentGame = "CatchTheBall";
		}
		// mini game pong is shown
		if (sc.getTime() == ini.getMinigameTime(6)) {
			ballcatch.stop();
			ballcatchOn = false;
			sc.score = sc.score + ballcatch.getPoints();
			pong = new PongGame(minispielPane);
			pong.run();
			pongOn = true;
			currentGame = "Pong";
		}
		// mini game pong is closed (only if necessary)
		if (sc.getTime() == ini.getMinigameTime(6) + 60) {
			pong.stop();
			pongOn = false;
			sc.score = sc.score + pong.getPoints();
		}
	}

	// Meteors(Enemies)
	////////////////////////////////
	private void createGameElements() {

		// Score Label
		scoreLabel = new InfoLabel("Score: " + sc.score);
		scoreLabel.setLayoutX(RIGHT_PANE_WIDTH / 2 - scoreLabel.IMG_WIDTH / 2);
		scoreLabel.setLayoutY(20);
		rightPane.getChildren().add(scoreLabel);

		if (ini.getHRSUSage() == 1) {
			heartLabel = new InfoLabel("" + sensor.getHeartR(sp));
		} else {
			heartLabel = new InfoLabel(" " + sensor.getHeartRate());
		}
		heartLabel.setLayoutX(RIGHT_PANE_WIDTH / 2 - heartLabel.IMG_WIDTH / 2);
		heartLabel.setLayoutY(RIGHT_PANE_HEIGHT - 180);
		rightPane.getChildren().add(heartLabel);

		// jeweils max 3 meteors generaten
		brownMeteors = new ImageView[3];
		for (int i = 0; i < brownMeteors.length; i++) {
			// erzeugen der ImageView Objekte
			brownMeteors[i] = new ImageView(METEOR_BROWN_IMG);

			// auf selbe Größe wie Player setzen = 60x60
			brownMeteors[i].setFitHeight(ENTITIES_SIZE);
			brownMeteors[i].setFitWidth(ENTITIES_SIZE);
			//
			setNewElementPosition(brownMeteors[i]);
			gamePane.getChildren().add(brownMeteors[i]);
		}

		greyMeteors = new ImageView[3];
		for (int i = 0; i < greyMeteors.length; i++) {
			// auf selbe Größe wie Player setzen
			greyMeteors[i] = new ImageView(METEOR_GREY_IMG);

			// auf selbe Größe wie Player setzen
			greyMeteors[i].setFitHeight(ENTITIES_SIZE);
			greyMeteors[i].setFitWidth(ENTITIES_SIZE);
			//
			setNewElementPosition(greyMeteors[i]);
			gamePane.getChildren().add(greyMeteors[i]);
		}
	}

	private void setNewElementPosition(ImageView img) {
		img.setLayoutX(RAND.nextInt(GAME_WIDTH - ENTITIES_SIZE));// 370
		// zahl beliebig
		img.setLayoutY(-RAND.nextInt(400));
		// img.setLayoutY(0);
	}

	private void moveGameElements() {
		for (int i = 0; i < brownMeteors.length; i++) {
			brownMeteors[i].setLayoutY(brownMeteors[i].getLayoutY() + 4); // speed //4
			brownMeteors[i].setRotate(brownMeteors[i].getRotate() + 4);
			// System.out.println(brownMeteors[i].getLayoutX() + " " +
			// brownMeteors[i].getLayoutY());
		}

		for (int i = 0; i < greyMeteors.length; i++) {
			greyMeteors[i].setLayoutY(greyMeteors[i].getLayoutY() + 3); // speed
			greyMeteors[i].setRotate(greyMeteors[i].getRotate() - 4);
		}
	}

	private void checkIfElemetsAreUnderShip() {
		for (int i = 0; i < brownMeteors.length; i++) {
			// wenn obj unter fensterrand
			if (brownMeteors[i].getLayoutY() > GAME_HEIGHT) { // ship.getLayoutY()
				setNewElementPosition(brownMeteors[i]);
			}
		}

		for (int i = 0; i < greyMeteors.length; i++) {
			// wenn obj unter fensterrand
			if (greyMeteors[i].getLayoutY() > GAME_HEIGHT) {
				setNewElementPosition(greyMeteors[i]);
			}
		}
	}

	// Player(Ship)
	///////////////////////////
	private void createPlayer() {

		player = new ImageView("view/resources/playerShip1_orange.png");

		player.setFitHeight(ENTITIES_SIZE);
		player.setFitWidth(ENTITIES_SIZE);

		player.setLayoutX(GAME_WIDTH / 2 - ENTITIES_SIZE / 2);
		player.setLayoutY(GAME_HEIGHT - 90);
		// player.setFocusTraversable(true);
		gamePane.getChildren().add(player);
	}

	private void moveShip() {
		// nach links
		if (isLeftKeyPressed && !isRigtKeyPressed) {
			if (angle > -30) { // borders
				angle -= 5;
			}
			player.setRotate(angle);
			// if(player.getLayoutX() > - GAME_WIDTH / 2 + ENTITIES_SIZE / 2) {
			if (player.getLayoutX() > 0) {
				player.setLayoutX(player.getLayoutX() - 3);
				// moveLaserShots(player.getLayoutX(), player.getLayoutY(), -3);
				// System.out.println(player.getLayoutX() + " " + player.getLayoutY());
			}
		}

		// nach rechts
		if (isRigtKeyPressed && !isLeftKeyPressed) {
			if (angle < 30) {
				angle += 5;
			}
			player.setRotate(angle);
			// if(player.getLayoutX() < GAME_WIDTH / 2 - ENTITIES_SIZE / 2) {
			if (player.getLayoutX() < GAME_WIDTH - ENTITIES_SIZE) {
				player.setLayoutX(player.getLayoutX() + 3);
				// moveLaserShots(player.getLayoutX(), player.getLayoutY(), 3);
				// System.out.println(player.getLayoutX() + " " + player.getLayoutY());
			}
		}

		// pos beibehalten
		if (!isLeftKeyPressed && !isRigtKeyPressed) {
			if (angle < 0) {
				angle += 5;
			} else if (angle > 0) {
				angle -= 5;
			}
			player.setRotate(angle);
		}

		// pos beibehalten
		if (isRigtKeyPressed && isLeftKeyPressed) {
			if (angle < 0) {
				angle += 5;
			} else if (angle > 0) {
				angle -= 5;
			}
			player.setRotate(angle);
		}
	}

	// Lasershot 13x37 (width x height)
	///////////////////////////
	private void shootLaser() {
		for (int i = 0; i < lasers.size(); i++) {
			if (lasers.get(i).getLayoutY() > -lasers.get(i).getBoundsInParent().getHeight()) { // -37 wenn unterhalb des
																								// windows
				lasers.get(i).relocate(lasers.get(i).getLayoutX(), lasers.get(i).getLayoutY() - 3); // um 3 pixel nach
																									// oben bewegen
			} else { // wenn oberhalb des windows

				gamePane.getChildren().remove(lasers.get(i));
				lasers.remove(i);
				System.out.println(lasers.size());
			}
		}
	}

	// Background
	/////////////////////////////////
	public void createBackground() {
		// checks current gamemode
		String imageSource = "";
		if (feedback.getmode() == 0) {
			imageSource = BACKGROUND_IMG;
		} else if (feedback.getmode() == 1) {
			imageSource = GREEN_BACKGROUND_IMG;
		} else if (feedback.getmode() == 2) {
			imageSource = RED_BACKGROUND_IMG;
		}

		// only for first initialization
		if (!feedback.getChanging()) {
			// GridPane, da Background zu klein Screen-size ist
			// Überlagerung des Backgrounds als Gittereinheit
			gridPane1 = new GridPane();
			// when moves 2nd must cover the empty spot
			gridPane2 = new GridPane();
		}
		// only during changes by biofeedback
		if (feedback.getChanging()) {
			gridPane1.getChildren().clear();
			gridPane2.getChildren().clear();
		}

		for (int i = 0; i < 16; i++) {
			ImageView backgroundImage1 = new ImageView(imageSource);
			// size von bild(256x256 p), size anpassen dass gridpane auf vier geteilt wird
			backgroundImage1.setFitHeight(GAME_WIDTH / 4);
			backgroundImage1.setFitWidth(GAME_WIDTH / 4);

			ImageView backgroundImage2 = new ImageView(imageSource);
			backgroundImage2.setFitHeight(GAME_WIDTH / 4);
			backgroundImage2.setFitWidth(GAME_WIDTH / 4);

			// set columns and rows

			GridPane.setConstraints(backgroundImage1, i % 4, i / 4); // 4x4 grid
			GridPane.setConstraints(backgroundImage2, i % 4, i / 4); // 4x4 grid
			gridPane1.getChildren().add(backgroundImage1);
			gridPane2.getChildren().add(backgroundImage2);
		}

		// only for first initialization
		if (!feedback.getChanging()) {
			gridPane2.setLayoutY(-GAME_HEIGHT);
			gamePane.getChildren().addAll(gridPane1, gridPane2);
		}
	}

	private void moveBackground() {
		if (feedback.getmode() == 0) {
			gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.5);
			gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.5);
		} else if (feedback.getmode() == 1) {
			gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.2);
			gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.2);
		} else if (feedback.getmode() == 2) {
			gridPane1.setLayoutY(gridPane1.getLayoutY() + 2);
			gridPane2.setLayoutY(gridPane2.getLayoutY() + 2);
		}

		if (gridPane1.getLayoutY() >= GAME_HEIGHT) {
			gridPane1.setLayoutY(-GAME_HEIGHT);
		}

		if (gridPane2.getLayoutY() >= GAME_HEIGHT) {
			gridPane2.setLayoutY(-GAME_HEIGHT);
		}
	}

	private double distance(double x1, double y1, double x2, double y2) {
		return (Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)));
	}

	private void collision() {
		// player vs brown Meteor
		for (int i = 0; i < brownMeteors.length; i++) {
			if (distance(brownMeteors[i].getLayoutX() + ENTITIES_SIZE / 2,
					brownMeteors[i].getLayoutY() - ENTITIES_SIZE / 2, player.getLayoutX() + ENTITIES_SIZE / 2,
					player.getLayoutY() - ENTITIES_SIZE / 2) <= ENTITIES_SIZE) {
				showScoreDecrease(brownMeteors[i].getLayoutX());
				setNewElementPosition(brownMeteors[i]);

				// Update score -
				sc.score -= 3 * sc.collisionCounter;
				sc.collisionCounter++;
			}
		}
		// player vs grey Meteor
		for (int i = 0; i < greyMeteors.length; i++) {
			if (distance(greyMeteors[i].getLayoutX() + ENTITIES_SIZE / 2,
					greyMeteors[i].getLayoutY() - ENTITIES_SIZE / 2, player.getLayoutX() + ENTITIES_SIZE / 2,
					player.getLayoutY() - ENTITIES_SIZE / 2) <= ENTITIES_SIZE) {
				showScoreDecrease(greyMeteors[i].getLayoutX());
				setNewElementPosition(greyMeteors[i]);

				// Update score -
				sc.score -= 3 * sc.collisionCounter;
				sc.collisionCounter++;
			}
		}

		// laser vs brown Meteor
		for (int i = 0; i < lasers.size(); i++) {
			for (int j = 0; j < brownMeteors.length; j++) { // 0 - 2 -> 3elem
				if (lasers.get(i).getBoundsInParent().intersects(brownMeteors[j].getBoundsInParent())) { // bounds of a
																											// node in
																											// it's
																											// parent
																											// coordinates
					setNewElementPosition(brownMeteors[j]);
					gamePane.getChildren().remove(lasers.get(i));
					lasers.remove(i);
					System.out.println(lasers.size());

					// Update score +
					sc.score += 1;
					break; // kein fehler mehr durch index out of bounds verletzung
				}
			}
		}

		for (int i = 0; i < lasers.size(); i++) {
			for (int j = 0; j < greyMeteors.length; j++) { // 0 - 2 -> 3elem
				if (lasers.get(i).getBoundsInParent().intersects(greyMeteors[j].getBoundsInParent())) { // bounds of a
																										// node in it's
																										// parent
																										// coordinates
					setNewElementPosition(greyMeteors[j]);
					gamePane.getChildren().remove(lasers.get(i));
					lasers.remove(i);
					System.out.println(lasers.size());

					// Update score +
					sc.score += 1;
					break; // kein fehler mehr durch index out of bounds verletzung
				}
			}

		}
	}

	// method to update the score label
	private void updateScoreLabel() {
		String textToSet = "Score: ";
		scoreLabel.setText(textToSet + sc.score);
	}

	// method to update the heart label
	public void updateHeartLabel() {
		if (ini.getHRSUSage() == 1) {
			String textForHL = " ";
			textForHL = " " + sensor.getHeartR(sp)/* sensor.getHeartRate() */;
			heartLabel.setText(textForHL);
		} else {
			int zahl = sensor.getHeartRate();
			String textHL = " " + zahl;
			heartLabel.setText(textHL);
		}
	}

	// method to get the system time and date (for saving)
	public String getSysTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String t = sdf.format(date);
		return t;
	}

	// changes Colors depending on gamemode change
	public void changeColors(int mode) {
		if (mode == 0) {
			Image newPlayer = new Image("view/resources/playerShip1_orange.png");
			player.setImage(newPlayer);
			Image newMeteor1 = new Image("view/resources/meteorBrown_med1.png");
			for (int i = 0; i < brownMeteors.length; i++) {
				brownMeteors[i].setImage(newMeteor1);
			}
			Image newMeteor2 = new Image("view/resources/meteorGrey_med1.png");
			for (int i = 0; i < brownMeteors.length; i++) {
				greyMeteors[i].setImage(newMeteor2);
			}
		} else if (mode == 1) {
			Image newPlayer = new Image("view/resources/playerShip1_green.png");
			player.setImage(newPlayer);
			Image newMeteor1 = new Image("view/resources/meteorLightGreen_med1.png");
			for (int i = 0; i < brownMeteors.length; i++) {
				brownMeteors[i].setImage(newMeteor1);
			}
			Image newMeteor2 = new Image("view/resources/meteorGreen_med1.png");
			for (int i = 0; i < brownMeteors.length; i++) {
				greyMeteors[i].setImage(newMeteor2);
			}
		} else if (mode == 2) {
			Image newPlayer = new Image("view/resources/playerShip1_red.png");
			player.setImage(newPlayer);
			Image newMeteor1 = new Image("view/resources/meteorDarkRed_med1.png");
			for (int i = 0; i < brownMeteors.length; i++) {
				brownMeteors[i].setImage(newMeteor1);
			}
			Image newMeteor2 = new Image("view/resources/meteorLightRed_med1.png");
			for (int i = 0; i < brownMeteors.length; i++) {
				greyMeteors[i].setImage(newMeteor2);
			}
		}

	}

	public void changeText(int m) {
		if (m == 0) {
			scoreLabel.setFont(new Font(20));
			scoreLabel.setEffect(null);
		}
		if (m == 2) {
			scoreLabel.setFont(new Font(25));
			scoreLabel.setEffect(null);
		}
		if (m == 1) {
			scoreLabel.setFont(new Font(20));
			BoxBlur blur = new BoxBlur();
			blur.setIterations(2);
			scoreLabel.setEffect(blur);
		}
	}

	private void showScoreDecrease(double x) {
		if (feedback.getmode() == 1) {
			return;
		}
		for (int i = 0; i < 3; i++) {
			if (scoreDecrease[i] == null) {
				scoreDecrease[i] = new Text("" + (-3 * sc.collisionCounter));
				scoreDecrease[i].setFont(new Font(10));
				scoreDecrease[i].setLayoutX(x);
				scoreDecrease[i].setLayoutY(WINDOW_HEIGHT - 150);
				if (feedback.getmode() == 2) {
					scoreDecrease[i].setFill(Color.RED);
				}
				scoreDecrease[i].setScaleX(3);
				scoreDecrease[i].setScaleY(3);
				gamePane.getChildren().add(scoreDecrease[i]);
				return;
			}
		}
		gamePane.getChildren().remove(scoreDecrease[0]);
		scoreDecrease[0] = null;
		scoreDecrease[0] = new Text("" + (-3 * sc.collisionCounter));
		scoreDecrease[0].setScaleX(3);
		scoreDecrease[0].setScaleY(3);
		if (feedback.getmode() == 2) {
			scoreDecrease[0].setFill(Color.RED);
		}
		gamePane.getChildren().add(scoreDecrease[0]);
		scoreDecrease[0].setLayoutX(x);
		scoreDecrease[0].setLayoutY(WINDOW_HEIGHT - 150);
		scoreDecrease[0].setStyle(LASER_IMG);
		scoreDecrease[0].setFill(Color.RED);
	}

	private void moveScoreDecrease() {
		if (feedback.getmode() == 2) {
			for (int i = 0; i < 3; i++) {
				if (scoreDecrease[i] != null) {
					scoreDecrease[i].setScaleX(scoreDecrease[i].getScaleX() + 0.3);
					scoreDecrease[i].setScaleY(scoreDecrease[i].getScaleY() + 0.3);
					if (scoreDecrease[i].getScaleX() > 20) {
						gamePane.getChildren().remove(scoreDecrease[i]);
						scoreDecrease[i] = null;
					}
				}
			}
		} else if (feedback.getmode() == 0) {
			for (int i = 0; i < 3; i++) {
				if (scoreDecrease[i] != null) {
					scoreDecrease[i].setScaleX(scoreDecrease[i].getScaleX() + 0.02);
					scoreDecrease[i].setScaleY(scoreDecrease[i].getScaleY() + 0.02);
					if (scoreDecrease[i].getScaleX() > 5) {
						gamePane.getChildren().remove(scoreDecrease[i]);
						scoreDecrease[i] = null;
					}
				}
			}
		}
	}

}
