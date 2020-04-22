package view;


import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class GameViewManager {
	
	//constants
	//{
	//Window size
	private static final int GAME_WIDTH = 804;
	private static final int GAME_HEIGHT = 750;
	//Entities size
	private static final int ENTITIES_SIZE = 60;
	//Collision Detection Radius
	private final static int PLAYER_RADIUS = 30;
	private final static int METEOR_RADIUS = 30;
	//}
	
	//Ebenen
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	private Stage menuStage;
	
	//Gridpane for Background IMG
	private GridPane gridPane1;
	private GridPane gridPane2;
	
	private boolean isLeftKeyPressed;
	private boolean isRigtKeyPressed;
	private int angle;
	
	private AnimationTimer gameTimer;
	
	Random RAND;
	
	//IMG URL
	public final static String BACKGROUND_IMG = "view/resources/blue.png";
	private final static String METEOR_GREY_IMG = "view/resources/meteorGrey_med1.png";
	private final static String METEOR_BROWN_IMG = "view/resources/meteorBrown_med1.png";
	
	//IMG Objects to create
	private ImageView player;
	private ImageView[] brownMeteors;
	private ImageView[] greyMeteors;
	
	
	public GameViewManager() {
		initializeStage();
		createKeyListener();
		RAND = new Random();
	}
	
	private void createKeyListener() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if(e.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = true;
				}else if(e.getCode() == KeyCode.RIGHT) {
					isRigtKeyPressed = true;
				}
			}
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

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

	private void initializeStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
		gameStage.setResizable(false);
	}
	
	public void createNewGame(Stage menuStage) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		createBackground();
		createPlayer();
		createGameElements();
		
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
				checkIfElemetsAreUnderShip();
				collision();
				moveShip();
			}
		};
		gameTimer.start();
	}
	
	
	//Meteors(Enemies)
	////////////////////////////////
	private void createGameElements() {
		//jeweils max 3 meteors generaten
		brownMeteors = new ImageView[3];
		for(int i = 0;i<brownMeteors.length; i++) {
			//erzeugen der ImageView Objekte
			brownMeteors[i] = new ImageView(METEOR_BROWN_IMG);
			
			//auf selbe Größe wie Player setzen = 60x60
			brownMeteors[i].setFitHeight(ENTITIES_SIZE);
			brownMeteors[i].setFitWidth(ENTITIES_SIZE);
			//
			setNewElementPosition(brownMeteors[i]);
			gamePane.getChildren().add(brownMeteors[i]);
		}
		
		greyMeteors = new ImageView[3];
		for(int i = 0;i<greyMeteors.length; i++) {
			//auf selbe Größe wie Player setzen
			greyMeteors[i] = new ImageView(METEOR_GREY_IMG);
			
			//auf selbe Größe wie Player setzen
			greyMeteors[i].setFitHeight(ENTITIES_SIZE);
			greyMeteors[i].setFitWidth(ENTITIES_SIZE);
			//
			setNewElementPosition(greyMeteors[i]);
			gamePane.getChildren().add(greyMeteors[i]);
		}
	}
	
	private void setNewElementPosition(ImageView img) {
		img.setLayoutX(RAND.nextInt(GAME_WIDTH - ENTITIES_SIZE));//370
		//zahl beliebig
		img.setLayoutY(-RAND.nextInt(400));
		//img.setLayoutY(0);
	}
	
	private void moveGameElements() {
		for(int i = 0; i < brownMeteors.length; i++) {
			brownMeteors[i].setLayoutY(brownMeteors[i].getLayoutY() + 4); //speed
			brownMeteors[i].setRotate(brownMeteors[i].getRotate()+4);
			//System.out.println(brownMeteors[i].getLayoutX() + "     " + brownMeteors[i].getLayoutY());
		}
		
		for(int i = 0; i < greyMeteors.length; i++) {
			greyMeteors[i].setLayoutY(greyMeteors[i].getLayoutY() + 3); //speed 
			greyMeteors[i].setRotate(greyMeteors[i].getRotate()-4);
		}
	}
	
	private void checkIfElemetsAreUnderShip() {
		for(int i = 0; i <  brownMeteors.length; i++) {
			//wenn obj unter fensterrand
			if(brownMeteors[i].getLayoutY() > GAME_HEIGHT) { //ship.getLayoutY()
				setNewElementPosition(brownMeteors[i]);
			}
		}
		
		for(int i = 0; i <  greyMeteors.length; i++) {
			//wenn obj unter fensterrand
			if(greyMeteors[i].getLayoutY() > GAME_HEIGHT) {
				setNewElementPosition(greyMeteors[i]);
			}
		}
	}
	
	//Player(Ship)
	///////////////////////////
	private void createPlayer() {
		
		player = new ImageView("view/resources/playerShip1_orange.png");
		
		player.setFitHeight(ENTITIES_SIZE);
		player.setFitWidth(ENTITIES_SIZE);
		
		player.setX(GAME_WIDTH / 2 - ENTITIES_SIZE / 2); //600/2-49 = 251  -49
		player.setY(GAME_HEIGHT - 90); //-90
		gamePane.getChildren().add(player);
	}
	
	private void moveShip() {
		//nach links
		if(isLeftKeyPressed && !isRigtKeyPressed) {
			if(angle > -30) { //borders
				angle -= 5;
			}
			player.setRotate(angle);
			if(player.getLayoutX() > - GAME_WIDTH / 2 + ENTITIES_SIZE / 2) {
				player.setLayoutX(player.getLayoutX() -3);
				System.out.println(player.getLayoutX() + "     " + player.getLayoutY());
			}
		}
		
		//nach rechts
		if(isRigtKeyPressed && !isLeftKeyPressed) {
			if(angle < 30) {
				angle += 5;
			}
			player.setRotate(angle);
			if(player.getLayoutX() < GAME_WIDTH / 2 - ENTITIES_SIZE / 2) {
				player.setLayoutX(player.getLayoutX() +3);
				System.out.println(player.getLayoutX() + "     " + player.getLayoutY());
			}
		}
		
		//pos beibehalten
		if(!isLeftKeyPressed && !isRigtKeyPressed) {
			if(angle < 0) {
				angle += 5;
			}else if(angle > 0) {
				angle -= 5;
			}
			player.setRotate(angle);
		}
		
		//pos beibehalten
		if(isRigtKeyPressed && isLeftKeyPressed) {
			if(angle < 0) {
				angle += 5;
			}else if(angle > 0) {
				angle -= 5;
			}
			player.setRotate(angle);
		}
		
	}
	
	//Background
	/////////////////////////////////
	private void createBackground() {
		//GridPane, da Background zu klein Screen-size ist
		//Überlagerung des Backgrounds als Gittereinheit
		gridPane1 = new GridPane();
		//when moves 2nd must cover the empty spot
		gridPane2 = new GridPane();
		
		for(int i = 0; i<16;i++) {
			ImageView backgroundImage1 = new ImageView(BACKGROUND_IMG);
			ImageView backgroundImage2 = new ImageView(BACKGROUND_IMG);
			//set columns and rows 
			//da background 256x256 muss für width = 800, col & rows = 4
			GridPane.setConstraints(backgroundImage1, i%4, i/4);
			GridPane.setConstraints(backgroundImage2, i%4, i/4);
			gridPane1.getChildren().add(backgroundImage1);
			gridPane2.getChildren().add(backgroundImage2);
		}
		gridPane2.setLayoutY(-GAME_HEIGHT); 
		gamePane.getChildren().addAll(gridPane1, gridPane2);
	}
	
	private void moveBackground() {
		gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.5); 
		gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.5);
		
		if(gridPane1.getLayoutY() >=GAME_HEIGHT) {
			gridPane1.setLayoutY(-GAME_HEIGHT);
		}

		if(gridPane2.getLayoutY() >=GAME_HEIGHT) {
			gridPane2.setLayoutY(-GAME_HEIGHT);
		}
	}
	/*
	private void collision() {
		for(int i = 0; i < brownMeteors.length; i++) {
			if(ENTITIES_SIZE + ENTITIES_SIZE > distance(player.getLayoutX() + ENTITIES_SIZE/2, brownMeteors[i].getLayoutX() + PLAYER_RADIUS/2, 
					player.getLayoutY() + ENTITIES_SIZE/2, brownMeteors[i].getLayoutY() + PLAYER_RADIUS/2)) {
				setNewElementPosition(brownMeteors[i]);
			}
		}
		for(int i = 0; i < greyMeteors.length; i++) {
			if(ENTITIES_SIZE + ENTITIES_SIZE > distance(player.getLayoutX() + ENTITIES_SIZE/2, greyMeteors[i].getLayoutX() + ENTITIES_SIZE/2, 
					player.getLayoutY() + ENTITIES_SIZE/2, greyMeteors[i].getLayoutY() + ENTITIES_SIZE/2)) {
				setNewElementPosition(greyMeteors[i]);
			}
		}
	}
	*/
	
	private void collision() {
		for(int i = 0; i < brownMeteors.length; i++) {
			if( (distance(brownMeteors[i].getLayoutX() + ENTITIES_SIZE/2, brownMeteors[i].getLayoutY() + ENTITIES_SIZE/2,
					player.getLayoutX() + 372 + ENTITIES_SIZE/2, player.getLayoutY() + GAME_WIDTH - 90 - ENTITIES_SIZE/2)) <= (ENTITIES_SIZE/2 + ENTITIES_SIZE/2)) {
				setNewElementPosition(brownMeteors[i]);
			}
		}
		for(int i = 0; i < greyMeteors.length; i++) {
			if( (distance(greyMeteors[i].getLayoutX() + ENTITIES_SIZE/2, greyMeteors[i].getLayoutY() + ENTITIES_SIZE/2,
					player.getLayoutX() + 372 + ENTITIES_SIZE/2, player.getLayoutY() + GAME_WIDTH - 90 - ENTITIES_SIZE/2)) <= (ENTITIES_SIZE/2 + ENTITIES_SIZE/2)) {
				setNewElementPosition(greyMeteors[i]);
			}
		}
	}
	
	
	private double distance(double x1, double y1, double x2, double y2) {
		return (Math.sqrt(Math.pow( (x1-x2), 2) + Math.pow( (y1-y2), 2)));
	}
	
}


