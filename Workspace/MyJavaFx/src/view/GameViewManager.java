package view;


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
	private static final int SHIP_SIZE = 60;
	
	private Stage menuStage;
	private ImageView ship;
	
	private boolean isLeftKeyPressed;
	private boolean isRigtKeyPressed;
	private int angle;
	private AnimationTimer gameTimer;
	
	private GridPane gridPane1;
	private GridPane gridPane2;
	public final static String BACKGROUND_IMG = "view/resources/blue.png";
	
	
	public GameViewManager() {
		initializeStage();
		createKeyListener();
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
		createGameLoop();
		gameStage.setTitle("Biofeedback Anwendung");
		gameStage.show();
	}
	
	private void createShip() {
		
		ship = new ImageView("view/resources/playerShip1_orange.png");
		
		ship.setFitHeight(SHIP_SIZE);
		ship.setFitWidth(SHIP_SIZE);
		
		ship.setX(GAME_WIDTH / 2 - SHIP_SIZE / 2); //600/2-49 = 251  -49
		ship.setY(GAME_HEIGHT - 90); //-90
		gamePane.getChildren().add(ship);
	}
	
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				moveBackground();
				moveShip();
				
			}
			
		};
		gameTimer.start();
	}
	//99 x 75 breite x höhe
	private void moveShip() {
		//nach links
		if(isLeftKeyPressed && !isRigtKeyPressed) {
			System.out.println(ship.getLayoutX());
			if(angle > -30) { //borders
				angle -= 5;
			}
			ship.setRotate(angle);
			if(ship.getLayoutX() > - GAME_WIDTH / 2 + SHIP_SIZE / 2) {
				ship.setLayoutX(ship.getLayoutX() -3);
			}
		}
		
		//nach rechts
		if(isRigtKeyPressed && !isLeftKeyPressed) {
			if(angle < 30) {
				angle += 5;
			}
			ship.setRotate(angle);
			if(ship.getLayoutX() < GAME_WIDTH / 2 - SHIP_SIZE / 2) {
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


