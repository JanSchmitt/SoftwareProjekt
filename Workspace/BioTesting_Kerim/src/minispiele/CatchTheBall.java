package minispiele;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CatchTheBall extends Application {

	

	ImageView ball;
	private AnchorPane minispielPane;
	Scene scene;
	Stage window;
	int locX = 0;
	int locY = 0;
	
	public AnimationTimer gameTimer;
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryScene) throws Exception {
		window = primaryScene;
		minispielPane = new AnchorPane();
		scene = new Scene(minispielPane, 300, 300);
	}
	/*
	public CatchTheBall(AnchorPane mP){
		this.minispielPane = mP;
	}
	
	public void start() {
		
		addPlayer();
		createGameLoop();
		/*
		minispielPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
		
			@Override
			public void handle(KeyEvent e) {
				if(e.getCode() == KeyCode.LEFT) {
					locX -= 2;
					paintNewScene();
				}
				if(e.getCode() == KeyCode.RIGHT) {
					locX += 2;
					paintNewScene();
				}
			}
			
		});
		
	}
	
	private void createGameLoop() {
		gameTimer=new AnimationTimer() {
			@Override
			public void handle(long now) {				
				move();
			}
		};
		gameTimer.start();	
	}
	public void addPlayer() {
		ball = new ImageView("minispiele/resources/ball_basket2.png");
		
		ball.setFitHeight(30);
		ball.setFitWidth(30);
		
		ball.setLayoutX(0);
		ball.setLayoutY(-20);
		
		minispielPane.getChildren().add(ball);
	}
	
	
	public void move() {
		ball.setLayoutY(ball.getLayoutY() +2);
	}
	public void stop() {
		minispielPane.getChildren().remove(0);
	}
	
	public void moveLeft() {
		ball.setLayoutX(ball.getLayoutX() - 2);
	}
	
	public void moveRight() {
		ball.setLayoutX(ball.getLayoutX() +2);
	}

	*/
}

