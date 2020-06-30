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

public class PongGame{
	//static variables to define size
	private static final int SCENE_WIDTH = 700;
	private static final int SCENE_HEIGHT = 500;
	private static final int PLAYER_WIDTH = 150;
	private static final int PLAYER_HEIGHT = 20;
	private static final int BALL_SIZE = 50;
	
	//graphical elements
	private AnchorPane layout=new AnchorPane();
	private AnchorPane minispielPane;
	private Stage stage;
	private Scene scene=new Scene(layout, SCENE_WIDTH, SCENE_HEIGHT);
	private ImageView player;
	private ImageView ball;
	
	//moving objects
	private Player p=new Player();
	private Ball b=new Ball();
	
	//animation timer
	private AnimationTimer gameTimer;
	
	//score variable
	private int score=0;
	
	//constructor that recieves a stage
	public PongGame(AnchorPane mp) {
		this.minispielPane = mp;
		scene = new Scene(minispielPane, SCENE_WIDTH, SCENE_HEIGHT);
		stage.setScene(scene);		
	}
	
	//starts the game
	public void run() {
		/*scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
		});		*/
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
		layout.getChildren().addAll(player, ball);
		createGameLoop();
		stage.show();
	}
	
	public void movePlayerLeft() {
		p.moveLeft();
	}
	
	public void movePlayerRight() {
		p.moveRight();
	}
	
	//controls repeating actions during game
	private void createGameLoop() {
		gameTimer=new AnimationTimer() {
			@Override
			public void handle(long now) {				
				moveBall();
				movePlayer();
				collision();
			}
		};
		gameTimer.start();	
	}
	
	//moves ball on screen
	private void moveBall() {
		ball.setLayoutX(ball.getLayoutX()+b.getVX());
		ball.setLayoutY(ball.getLayoutY()+b.getVY());
	}
	
	//moves player on screen
	private void movePlayer() {
		if (p.getMovement()==1&&player.getLayoutX()>=5) {
			player.setLayoutX(player.getLayoutX()-5);
		}else if(p.getMovement()==2&&player.getLayoutX()<=SCENE_WIDTH-PLAYER_WIDTH-5) {
			player.setLayoutX(player.getLayoutX()+5);
		}
	}
	
	//checks collision of ball with edge or player
	private void collision() {
		if(ball.getLayoutX()+BALL_SIZE>=SCENE_WIDTH-b.getSpeed()) {
			b.moveLeft();
		}
		if(ball.getLayoutX()<=0+b.getSpeed()) {
			b.moveRight();
		}
		if(ball.getLayoutY()<=0+b.getSpeed()) {
			b.moveDown();
		}
		if(ball.getLayoutY()+BALL_SIZE-SCENE_HEIGHT+PLAYER_HEIGHT<=b.getSpeed()
		&&ball.getLayoutY()+BALL_SIZE-SCENE_HEIGHT+PLAYER_HEIGHT>=0
		&&ball.getLayoutX()+BALL_SIZE>player.getLayoutX()
		&&ball.getLayoutX()<player.getLayoutX()+PLAYER_WIDTH) {
			b.moveUp();
			score++;
			
		}
		if(ball.getLayoutY()>=SCENE_HEIGHT) {
			b.stop();
			System.out.println("Score: "+score);
			stage.close();
		}
	}
	
	public void stop() {
		b.stop();
		minispielPane.getChildren().remove(0);
		stage.close();
	}
}
