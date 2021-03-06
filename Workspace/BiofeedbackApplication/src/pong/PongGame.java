package pong;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;

//import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PongGame {
	// static variables to define size
	private static final int SCENE_WIDTH = 400;
	private static final int SCENE_HEIGHT = 430;
	private static final int PLAYER_WIDTH = 100;
	private static final int PLAYER_HEIGHT = 20;
	private static final int BALL_SIZE = 50;

	// graphical elements
	private AnchorPane layout = new AnchorPane();
	private AnchorPane minispielPane;
	private Stage stage;
	private Scene scene = new Scene(layout, SCENE_WIDTH, SCENE_HEIGHT);
	private ImageView player;
	private ImageView ball;

	// moving objects
	private Player p = new Player();
	private Ball b = new Ball();

	// animation timer
	private AnimationTimer gameTimer;

	// score variable
	private int score = 0;
	private int counter = 0;

	// constructor that recieves a stage
	public PongGame(AnchorPane mp) {
		this.minispielPane = mp;
		/*scene = new Scene(minispielPane, SCENE_WIDTH, SCENE_HEIGHT);
		stage.setScene(scene);*/
	}

	// starts the game and initializes variables
	public void run() {
		/*
		 * scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
		 * 
		 * @Override public void handle(KeyEvent e) { if(e.getCode()==KeyCode.LEFT) {
		 * p.moveLeft(); }else if(e.getCode()==KeyCode.RIGHT) { p.moveRight(); } } });
		 * scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
		 * 
		 * @Override public void handle(KeyEvent e) {
		 * if(e.getCode()==KeyCode.LEFT||e.getCode()==KeyCode.RIGHT) { p.stop(); } } });
		 */
		player = new ImageView("minispiele/resources/player0.png");
		player.setFitHeight(PLAYER_HEIGHT);
		player.setFitWidth(PLAYER_WIDTH);
		player.setLayoutX(SCENE_WIDTH / 2 - PLAYER_WIDTH / 2);
		player.setLayoutY(SCENE_HEIGHT - PLAYER_HEIGHT);
		ball = new ImageView("minispiele/resources/ball0.png");
		ball.setFitHeight(BALL_SIZE);
		ball.setFitWidth(BALL_SIZE);
		ball.setLayoutX(0);
		ball.setLayoutY(0);
		minispielPane.getChildren().addAll(player, ball);
		createGameLoop();
		//stage.show();
	}

	//calls moveLeft method in Player
	public void movePlayerLeft() {
		p.moveLeft();
	}
	
	//calls moveRight method 
	public void movePlayerRight() {
		p.moveRight();
	}

	// controls repeating actions during game
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				moveBall();
				movePlayer();
				collision();
			}
		};
		gameTimer.start();
	}

	// moves ball on screen
	private void moveBall() {
		ball.setLayoutX(ball.getLayoutX() + b.getVX());
		ball.setLayoutY(ball.getLayoutY() + b.getVY());
	}

	// moves player on screen
	private void movePlayer() {
		if (p.getMovement() == 1 && player.getLayoutX() >= 5) {
			player.setLayoutX(player.getLayoutX() - 5);
		} else if (p.getMovement() == 2 && player.getLayoutX() <= SCENE_WIDTH - PLAYER_WIDTH - 5) {
			player.setLayoutX(player.getLayoutX() + 5);
		}
	}

	// checks collision of ball with edge or player
	private void collision() {
		//ball hits right edge
		if (ball.getLayoutX() + BALL_SIZE >= SCENE_WIDTH - b.getSpeed()) {
			b.moveLeft();
		}
		//ball hits left edge
		if (ball.getLayoutX() <= 0 + b.getSpeed()) {
			b.moveRight();
		}
		//ball hits upper edge
		if (ball.getLayoutY() <= 0 + b.getSpeed()) {
			b.moveDown();
		}
		//ball hits player
		if (ball.getLayoutY() + BALL_SIZE - SCENE_HEIGHT + PLAYER_HEIGHT <= b.getSpeed()
				&& ball.getLayoutY() + BALL_SIZE - SCENE_HEIGHT + PLAYER_HEIGHT >= 0
				&& ball.getLayoutX() + BALL_SIZE > player.getLayoutX()
				&& ball.getLayoutX() < player.getLayoutX() + PLAYER_WIDTH) {
			b.moveUp();
			score = score + 50;

		}
		//ball falls out of the window 
		if (ball.getLayoutY() >= SCENE_HEIGHT) {
			counter += 1;
			if (counter == 10) {
				 //b.stop();
				 System.out.println("Score: "+score);
				 //stage.close();
				 stop();
			} else {
				score = score - 200;
				/*minispielPane.getChildren().remove(ball);
				ball = new ImageView("minispiele/resources/ball0.png");
				ball.setFitHeight(BALL_SIZE);
				ball.setFitWidth(BALL_SIZE);*/
				ball.setLayoutX(0);
				ball.setLayoutY(0);
				//minispielPane.getChildren().addAll(ball);
			}
		}
	}

	//stops game
	public void stop() {
		b.stop();
		gameTimer.stop();
		minispielPane.getChildren().clear();
		//stage.close();
	}

	//returns score
	public int getPoints() {
		return score;
	}
	
	// starts the game in test mode and initializes variables
	public void testRun() {
		//handles keyboard input
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if(e.getCode()==KeyCode.LEFT) {
					p.moveLeft();
				}else if(e.getCode()==KeyCode.RIGHT) {
					p.moveRight();
				}
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if(e.getCode()==KeyCode.LEFT||e.getCode()==KeyCode.RIGHT) {
					p.stop();
				}
			}
		});		
		player=new ImageView("minispiele/resources/player0.png");
		player.setFitHeight(PLAYER_HEIGHT);
		player.setFitWidth(PLAYER_WIDTH);
		player.setLayoutX(SCENE_WIDTH/2-PLAYER_WIDTH/2);
		player.setLayoutY(SCENE_HEIGHT-PLAYER_HEIGHT);
		ball=new ImageView("minispiele/resources/ball0.png");
		ball.setFitHeight(BALL_SIZE);
		ball.setFitWidth(BALL_SIZE);
		ball.setLayoutX(0);
		ball.setLayoutY(0);
		minispielPane.getChildren().addAll(player, ball);
		createTestGameLoop();
		//stage.show();
	}
	
	// controls repeating actions during test mode
	private void createTestGameLoop() {
		gameTimer=new AnimationTimer() {
			@Override
			public void handle(long now) {				
				moveBall();
				movePlayer();
				testCollision();
			}
		};
		gameTimer.start();	
	}
	
	// checks collision of ball with edge or player during test mode, ends test at score 3
	private void testCollision() {
		//ball hits right edge
		if(ball.getLayoutX()+BALL_SIZE>=SCENE_WIDTH-b.getSpeed()) {
			b.moveLeft();
		}
		//ball hits left edge
		if(ball.getLayoutX()<=0+b.getSpeed()) {
			b.moveRight();
		}
		//ball hits upper edge
		if(ball.getLayoutY()<=0+b.getSpeed()) {
			b.moveDown();
		}
		//ball hits player
		if(ball.getLayoutY()+BALL_SIZE-SCENE_HEIGHT+PLAYER_HEIGHT<=b.getSpeed()
		&&ball.getLayoutY()+BALL_SIZE-SCENE_HEIGHT+PLAYER_HEIGHT>=0
		&&ball.getLayoutX()+BALL_SIZE>player.getLayoutX()
		&&ball.getLayoutX()<player.getLayoutX()+PLAYER_WIDTH) {
			if(score<3) {
				b.moveUp();
				score++;
			}else {
				stop();
				//b.stop();
				//stage.close();
			}
		}
		//ball falls out of the window 
		if(ball.getLayoutY()>=SCENE_HEIGHT) {
			//respawns ball if score is lower than 3
			if(score<3){
				ball.setLayoutX(0);
				ball.setLayoutY(0);
			}else {
				stop();
				//b.stop();
				//stage.close();
			}
			
		}
	}
}
