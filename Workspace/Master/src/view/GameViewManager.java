package view;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameViewManager {
	
	//constants
	//{
	//Window size
	private static final int GAME_WIDTH = 800;
	private static final int GAME_HEIGHT = 750;
	//Entities size
	private static final int ENTITIES_SIZE = 60;
	//}
	
	//Ebenen
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	private Stage menuStage;
	
	//Gridpane for Background IMG
	private GridPane gridPane1;
	private GridPane gridPane2;
	
	private int angle;

	private AnimationTimer gameTimer;
	
	Random RAND;
	
	//KeyEvents
	private boolean isLeftKeyPressed;
	private boolean isRigtKeyPressed;
	private boolean shooting;

	//IMG URL
	public final static String BACKGROUND_IMG = "view/resources/blue.png";
	private final static String METEOR_GREY_IMG = "view/resources/meteorGrey_med1.png";
	private final static String METEOR_BROWN_IMG = "view/resources/meteorBrown_med1.png";
	private final static String LASER_IMG = "view/resources/laserGreen04.png";
	
	//IMG Objects to create
	private ImageView player;
	private ImageView[] brownMeteors;
	private ImageView[] greyMeteors;
	//private ImageView[] laserShot;
	private ImageView laserShot;
	
	private ArrayList<Node> lasers = new ArrayList<Node>();
	
	
	
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
				}else if(e.getCode() == KeyCode.SPACE) {
					if(!shooting) {
						laserShot = new ImageView(LASER_IMG);
						Node newLaserShot = laserShot;
						newLaserShot.relocate(player.getLayoutX() + ENTITIES_SIZE / 2 - 13/2 , player.getLayoutY());
						lasers.add(newLaserShot);
						gamePane.getChildren().add(newLaserShot);
						shooting = true;
					}
					
					
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
				}else if(e.getCode() == KeyCode.SPACE) {
					shooting = false;
				}
				e.consume();
			}
			
		});
	}
	
	private void shootLaser() {
		for(int i = 0; i<lasers.size(); i++) {
			if(lasers.get(i).getLayoutY() > 0) {
				lasers.get(i).relocate(lasers.get(i).getLayoutX(), lasers.get(i).getLayoutY() - 3);
			}
			else {
				lasers.remove(i);
			}
		}
	}

	private void initializeStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameStage = new Stage();//eine Stage 
		gameStage.setScene(gameScene);
		gameStage.setResizable(false);
	}
	
	public void createNewGame(Stage menuStage) {
		this.menuStage = menuStage;
		this.menuStage.hide();

		createBackground();
		createPlayer();
		//createLaserShot();
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
				shootLaser();
				//shoot();
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
			
			//auf selbe Gr��e wie Player setzen = 60x60
			brownMeteors[i].setFitHeight(ENTITIES_SIZE);
			brownMeteors[i].setFitWidth(ENTITIES_SIZE);
			//
			setNewElementPosition(brownMeteors[i]);
			gamePane.getChildren().add(brownMeteors[i]);
		}
		
		greyMeteors = new ImageView[3];
		for(int i = 0;i<greyMeteors.length; i++) {
			//auf selbe Gr��e wie Player setzen
			greyMeteors[i] = new ImageView(METEOR_GREY_IMG);
			
			//auf selbe Gr��e wie Player setzen
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
		
		//player.setX(GAME_WIDTH / 2 - ENTITIES_SIZE / 2); //600/2-49 = 251  -49
		//player.setY(GAME_HEIGHT - 90); //-90
		player.setLayoutX(GAME_WIDTH / 2 - ENTITIES_SIZE / 2);
		player.setLayoutY(GAME_HEIGHT - 90);
		gamePane.getChildren().add(player);
	}
	
	private void moveShip() {
		//nach links
		if(isLeftKeyPressed && !isRigtKeyPressed) {
			if(angle > -30) { //borders
				angle -= 5;
			}
			player.setRotate(angle);
			//if(player.getLayoutX() > - GAME_WIDTH / 2 + ENTITIES_SIZE / 2) {
			if(player.getLayoutX() > 0) {
				player.setLayoutX(player.getLayoutX() -3);
				//moveLaserShots(player.getLayoutX(), player.getLayoutY(), -3);
				System.out.println(player.getLayoutX() + "     " + player.getLayoutY());
			}
		}
		
		//nach rechts
		if(isRigtKeyPressed && !isLeftKeyPressed) {
			if(angle < 30) {
				angle += 5;
			}
			player.setRotate(angle);
			//if(player.getLayoutX() < GAME_WIDTH / 2 - ENTITIES_SIZE / 2) {
			if(player.getLayoutX() < GAME_WIDTH - ENTITIES_SIZE) {
				player.setLayoutX(player.getLayoutX() +3);
				//moveLaserShots(player.getLayoutX(), player.getLayoutY(), 3);
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
	/*
	private void checkIfShot() {
		if(isSpaceKeyPressed) {
			laserShot = new ImageView(LASER_IMG);
			laserShot.setLayoutX(player.getLayoutX() + ENTITIES_SIZE / 2 - 13/2);
			laserShot.setLayoutY(player.getLayoutY());
			
			//laserShot[i].setVisible(false);
			gamePane.getChildren().add(laserShot); //200millies warten
			
			
			
			//moveLaserShot();


			
		}
	}
	
	private void moveLaserShot() {
	
		if(laserShot.getLayoutY() + 37 > 0) {
			laserShot.relocate(laserShot.getLayoutX(), laserShot.getLayoutY() - 5);	
			//laserShot.setLayoutY(laserShot.getLayoutY() - 2);
			
		}else if(laserShot.getLayoutY() + 37 < 0) {
			gamePane.getChildren().remove(laserShot);
			System.out.println("R");
		}	
	}
	
	/*
	private void shoot() {
		if(isSpaceKeyPressed && displayedShots < MAX_SHOTS_SIZE) {
			createLaserShot(player.getLayoutX() + ENTITIES_SIZE / 2 - 13/2, player.getLayoutY());
		}
		//return new Shot(posX + size / 2 - Shot.size / 2, posY - Shot.size);
	}
	*/
	
	//Laser Shot (13x37) (width,height)
	//////////////////////////
	/*
	private void createLaserShot() {
		laserShot = new ImageView[maxLaserShots];
		for(int i = 0;i<laserShot.length; i++) {
			//erzeugen der ImageView Objekte
			laserShot[i] = new ImageView(LASER_IMG);
			laserShot[i].setLayoutX(player.getLayoutX() + ENTITIES_SIZE / 2 - 13/2);
			laserShot[i].setLayoutY(player.getLayoutY());
			//laserShot[i].setVisible(false);
			gamePane.getChildren().add(laserShot[i]);
		}
	}
	
	private void moveLaserShots(double x, double y, int delta) {
		for(int i = 0; i < laserShot.length; i++) {
			laserShot[i].setLayoutX(laserShot[i].getLayoutX() + delta);
		}
	}
	
	private void shoot() {
		if(isSpaceKeyPressed) {
			toShootLaser[currentLaserShot] = true;
		}
		if(toShootLaser[currentLaserShot] && currentLaserShot < laserShot.length) {
			laserShot[currentLaserShot].setLayoutY(laserShot[currentLaserShot].getLayoutY() - 2);
		}

		
	}
/*
	private void moveLaserShot() {
		if(laserShot.getLayoutY() + 37 > 0) {
			laserShot.setLayoutY(laserShot.getLayoutY() - 2);
		}else if(laserShot.getLayoutY() + 37 < 0) {
			gamePane.getChildren().remove(laserShot);
			displayedShots--;
		}	
	}
	*/
	//Background
	/////////////////////////////////
	private void createBackground() {
		//GridPane, da Background zu klein Screen-size ist
		//�berlagerung des Backgrounds als Gittereinheit
		gridPane1 = new GridPane();
		//when moves 2nd must cover the empty spot
		gridPane2 = new GridPane();
		
		for(int i = 0; i<16;i++) {
			ImageView backgroundImage1 = new ImageView(BACKGROUND_IMG);
			ImageView backgroundImage2 = new ImageView(BACKGROUND_IMG);
			//set columns and rows 
			//da background 256x256 muss f�r width = 800, col & rows = 4
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
	
	private void collision() {
		for(int i = 0; i < brownMeteors.length; i++) {
			if( distance(brownMeteors[i].getLayoutX() + ENTITIES_SIZE/2, brownMeteors[i].getLayoutY() - ENTITIES_SIZE/2, 
					player.getLayoutX() + ENTITIES_SIZE/2, player.getLayoutY() - ENTITIES_SIZE/2) <= ENTITIES_SIZE) {
				setNewElementPosition(brownMeteors[i]);
			}
		}
		for(int i = 0; i < greyMeteors.length; i++) {
			if( distance(greyMeteors[i].getLayoutX() + ENTITIES_SIZE/2, greyMeteors[i].getLayoutY() - ENTITIES_SIZE/2, 
					player.getLayoutX() + ENTITIES_SIZE/2, player.getLayoutY() - ENTITIES_SIZE/2) <= ENTITIES_SIZE) {
				setNewElementPosition(greyMeteors[i]);
			}
		}
	}
	
	/*
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
	*/
	
	private double distance(double x1, double y1, double x2, double y2) {
		return (Math.sqrt(Math.pow( (x1-x2), 2) + Math.pow( (y1-y2), 2)));
	}
	
}


