package view;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.InfoLabel;
import timer.Score;

public class GameViewManager {
	
	//constants
	//{
	//Window size
	
	private static final int WINDOW_WIDTH = 1440;
	private static final int WINDOW_HEIGHT = 800;
	
	private static final int GAME_WIDTH = 1008; //0.7*WINDOW_WIDTH
	private static final int GAME_HEIGHT = 800;
	
	private static final int RIGHT_PANE_WIDTH = WINDOW_WIDTH - GAME_WIDTH;       //0.3*WINDOW_WIDTH = //432
	private static final int RIGHT_PANE_HEIGHT = 800;
	//Entities size
	private static final int ENTITIES_SIZE = 60;
	//}
	
	//Ebenen
	public AnchorPane anchor0;
	public SplitPane splitPane;
	public AnchorPane gamePane;
	public AnchorPane rightPane;
	public Scene gameScene;
	public Stage gameStage;
	public Stage menuStage;
	
	//Gridpane for Background IMG
	private GridPane gridPane1;
	private GridPane gridPane2;
	
	//Timer
	public AnimationTimer gameTimer;
    
	Random RAND;
	private int angle;
	
	//KeyEvents
	private boolean isLeftKeyPressed;
	private boolean isRigtKeyPressed;
	private boolean shooting;

	//IMG URL
	public final static String BACKGROUND_IMG = "view/resources/blue.png";
	public final static String RED_BACKGROUND_IMG = "view/resources/red.png";
	public final static String GREEN_BACKGROUND_IMG = "view/resources/green.png";
	private final static String METEOR_GREY_IMG = "view/resources/meteorGrey_med1.png";
	private final static String METEOR_BROWN_IMG = "view/resources/meteorBrown_med1.png";
	private final static String LASER_IMG = "view/resources/laserGreen04.png";
	
	
	//IMG Objects to create
	private ImageView player;
	private ImageView[] brownMeteors;
	private ImageView[] greyMeteors;
	private ImageView laserShot;
	private ArrayList<Node> lasers = new ArrayList<Node>();
	
	private InfoLabel scoreLabel;
	private Score sc;
	
	//Biofeedback manager
	private FeedbackManager feedback = new FeedbackManager(this);
	
	private Text scoreDecrease[] = {null,null,null};
	
	//text example
	//Text bsp;
	
