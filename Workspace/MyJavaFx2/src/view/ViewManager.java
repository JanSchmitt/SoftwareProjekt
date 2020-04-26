package view;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;
import model.MyButton;


public class ViewManager {

	private static final int HEIGHT = 768;
	private static final int WIDTH = 1024;
	private AnchorPane mainPane; //Container mit 5 Logic-Regions: Top, Bottom, Left, Center, Right
	private Scene mainScene;
	private Stage mainStage; //neues fenster
	
	private final static int NEW_BUTTON_START_X = 100;
	private final static int NEW_BUTTON_START_Y = 150;
	
	List<MyButton> menuButtons;
	
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
	
	public Stage getMainStage() {
		return mainStage;
	}
	
	private void addMenuButton(MyButton button) {
		button.setLayoutX(NEW_BUTTON_START_X);
		button.setLayoutY(NEW_BUTTON_START_Y + menuButtons.size() *100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}
	
	private void createButtons() {
		createStartButton();
		createScoresButton();
		createHelpButton();
		createCreditsButton();
		createExitButton();
	}
	
	private void createStartButton() {
		MyButton startButton = new MyButton("PLAY");
		addMenuButton(startButton);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				GameViewManager gameManager = new GameViewManager();
				gameManager.createNewGame(mainStage);
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
}
