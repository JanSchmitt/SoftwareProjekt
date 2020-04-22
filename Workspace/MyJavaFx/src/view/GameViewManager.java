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

	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private static final int GAME_WIDTH = 800;
	private static final int GAME_HEIGHT = 750;
	private static final int ENTITIES_SIZE = 60;
	
	private Stage menuStage;
	private ImageView ship;
	
	private boolean isLeftKeyPressed;
	private boolean isRigtKeyPressed;
	private int angle;
	private AnimationTimer gameTimer;
	
	private GridPane gridPane1;
	private GridPane gridPane2;
	public final static String BACKGROUND_IMG = "view/resources/blue.png";
	
	private final static String METEOR_GREY_IMG = "view/resources/meteorGrey_med1.png";
	private final static String METEOR_BROWN_IMG = "view/resources/meteorBrown_med1.png";
	
	private ImageView[] brownMeteors;
	private ImageView[] greyMeteors;
	Random RAND;
	
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
		createShip();
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
				moveShip();
				
			}
		};
		gameTimer.start();
	}
	
	
	//Meteors
	////////////////////////////////
	private void createGameElements() {
		//jeweils max 3 meteors generaten
		brownMeteors = new ImageView[3];
		for(int i = 0;i<brownMeteors.length; i++) {
			//erzeugen der ImageView Objekte
			brownMeteors[i] = new ImageView(METEOR_BROWN_IMG);
			
			//auf selbe Größe wie Player setzen
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
	}
	
	private void moveGameElements() {
		for(int i = 0; i < brownMeteors.length; i++) {
			brownMeteors[i].setLayoutY(brownMeteors[i].getLayoutY() + 4);
			brownMeteors[i].setRotate(brownMeteors[i].getRotate()+4);
		}
		
		for(int i = 0; i < greyMeteors.length; i++) {
			greyMeteors[i].setLayoutY(greyMeteors[i].getLayoutY() + 3); 
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
	
	//Ship(Player)
	///////////////////////////
	private void createShip() {
		
		ship = new ImageView("view/resources/playerShip1_orange.png");
		
		ship.setFitHeight(ENTITIES_SIZE);
		ship.setFitWidth(ENTITIES_SIZE);
		
		ship.setX(GAME_WIDTH / 2 - ENTITIES_SIZE / 2); //600/2-49 = 251  -49
		ship.setY(GAME_HEIGHT - 90); //-90
		gamePane.getChildren().add(ship);
	}
	
	private void moveShip() {
		//nach links
		if(isLeftKeyPressed && !isRigtKeyPressed) {
			if(angle > -30) { //borders
				angle -= 5;
			}
			ship.setRotate(angle);
			if(ship.getLayoutX() > - GAME_WIDTH / 2 + ENTITIES_SIZE / 2) {
				ship.setLayoutX(ship.getLayoutX() -3);
			}
		}
		
		//nach rechts
		if(isRigtKeyPressed && !isLeftKeyPressed) {
			if(angle < 30) {
				angle += 5;
			}
			ship.setRotate(angle);
			if(ship.getLayoutX() < GAME_WIDTH / 2 - ENTITIES_SIZE / 2) {
				ship.setLayoutX(ship.getLayoutX() +3);
			}
		}
		
		//pos beibehalten
		if(!isLeftKeyPressed && !isRigtKeyPressed) {
			if(angle < 0) {
				angle += 5;
			}else if(angle > 0) {
				angle -= 5;
			}
			ship.setRotate(angle);
		}
		
		//pos beibehalten
		if(isRigtKeyPressed && isLeftKeyPressed) {
			if(angle < 0) {
				angle += 5;
			}else if(angle > 0) {
				angle -= 5;
			}
			ship.setRotate(angle);
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
}