	public GameViewManager() {

		initializeStage();
		createKeyListener();
		RAND = new Random();
	}
	
	
	private void createKeyListener() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if(e.getCode() == KeyCode.A) { //LEFT
					isLeftKeyPressed = true;
				}else if(e.getCode() == KeyCode.D) { //RIGHT
					isRigtKeyPressed = true;
				}else if(e.getCode() == KeyCode.SPACE) { 
					if(!shooting) { //wenn leertaste losgelassen wurde
						if(sc.time % 2 == 0) {
							laserShot = new ImageView(LASER_IMG);
							Node newLaserShot = laserShot;
							newLaserShot.relocate(player.getLayoutX() + ENTITIES_SIZE / 2 - 13/2 , player.getLayoutY());
							lasers.add(newLaserShot);
							gamePane.getChildren().add(newLaserShot);
							System.out.println("size: " +lasers.size());
							shooting = true;
						}
						
						
					}
				}else if(e.getCode()==KeyCode.DIGIT1) {
					feedback.setMode(1);
				}else if(e.getCode()==KeyCode.DIGIT2) {
					feedback.setMode(0);
				}else if(e.getCode()==KeyCode.DIGIT3) {
					feedback.setMode(2);
				}
			}
		});
	
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if(e.getCode() == KeyCode.A) {
					isLeftKeyPressed = false;
				}else if(e.getCode() == KeyCode.D) {
					isRigtKeyPressed = false;
				}else if(e.getCode() == KeyCode.SPACE) {
					shooting = false;
				}
			}
			
		});
	}

	private void initializeStage() {

		anchor0 = new AnchorPane();
		anchor0.setLayoutX(0);
		anchor0.setLayoutY(0);
		anchor0.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT); //breite vom fenster
		anchor0.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		anchor0.setMaxSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	
		//Linke Pane: SpaceInvadors
		gamePane = new AnchorPane();
		//gamePane.boundsInLocalProperty();
		//gamePane.boundsInParentProperty();
		gamePane.setLayoutX(0);
		gamePane.setLayoutY(0);
		gamePane.setPrefSize(GAME_WIDTH, GAME_HEIGHT); //1008,800
	
		//Rechte Pane: Minispiele etc
		rightPane = new AnchorPane();
		
		rightPane.setLayoutX(GAME_WIDTH);
		rightPane.setLayoutY(0);
		rightPane.setPrefSize(RIGHT_PANE_WIDTH, RIGHT_PANE_HEIGHT);  //432
		
		
		//splitPane.getChildrenUnmodifiable().addAll(gamePane, rightPane);
		anchor0.getChildren().addAll(gamePane, rightPane);
		
		
		//gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameScene = new Scene(anchor0);
		gameStage = new Stage();//eine Stage 
		gameStage.setScene(gameScene);
		gameStage.setResizable(false);
		
	}
	
	public void createNewGame(Stage menuStage) {
		this.menuStage = menuStage;
		//this.menuStage.hide();
		this.menuStage.close();
		
		sc = new Score();
		sc.startTimer();
		
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
				shootLaser();
				checkIfElemetsAreUnderShip();
				collision();
				moveShip();
				updateScoreLabel();
				moveScoreDecrease();
				//moveText();				
			}
		};
		gameTimer.start();
	}
	

	//Meteors(Enemies)
	////////////////////////////////
	private void createGameElements() {
		
		//Score Label
		scoreLabel = new InfoLabel("Score: " + sc.score);
		scoreLabel.setLayoutX(RIGHT_PANE_WIDTH/2 - scoreLabel.IMG_WIDTH/2);
		scoreLabel.setLayoutY(20);
		rightPane.getChildren().add(scoreLabel);
		
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
			brownMeteors[i].setLayoutY(brownMeteors[i].getLayoutY() + 4); //speed //4
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
		
		player.setLayoutX(GAME_WIDTH / 2 - ENTITIES_SIZE / 2);
		player.setLayoutY(GAME_HEIGHT - 90);
		
		gamePane.getChildren().add(player);
		
		//ImageView player2 = new ImageView("view/resources/playerShip1_orange.png");
		//player2.setLayoutX(0);
		//player2.setLayoutY(GAME_HEIGHT - 90);
		//rightPane.getChildren().add(player2);
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
				//System.out.println(player.getLayoutX() + "     " + player.getLayoutY());
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
				//System.out.println(player.getLayoutX() + "     " + player.getLayoutY());
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
	
	//Lasershot 13x37 (width x height)
	///////////////////////////
	private void shootLaser() {
		for(int i = 0; i<lasers.size(); i++) {
			if(lasers.get(i).getLayoutY() > -lasers.get(i).getBoundsInParent().getHeight()  ) { //-37 wenn unterhalb des windows 
				lasers.get(i).relocate(lasers.get(i).getLayoutX(), lasers.get(i).getLayoutY() - 3); //um 3 pixel nach oben bewegen
			}
			else { //wenn oberhalb des windows 
				
				gamePane.getChildren().remove(lasers.get(i));
				lasers.remove(i);
				System.out.println(lasers.size());
			}
		}
	}
	
	//Background
	/////////////////////////////////
	public void createBackground() {
		//checks current gamemode
		String imageSource="";
		if(feedback.getmode()==0) {
			imageSource=BACKGROUND_IMG;
		}else if(feedback.getmode()==1) {
			imageSource=GREEN_BACKGROUND_IMG;
		}else if(feedback.getmode()==2) {
			imageSource=RED_BACKGROUND_IMG;
		}
		
		//only for first initialization
		if(!feedback.getChanging()) {
			//GridPane, da Background zu klein Screen-size ist
			//�berlagerung des Backgrounds als Gittereinheit
			gridPane1 = new GridPane();
			//when moves 2nd must cover the empty spot
			gridPane2 = new GridPane();
		}
		//only during changes by biofeedback
		if(feedback.getChanging()) {
			gridPane1.getChildren().clear();
			gridPane2.getChildren().clear();
		}
		
		for(int i = 0; i<16;i++) {
			ImageView backgroundImage1 = new ImageView(imageSource);
			//size von bild(256x256 p), size anpassen dass gridpane auf vier geteilt wird
			backgroundImage1.setFitHeight(GAME_WIDTH /4); 
			backgroundImage1.setFitWidth(GAME_WIDTH/4);
		
			ImageView backgroundImage2 = new ImageView(imageSource);
			backgroundImage2.setFitHeight(GAME_WIDTH /4);
			backgroundImage2.setFitWidth(GAME_WIDTH/4);
			
			//set columns and rows 
			
			GridPane.setConstraints(backgroundImage1, i%4, i/4); //4x4 grid
			GridPane.setConstraints(backgroundImage2, i%4, i/4); //4x4 grid
			gridPane1.getChildren().add(backgroundImage1);
			gridPane2.getChildren().add(backgroundImage2);
		}
		
		//only for first initialization
		if(!feedback.getChanging()) {
			gridPane2.setLayoutY(-GAME_HEIGHT); 
			gamePane.getChildren().addAll(gridPane1, gridPane2);
		}
	}
	
	private void moveBackground() {
		if(feedback.getmode()==0) {
			gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.5);
			gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.5);
		}else if(feedback.getmode()==1) {
			gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.2);
			gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.2);
		}else if(feedback.getmode()==2) {
			gridPane1.setLayoutY(gridPane1.getLayoutY() + 2);
			gridPane2.setLayoutY(gridPane2.getLayoutY() + 2);
		}
		
		if(gridPane1.getLayoutY() >=GAME_HEIGHT) {
			gridPane1.setLayoutY(-GAME_HEIGHT);
		}

		if(gridPane2.getLayoutY() >=GAME_HEIGHT) {
			gridPane2.setLayoutY(-GAME_HEIGHT);
		}
	}
	
	
	private double distance(double x1, double y1, double x2, double y2) {
		return (Math.sqrt(Math.pow( (x1-x2), 2) + Math.pow( (y1-y2), 2)));
	}
	
	private void collision() {
		//player vs brown Meteor
		for(int i = 0; i < brownMeteors.length; i++) {
			if( distance(brownMeteors[i].getLayoutX() + ENTITIES_SIZE/2, brownMeteors[i].getLayoutY() - ENTITIES_SIZE/2, 
					player.getLayoutX() + ENTITIES_SIZE/2, player.getLayoutY() - ENTITIES_SIZE/2) <= ENTITIES_SIZE) {
				showScoreDecrease(brownMeteors[i].getLayoutX());
				setNewElementPosition(brownMeteors[i]);
				
				//Update score -
				sc.score -= 3 * sc.collisionCounter;                    
				sc.collisionCounter++;
				
			}
		}
		//player vs grey Meteor
		for(int i = 0; i < greyMeteors.length; i++) {
			if( distance(greyMeteors[i].getLayoutX() + ENTITIES_SIZE/2, greyMeteors[i].getLayoutY() - ENTITIES_SIZE/2, 
					player.getLayoutX() + ENTITIES_SIZE/2, player.getLayoutY() - ENTITIES_SIZE/2) <= ENTITIES_SIZE) {
				showScoreDecrease(greyMeteors[i].getLayoutX());
				setNewElementPosition(greyMeteors[i]);

				//Update score -
				sc.score -= 3 * sc.collisionCounter;
				sc.collisionCounter++;
				
			}
		}
		
		
		//laser vs brown Meteor
		for(int i = 0; i < lasers.size(); i++) {
			for(int j = 0; j < brownMeteors.length; j++) { // 0 - 2 -> 3elem
				if(lasers.get(i).getBoundsInParent().intersects(brownMeteors[j].getBoundsInParent())) {	// bounds of a node in it's parent coordinates
					setNewElementPosition(brownMeteors[j]);
					gamePane.getChildren().remove(lasers.get(i));
					lasers.remove(i);
					System.out.println(lasers.size());
					
					//Update score +
					sc.score += 1; 
					break; //kein fehler mehr durch index out of bounds verletzung
				}
			}
		}	
		
		for(int i = 0; i < lasers.size(); i++) {
			for(int j = 0; j < greyMeteors.length; j++) { // 0 - 2 -> 3elem
				if(lasers.get(i).getBoundsInParent().intersects(greyMeteors[j].getBoundsInParent())) {	// bounds of a node in it's parent coordinates
					setNewElementPosition(greyMeteors[j]);
					gamePane.getChildren().remove(lasers.get(i));
					lasers.remove(i);
					System.out.println(lasers.size());
					
					//Update score +
					sc.score += 1; 
					break; //kein fehler mehr durch index out of bounds verletzung
				}
			}
			
		}		
	}
	
		
	private void updateScoreLabel() {
		String textToSet = "Score: ";
		scoreLabel.setText(textToSet + sc.score);
	}		
	
	//changes Colors depending on gamemode change
	public void changeColors(int mode) {
		if(mode==0) {
			Image newPlayer=new Image("view/resources/playerShip1_orange.png");
			player.setImage(newPlayer);
			Image newMeteor1=new Image("view/resources/meteorBrown_med1.png");
			for(int i=0; i<brownMeteors.length; i++) {
				brownMeteors[i].setImage(newMeteor1);
			}
			Image newMeteor2=new Image("view/resources/meteorGrey_med1.png");
			for(int i=0; i<brownMeteors.length; i++) {
				greyMeteors[i].setImage(newMeteor2);
			}
		}else if(mode==1) {
			Image newPlayer=new Image("view/resources/playerShip1_green.png");
			player.setImage(newPlayer);
			Image newMeteor1=new Image("view/resources/meteorLightGreen_med1.png");
			for(int i=0; i<brownMeteors.length; i++) {
				brownMeteors[i].setImage(newMeteor1);
			}
			Image newMeteor2=new Image("view/resources/meteorGreen_med1.png");
			for(int i=0; i<brownMeteors.length; i++) {
				greyMeteors[i].setImage(newMeteor2);
			}
		}else if(mode==2) {
			Image newPlayer=new Image("view/resources/playerShip1_red.png");
			player.setImage(newPlayer);
			Image newMeteor1=new Image("view/resources/meteorDarkRed_med1.png");
			for(int i=0; i<brownMeteors.length; i++) {
				brownMeteors[i].setImage(newMeteor1);
			}
			Image newMeteor2=new Image("view/resources/meteorLightRed_med1.png");
			for(int i=0; i<brownMeteors.length; i++) {
				greyMeteors[i].setImage(newMeteor2);
			}
			
			/*
			bsp=new Text("  !!!HINWEIS!!!:   \nDies ist ein Beispiel");
			bsp.setFont(new Font(40));
			bsp.setLayoutX(0);
			bsp.setLayoutY(0);
			gamePane.getChildren().add(bsp);
			*/
		}
		
	}
	
	/*
	//moves text example
	private void moveText(){
		if(bsp.getLayoutX()<=WINDOW_WIDTH) {
			bsp.setLayoutX(bsp.getLayoutX()+5);
			bsp.setLayoutY(bsp.getLayoutX()+5);
			bsp.setRotate(bsp.getRotate()-1);
		}
	}
	*/
	
	//changes properties of score label
	public void changeText(int m) {
		if(m==0) {
			scoreLabel.setFont(new Font(20));
			scoreLabel.setEffect(null);
		}if(m==1) {
			scoreLabel.setFont(new Font(25));
			scoreLabel.setEffect(null);
		}if(m==2) {
			scoreLabel.setFont(new Font(20));
			BoxBlur blur=new BoxBlur();
			blur.setIterations(2);
			scoreLabel.setEffect(blur);
		}
	}
	
	//shows score decrease after collision
	private void showScoreDecrease(double x) {
		if(feedback.getmode()==1) {
			return;
		}
		for(int i=0; i<3; i++) {
			if(scoreDecrease[i]==null) {
				scoreDecrease[i]=new Text(""+(-3 * sc.collisionCounter));
				scoreDecrease[i].setFont(new Font(10));
				scoreDecrease[i].setLayoutX(x);
				scoreDecrease[i].setLayoutY(WINDOW_HEIGHT-150);
				if(feedback.getmode()==2) {
					scoreDecrease[i].setFill(Color.RED);
				}
				scoreDecrease[i].setScaleX(3);
				scoreDecrease[i].setScaleY(3);
				gamePane.getChildren().add(scoreDecrease[i]);
				return;
			}
		}
		gamePane.getChildren().remove(scoreDecrease[0]);
		scoreDecrease[0]=null;
		scoreDecrease[0]=new Text(""+(-3 * sc.collisionCounter));
		scoreDecrease[0].setScaleX(3);
		scoreDecrease[0].setScaleY(3);
		if(feedback.getmode()==2) {
			scoreDecrease[0].setFill(Color.RED);
		}
		gamePane.getChildren().add(scoreDecrease[0]);
		scoreDecrease[0].setLayoutX(x);
		scoreDecrease[0].setLayoutY(WINDOW_HEIGHT-150);
		scoreDecrease[0].setStyle(LASER_IMG);
		scoreDecrease[0].setFill(Color.RED);
	}
	
	//animates text from score decrease
	private void moveScoreDecrease() {
		if(feedback.getmode()==2) {
			for(int i=0; i<3; i++) {
				if(scoreDecrease[i]!=null) {
					scoreDecrease[i].setScaleX(scoreDecrease[i].getScaleX()+0.3);
					scoreDecrease[i].setScaleY(scoreDecrease[i].getScaleY()+0.3);
					if(scoreDecrease[i].getScaleX()>20) {
						gamePane.getChildren().remove(scoreDecrease[i]);
						scoreDecrease[i]=null;
					}
				}
			}
		}else if(feedback.getmode()==0) {
			for(int i=0; i<3; i++) {
				if(scoreDecrease[i]!=null) {
					scoreDecrease[i].setScaleX(scoreDecrease[i].getScaleX()+0.02);
					scoreDecrease[i].setScaleY(scoreDecrease[i].getScaleY()+0.02);
					if(scoreDecrease[i].getScaleX()>5) {
						gamePane.getChildren().remove(scoreDecrease[i]);
						scoreDecrease[i]=null;
					}
				}
			}
		}
	}
	
}


