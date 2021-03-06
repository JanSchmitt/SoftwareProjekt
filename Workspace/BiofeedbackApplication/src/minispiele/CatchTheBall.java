package minispiele;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CatchTheBall {

	ImageView ball1, ball2;
	private static final int BALL_SIZE = 30;
	private AnchorPane minispielPane;
	Scene scene;
	Stage window;
	Node rect;

	int locX = 0;
	int locY = 0;

	private boolean isLeftKeyPressed;
	private boolean isRigtKeyPressed;

	private static final int WIDTH = 400;
	private static final int HEIGHT = 430;

	private boolean catchOn = false;

	// var f�r testfkt
	private boolean test = false;
	private int testhit = 0;

	public AnimationTimer gameTimer;

	public int score;
	Random RAND;

	// constructor to know which pane is used for this minigame
	public CatchTheBall(AnchorPane mp) {
		this.minispielPane = mp;
	}

	public void start() {
		// scene = new Scene(minispielPane, WIDTH, HEIGHT);
		// window.setScene(scene);
		// window.show();

		/*
		 * scene = new Scene(minispielPane, WIDTH, HEIGHT); window.setScene(scene);
		 * window.show();
		 */

		startGame();
	}

	public void startGame() { // initialization
		createBackground();
		addRect();
		addBall();
		// createKeyListener();
		createGameLoop();

	}

	/*
	 * private void createKeyListener() { scene.setOnKeyPressed(new
	 * EventHandler<KeyEvent>() {
	 * 
	 * @Override public void handle(KeyEvent e) { if(e.getCode() == KeyCode.LEFT) {
	 * isLeftKeyPressed = true; } if(e.getCode() == KeyCode.RIGHT) {
	 * isRigtKeyPressed = true; } } });
	 * 
	 * scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
	 * 
	 * @Override public void handle(KeyEvent e) { if(e.getCode() == KeyCode.LEFT) {
	 * isLeftKeyPressed = false; }else if(e.getCode() == KeyCode.RIGHT) {
	 * isRigtKeyPressed = false; } } }); }
	 */

	// createKeyListener();

	/*
	 * private void createKeyListener() { // key event handler for the left and
	 * right key scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	 * 
	 * @Override public void handle(KeyEvent e) { if(e.getCode() == KeyCode.LEFT) {
	 * isLeftKeyPressed = true; } if(e.getCode() == KeyCode.RIGHT) {
	 * isRigtKeyPressed = true; } } });
	 * 
	 * scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
	 * 
	 * @Override public void handle(KeyEvent e) { if(e.getCode() == KeyCode.LEFT) {
	 * isLeftKeyPressed = false; }else if(e.getCode() == KeyCode.RIGHT) {
	 * isRigtKeyPressed = false; } } }); }
	 */

	public void moveRight() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.RIGHT) {
					isRigtKeyPressed = true;
				}
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.RIGHT) {
					isRigtKeyPressed = false;
				}
			}
		});
	}

	public void moveLeft() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = true;
				}
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = false;
				}
			}
		});
	}

	public void moveRectLeft() {
		if (rect.getLayoutX() > 0) {
			rect.setLayoutX(rect.getLayoutX() - 25);
		}
	}

	public void moveRectRight() {
		if (rect.getLayoutX() < WIDTH - BALL_SIZE * 2) {
			rect.setLayoutX(rect.getLayoutX() + 25);
		}
	}

	private void createBackground() {
		BackgroundFill myBF = new BackgroundFill(Color.ANTIQUEWHITE, new CornerRadii(1),
				new Insets(0.0, 0.0, 0.0, 0.0));// fill the background to a preset color
		// then you set to your node or container or layout
		minispielPane.setBackground(new Background(myBF));
	}

	public void addBall() { // adds the two balls and set it to a start location
		ball1 = new ImageView("minispiele/resources/ball_basket2.png");
		ball1.setFitHeight(BALL_SIZE);
		ball1.setFitWidth(BALL_SIZE);
		ball1.setLayoutX((int) (Math.random() * WIDTH));
		ball1.setLayoutY(0);

		ball2 = new ImageView("minispiele/resources/ball_basket2.png");
		ball2.setFitHeight(BALL_SIZE);
		ball2.setFitWidth(BALL_SIZE);
		ball2.setLayoutX((int) (Math.random() * WIDTH));
		ball2.setLayoutY(-250);

		minispielPane.getChildren().addAll(ball1, ball2);
	}

	public void addRect() { // = player
		rect = createRect(); // calls createRect()-function to create a JavaFX Rectangle object
		rect.setLayoutX(WIDTH / 2 - BALL_SIZE);
		rect.setLayoutY(HEIGHT - 30);

		minispielPane.getChildren().add(rect);
	}

	private void createGameLoop() {
		gameTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				moveBall();
				moveRect();
				checkIfBehind();
				collision();
				
				if(test) {
					if(testhit >= 3) gameTimer.stop();
					// Hier soll die Testfunktion verlassen werden
					if(testhit >= 3) 
						gameTimer.stop();
					// Hier soll die Testfunktion verlassen werden
					test = false;
				}
			}
		};
		gameTimer.start();
		
		
	}

	public void moveRect() {
		//nach links
		if(isLeftKeyPressed && !isRigtKeyPressed) {
			if(rect.getLayoutX() > 0) {
				rect.setLayoutX(rect.getLayoutX() -4);
			}
		}
		
		//nach rechts
		if(isRigtKeyPressed && !isLeftKeyPressed) {
			if(rect.getLayoutX() < WIDTH - BALL_SIZE *2) {
				rect.setLayoutX(rect.getLayoutX() +4);
			}
		}
	}


	public void moveBall() {
		ball1.setLayoutY(ball1.getLayoutY() + 2.2);

		// ball soll sich im zick-zack-bewegen
		// random x bewegung
		if (ball1.getLayoutX() >= 0) {
			ball1.setLayoutX(ball1.getLayoutX() - ((int) (Math.random() * 10) - 1)); // ball soll sich im
																						// zick-zack-bewegen
		}
		if (ball1.getLayoutX() < WIDTH - BALL_SIZE) {
			ball1.setLayoutX(ball1.getLayoutX() + ((int) (Math.random() * 10) - 1));// ball soll sich im
																					// zick-zack-bewegen
		}

		ball2.setLayoutY(ball2.getLayoutY() + 2.2);
		if (ball2.getLayoutX() >= 0) {
			ball2.setLayoutX(ball2.getLayoutX() - ((int) (Math.random() * 5) - 1));
		}
		if (ball2.getLayoutX() < WIDTH - BALL_SIZE) {
			ball2.setLayoutX(ball2.getLayoutX() + ((int) (Math.random() * 5) - 1));
		}
	}

	public Rectangle createRect() { // creates the rectangular player
		Rectangle rf = new Rectangle();
		rf.setFill(Color.BLUE);
		rf.setHeight(10);
		rf.setWidth(BALL_SIZE * 2);
		return rf;
	}

	public void checkIfBehind() { // if balls are under the pane
		if (ball1.getLayoutY() > HEIGHT) {
			score -= 50;
			setNewElementPosition(ball1);
		}
		if (ball2.getLayoutY() > HEIGHT) {
			score -= 50;
			setNewElementPosition(ball2);
		}
	}

	private void setNewElementPosition(ImageView img) {
		img.setLayoutX((int) (Math.random() * WIDTH - BALL_SIZE));// 370
		// random number
		img.setLayoutY(-200);

	}

	private double distance(double x1, double y1, double x2, double y2) { // calculates the distance bewteen 2 objects
		return (Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)));
	}

	private void collision() {
		if (ball1.getBoundsInParent().intersects(rect.getBoundsInParent())) { // bounds of a node in it's parent
																				// coordinates for ball1 and rect
			setNewElementPosition(ball1);
			score += 50;

			if (test) {
				testhit++;
			}
		}
		if (ball2.getBoundsInParent().intersects(rect.getBoundsInParent())) { // bounds of a node in it's parent
																				// coordinates for ball2 and rect
			setNewElementPosition(ball2);
			score += 50;

			if (test) {
				testhit++;
			}
		}
	}

	public void stop() { // stops the minigame by deleting everything which is on the "minispielPane"
		minispielPane.getChildren().clear();
	}

	public int getPoints() {
		return score;
	}

	// Funktion f�r testRun
	public void testRun() {
		test = true;
		start();
	}
}
