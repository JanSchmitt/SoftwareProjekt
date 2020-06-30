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
	private static final int HEIGHT = 400;
	
	private boolean catchOn = false;
	
	public AnimationTimer gameTimer;
	
	public int score;
	Random RAND;
	
	
	public CatchTheBall(AnchorPane mp) {
		this.minispielPane = mp;
	}
	
	public void start() {
		scene = new Scene(minispielPane, WIDTH, HEIGHT);
		window.setScene(scene);
		window.show();
		
		startGame();
	}
	
	
	public void startGame() {
		createBackground();
		addRect();
		addBall();
		createKeyListener();
		createGameLoop();
		
	}
	private void createKeyListener() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if(e.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = true;
				}
				if(e.getCode() == KeyCode.RIGHT) {
					isRigtKeyPressed = true;
				}
			}
		});
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if(e.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = false;
				}else if(e.getCode() == KeyCode.RIGHT) {
					isRigtKeyPressed = false;
				}
			}
		});
	}
	
	public void moveRight() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if(e.getCode() == KeyCode.RIGHT) {
					isRigtKeyPressed = true;
				}
			}
		});
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if(e.getCode() == KeyCode.RIGHT) {
					isRigtKeyPressed = false;
				}
			}
		});
	}
	
	public void moveLeft() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if(e.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = true;
				}
			}
		});
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if(e.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = false;
				}
			}
		});
	}

	private void createBackground() {
		BackgroundFill myBF = new BackgroundFill(Color.ANTIQUEWHITE, new CornerRadii(1),
		         new Insets(0.0,0.0,0.0,0.0));// or null for the padding
		//then you set to your node or container or layout
		minispielPane.setBackground(new Background(myBF));
	}
	
	public void addBall() {
		ball1 = new ImageView("minispiele/resources/ball_basket2.png");
		ball1.setFitHeight(BALL_SIZE);
		ball1.setFitWidth(BALL_SIZE);
		ball1.setLayoutX((int)(Math.random() * WIDTH));
		ball1.setLayoutY(0);
		
		ball2 = new ImageView("minispiele/resources/ball_basket2.png");
		ball2.setFitHeight(BALL_SIZE);
		ball2.setFitWidth(BALL_SIZE);
		ball2.setLayoutX((int)(Math.random() * WIDTH));
		ball2.setLayoutY(-250);
		
		minispielPane.getChildren().addAll(ball1, ball2);
	}
	
	public void addRect() {
		rect = createRect();
		rect.setLayoutX(WIDTH  /2 - BALL_SIZE);
		rect.setLayoutY(HEIGHT - 30);

		minispielPane.getChildren().add(rect);
	}
	
	private void createGameLoop() {
		gameTimer=new AnimationTimer() {
			@Override
			public void handle(long now) {				
				moveBall();
				moveRect();
				checkIfBehind();
				collision();
			}
		};
		gameTimer.start();	
	}
		
	public void moveRect() {
		//nach links
		if(isLeftKeyPressed && !isRigtKeyPressed) {
			if(rect.getLayoutX() > 0) {
				rect.setLayoutX(rect.getLayoutX() -2);
			}
		}
		
		//nach rechts
		if(isRigtKeyPressed && !isLeftKeyPressed) {
			if(rect.getLayoutX() < WIDTH - BALL_SIZE *2) {
				rect.setLayoutX(rect.getLayoutX() +2);
			}
		}
	}
	
	public void moveBall() {
		ball1.setLayoutY(ball1.getLayoutY() + 2.2);
		if(ball1.getLayoutX() >= 0) {
			ball1.setLayoutX(ball1.getLayoutX() - ((int)(Math.random() * 10) -1 ) );
		}
		if(ball1.getLayoutX() < WIDTH - BALL_SIZE) {
			ball1.setLayoutX(ball1.getLayoutX() + ((int)(Math.random() * 10) -1 ) );
		}
		
		ball2.setLayoutY(ball2.getLayoutY() + 2.2);
		if(ball2.getLayoutX() >= 0) {
			ball2.setLayoutX(ball2.getLayoutX() - ((int)(Math.random() * 5) -1 ) );
		}
		if(ball2.getLayoutX() < WIDTH - BALL_SIZE) {
			ball2.setLayoutX(ball2.getLayoutX() + ((int)(Math.random() * 5) -1 ) );
		}
	}		
		
	public Rectangle createRect() {
		Rectangle rf = new Rectangle();
		rf.setFill(Color.BLUE);
		rf.setHeight(10);
		rf.setWidth(BALL_SIZE * 2);
		
		return rf;
	}
	
	public void checkIfBehind() {
		if(ball1.getLayoutY() > HEIGHT) {
			score -= 10;
			setNewElementPosition(ball1);
		}
		if(ball2.getLayoutY() > HEIGHT) {
			score -= 10;
			setNewElementPosition(ball2);
		}
	}
	
	private void setNewElementPosition(ImageView img) {
		img.setLayoutX((int)(Math.random() * WIDTH - BALL_SIZE));//370
		//zahl beliebig
		img.setLayoutY(-200);
		
	}
	
	private double distance(double x1, double y1, double x2, double y2) {
		return (Math.sqrt(Math.pow( (x1-x2), 2) + Math.pow( (y1-y2), 2)));
	}
	
	private void collision() {
		if(ball1.getBoundsInParent().intersects(rect.getBoundsInParent())) {	// bounds of a node in it's parent coordinates
			setNewElementPosition(ball1);
			score += 2;
		}
		if(ball2.getBoundsInParent().intersects(rect.getBoundsInParent())) {	// bounds of a node in it's parent coordinates
			setNewElementPosition(ball2);
			score += 2;
		}
	}
	
	public void stop() {
		minispielPane.getChildren().remove(0);
	}
	
}

