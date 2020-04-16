package view;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameViewManager {

	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private static final int GAME_WIDTH = 600;
	private static final int GAME_HEIGHT = 800;
	
	private Stage menuStage;
	private ImageView ship;
	
	private boolean isLeftKeyPressed;
	private boolean isRigtKeyPressed;
	private int angle;
	private AnimationTimer gameTimer;
	
	private final static String METEOR_BROWN_IMG = "view/resources/meteorBrown_med1.png";
	
	private ImageView[] brownMeteors;
	Random randomPositionGenerator;
	
	public GameViewManager() {
		initializeStage();
		createKeyListener();
		randomPositionGenerator = new Random();
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
	}
	
	public void createNewGame(Stage menuStage) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		createShip();
		createGameElements();
		createGameLoop();
		gameStage.show();
	}
	
	private void createGameElements() {
		brownMeteors = new ImageView[3];
		for(int i = 0;i<brownMeteors.length; i++) {
			brownMeteors[i] = new ImageView(METEOR_BROWN_IMG);
			setNewElemPos(brownMeteors[i]);
			gamePane.getChildren().add(brownMeteors[i]);
		}
	}
	
	private void moveGameElems() {
		for(int i = 0;i<brownMeteors.length;i++) {
			brownMeteors[i].setLayoutY(brownMeteors[i].getLayoutY()+7);
			brownMeteors[i].setRotate(brownMeteors[i].getRotate()+4);
		}
	}
	
	private void checkIfElemsAreBehindShipAndRelocate() {
		for(int i = 0;i<brownMeteors.length;i++) {
			if(brownMeteors[i].getLayoutY() > 900) {
				setNewElemPos(brownMeteors[i]);
			}
		}
	}
	
	private void setNewElemPos(ImageView image) {
		image.setLayoutX(randomPositionGenerator.nextInt(370));
		image.setLayoutY(-(randomPositionGenerator.nextInt(3200) + 600));//3200
		
	
	}
	private void createShip() {
		ship = new ImageView("view/resources/playerShip1_orange.png");
		ship.setX(GAME_WIDTH / 2 - 49); //600/2-49 = 251
		ship.setY(GAME_HEIGHT -90);
		gamePane.getChildren().add(ship);
	}
	
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				moveGameElems();
				checkIfElemsAreBehindShipAndRelocate();
				moveShip();
				
			}
			
		};
		gameTimer.start();
	}
	//99 x 75 breite x höhe
	private void moveShip() {
		//nach links
		if(isLeftKeyPressed && !isRigtKeyPressed) {
			if(angle > -30) { //borders
				angle -= 5;
			}
			ship.setRotate(angle);
			if(ship.getLayoutX() > -249) {
				ship.setLayoutX(ship.getLayoutX() -3);
			}
		}
		
		//nach rechts
		if(isRigtKeyPressed && !isLeftKeyPressed) {
			if(angle < 30) {
				angle += 5;
			}
			ship.setRotate(angle);
			if(ship.getLayoutX() < 248) {
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
}

