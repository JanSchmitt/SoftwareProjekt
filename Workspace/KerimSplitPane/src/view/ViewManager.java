package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

import model.MyButton;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;



public class ViewManager {
	/*
	private static final int HEIGHT = 768;
	private static final int WIDTH = 1024;
	private AnchorPane mainPane; //Container mit 5 Logic-Regions: Top, Bottom, Left, Center, Right
	private Scene mainScene;
	 //neues fenster

	private final static int NEW_BUTTON_START_X = 100;
	private final static int NEW_BUTTON_START_Y = 150;
	
	List<MyButton> menuButtons;
	
	//Timer soll 
	//zeit soll in textfield eingegeben werden
	private static final int minutes = 5;
	private static final Integer STARTTIME = 10;  //minutes * 60;  //
    private Timeline timeline;
    private Integer timeSeconds = STARTTIME;

    
    
	public ViewManager() {
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane,WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		createButtons();
		createBackground();
		createLogo();
	}
	*/
	
	private Stage mainStage;
    public ViewManager() throws Exception{
    	Parent root = FXMLLoader.load(getClass().getResource("/view/View.fxml"));
    	Scene scene = new Scene(root);
    	scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
    	
    	//GameViewManager gm = new GameViewManager();
    	mainStage = new Stage();
    	mainStage.setScene(scene);
    	mainStage.setTitle("Biofeedback Anwendung");
    	
    }
    
	public Stage getMainStage() {
		return mainStage;
	}
	
	
	
	/*
	private void addMenuButton(MyButton button) {
		button.setLayoutX(NEW_BUTTON_START_X);
		button.setLayoutY(NEW_BUTTON_START_Y + menuButtons.size() *100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
		
	}
	
	private void createButtons() {
		createStartButton2();
		createScoresButton();
		createHelpButton();
		createCreditsButton();
		createExitButton();
	}
	
	
	
	

	/*
	private void createStartButton2() {
		MyButton startButton = new MyButton("PLAY");
		addMenuButton(startButton);
		
		//Kein Focus auf Startbutton, sonst bei Return zum Startmenu als focused = true ausgewählt 
		//und man kann zur GameStage durch irgendeinen Tastendruck
		startButton.setFocusTraversable(false);  
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {  //Button event handler
			
			public void handle(ActionEvent event) {
				if (timeline != null) {
		            timeline.stop();
		     
		        }
		        GameViewManager gameManager = new GameViewManager();
				gameManager.createNewGame(mainStage); //geht in Fkt createNewGame(..) in Class GameViewManager

				timeSeconds = STARTTIME;
		 
		        // update timerLabel
		        timeline = new Timeline();
		        timeline.setCycleCount(Timeline.INDEFINITE);
		        timeline.getKeyFrames().add(
		                new KeyFrame(Duration.seconds(1),
		                  new EventHandler<ActionEvent>() {
		                    // KeyFrame event handler

							@Override
							public void handle(ActionEvent event) {
							    timeSeconds--;
		                        // update timerLabel
		                        if (timeSeconds <= 0) {
		                            timeline.stop();
		                            System.out.println("stop");
		                            
		                            gameManager.gameStage.close();
		                            gameManager.gameTimer.stop();
		                            
		                            gameManager.menuStage.show();
		                            
		                        }
		                    
							}
		                }));
		        
		        timeline.playFromStart();
		    }
		});
	}
	
	private void createScoresButton() {
		MyButton scoresButton = new MyButton("SCORES");
		addMenuButton(scoresButton);
	}
	
	private void createHelpButton() {
		MyButton helpButton = new MyButton("HELP");
		addMenuButton(helpButton);
	}
	
	private void createCreditsButton() {
		MyButton creditsButton = new MyButton("CREDITS");
		addMenuButton(creditsButton);
	}
	
	private void createExitButton() {
		MyButton exitButton = new MyButton("EXIT");
		addMenuButton(exitButton);
	}
	
	private void createBackground() {
		Image backgroundImage = new Image("view/resources/purple.png", 256,256,false,true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
		
	}
	
	private void createLogo() {
		ImageView logo = new ImageView("view/resources/th_ab_logo.png");
		logo.setX(750);
		logo.setY(25);
		
		logo.setOnMouseEntered(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				logo.setEffect(new DropShadow()); //new DropShadow()
			}
		});
		
		logo.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				logo.setEffect(null);
			}	
		});
		mainPane.getChildren().add(logo);
		
	}
	
	*/
}
