package application;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class JumpGame implements EventHandler<KeyEvent>{
	//static values
	private static final int SCENE_WIDTH = 1000;
	private static final int SCENE_HEIGHT = 300;
	private static final int PLAYER_SIZE = 100;
	private static final int OBSTACLE_SIZE = 100;
	private static final int PLAYER_HEIGHT = 50;
	private static final int HIGH_OBSTACLE_HEIGHT = 70;
	private static final int LOW_OBSTACLE_HEIGHT = 30;
	private static final int OBSTACLES_AMOUNT = 40;	
	
	
	private Stage stage;
	private AnchorPane layout=new AnchorPane();
	private Scene scene=new Scene(layout,SCENE_WIDTH,SCENE_HEIGHT);	
	private Player p=new Player();
	private ImageView player;
	private Obstacle[] o=new Obstacle[OBSTACLES_AMOUNT];
	private ImageView[] obstacle=new ImageView[OBSTACLES_AMOUNT];
	private int[] obstaclesOrder = {0,1,0,0,1,0,2,1,0,2,1,1,0,0,1,2,2,2,1,2,1,2,1,2,2,2,1,1,1,0,0,1,1,1,1,2,1,2,1,2};
	private AnimationTimer gameTimer;
	private int score;
	
	//constructor that recieves stage
	public JumpGame(Stage s) {
		stage=s;
		stage.setScene(scene);		
	}
	
	//starts game
	public void run() {
		scene.setOnKeyPressed(this);
		stage.show();
		player=new ImageView();
		player.setFitHeight(PLAYER_SIZE);
		player.setFitWidth(PLAYER_SIZE);
		for(int i=0; i<OBSTACLES_AMOUNT; i++) {
			if(obstaclesOrder[i]!=0) {
				o[i]=new Obstacle();
				o[i].setHeight(obstaclesOrder[i]);
				obstacle[i]=new ImageView("resources/obstacle0.png");
				obstacle[i].setFitHeight(OBSTACLE_SIZE);
				obstacle[i].setFitWidth(OBSTACLE_SIZE);
				obstacle[i].setLayoutX(SCENE_WIDTH+i*400);
				obstacle[i].setLayoutY(SCENE_HEIGHT*4/5-o[i].height*SCENE_HEIGHT/3);
				layout.getChildren().add(obstacle[i]);
			}
		}
		layout.getChildren().add(player);
		createGameLoop();
	}
	
	public void testRun() {
		scene.setOnKeyPressed(this);
		stage.show();
		player=new ImageView();
		player.setFitHeight(PLAYER_SIZE);
		player.setFitWidth(PLAYER_SIZE);
		for(int i=0; i<10; i++) {
			if(obstaclesOrder[i]!=0) {
				o[i]=new Obstacle();
				o[i].setHeight(obstaclesOrder[i]);
				obstacle[i]=new ImageView("resources/obstacle0.png");
				obstacle[i].setFitHeight(OBSTACLE_SIZE);
				obstacle[i].setFitWidth(OBSTACLE_SIZE);
				obstacle[i].setLayoutX(SCENE_WIDTH+i*400);
				obstacle[i].setLayoutY(SCENE_HEIGHT*4/5-o[i].height*SCENE_HEIGHT/3);
				layout.getChildren().add(obstacle[i]);
			}
		}
		layout.getChildren().add(player);
		createTestGameLoop();
	}
	
	//method to repeat steps during game
	private void createGameLoop() {
		gameTimer=new AnimationTimer() {
			@Override
			public void handle(long now) {				
				moveObstacles();
				movePlayer();
				pass();
				p.runAnimation();
			}
		};
		gameTimer.start();		
	}	
	private void createTestGameLoop() {
		gameTimer=new AnimationTimer() {
			@Override
			public void handle(long now) {				
				moveTestObstacles();
				movePlayer();
				testPass();
				p.runAnimation();
			}
		};
		gameTimer.start();		
	}
	
	//method to move player figure in gui
	public void movePlayer() {
		if(p.isJumping) {
			Image img=new Image("resources/jumping0.png");
			player.setImage(img);
			player.setLayoutX(SCENE_WIDTH/4);
			player.setLayoutY((SCENE_HEIGHT/3)-(SCENE_HEIGHT/6));
		}else if(p.isDucked) {
			Image img=new Image("resources/ducked0.png");
			player.setImage(img);
			player.setLayoutX(SCENE_WIDTH/4);
			player.setLayoutY(SCENE_HEIGHT/3);
		}else {
			if(p.running==0) {
				Image img=new Image("resources/running0.png");
				player.setImage(img);
			}else if(p.running==1) {
				Image img=new Image("resources/running1.png");
				player.setImage(img);
			}			
			player.setLayoutX(SCENE_WIDTH/4);
			player.setLayoutY(SCENE_HEIGHT/3);
			
		}
		
	}
	
	//method to move obstacles in gui
	public void moveObstacles() {
		for(int i=0; i<OBSTACLES_AMOUNT; i++) {
			if(obstaclesOrder[i]!=0) {
				obstacle[i].setLayoutX(obstacle[i].getLayoutX()-6);
				if(obstaclesOrder[i]==1) {
					obstacle[i].setRotate(obstacle[i].getRotate()-4);
				}else {
					obstacle[i].setRotate(obstacle[i].getRotate()+3);
				}
			}
		}
	}
	public void moveTestObstacles() {
		for(int i=0; i<10; i++) {
			if(obstaclesOrder[i]!=0) {
				obstacle[i].setLayoutX(obstacle[i].getLayoutX()-6);
				if(obstaclesOrder[i]==1) {
					obstacle[i].setRotate(obstacle[i].getRotate()-4);
				}else {
					obstacle[i].setRotate(obstacle[i].getRotate()+3);
				}
			}
		}
	}
	
	//method to monitor and count passing obstacles
	public void pass() {
		for(int i=0; i<OBSTACLES_AMOUNT;i++) {
			if(obstaclesOrder[i]!=0) {
				if(((o[i].height==1&&p.isJumping)||(o[i].height==2&&p.isDucked))&&Math.abs(player.getLayoutX()-obstacle[i].getLayoutX())<=3&&!o[i].passed) {
					o[i].passed=true;
					System.out.println("obstacle passed!");
					score=score+10;
					System.out.println("score: "+score);
				}
				if(((o[i].height==1&&!p.isJumping)||(o[i].height==2&&!p.isDucked))&&Math.abs(player.getLayoutX()-obstacle[i].getLayoutX())<=3&&!o[i].passed) {
					o[i].passed=true;
					o[i].hit=true;
					obstacle[i].setVisible(false);
					System.out.println("obstacle hit!");
					score=score-5;
					System.out.println("score: "+score);					
				}
			}
		}
		if(obstacle[OBSTACLES_AMOUNT-1].getLayoutX()<=0) {
			stage.close();
		}
	}
	public void testPass() {
		for(int i=0; i<10;i++) {
			if(obstaclesOrder[i]!=0) {
				if(((o[i].height==1&&p.isJumping)||(o[i].height==2&&p.isDucked))&&Math.abs(player.getLayoutX()-obstacle[i].getLayoutX())<=3&&!o[i].passed) {
					o[i].passed=true;
					System.out.println("obstacle passed!");
					score=score+10;
					System.out.println("score: "+score);
				}
				if(((o[i].height==1&&!p.isJumping)||(o[i].height==2&&!p.isDucked))&&Math.abs(player.getLayoutX()-obstacle[i].getLayoutX())<=3&&!o[i].passed) {
					o[i].passed=true;
					o[i].hit=true;
					obstacle[i].setVisible(false);
					System.out.println("obstacle hit!");
					score=score-5;
					System.out.println("score: "+score);					
				}
			
			}
		}
		if(obstacle[9].getLayoutX()<=0) {
			stage.close();
		}
	}
	
	//converts KeyEvent into player movement
	@Override
	public void handle(KeyEvent e){
		if(e.getCode()==KeyCode.UP){
			p.jump();
		}
		if(e.getCode()==KeyCode.DOWN){
			p.duck();
		}
	}

}
