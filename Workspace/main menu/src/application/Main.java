package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;


public class Main extends Application{
	Stage stage;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override	
	public void start(Stage primaryStage) throws Exception{
		stage=primaryStage;
		stage.setTitle("GUI-Fenster");
		
		GridPane grid=new GridPane();
		grid.setPadding(new Insets(50,50,50,50));
		grid.setVgap(15);
		grid.setHgap(10);
		
		Label name=new Label("Name:");
		GridPane.setConstraints(name, 0, 0);		
		TextField nameIn=new TextField();
		nameIn.setPromptText("Name");
		GridPane.setConstraints(nameIn, 1, 0);
		
		Label age=new Label("Age:");
		GridPane.setConstraints(age, 0, 1);		
		TextField ageIn=new TextField();
		ageIn.setPromptText("Age");
		GridPane.setConstraints(ageIn, 1, 1);
		
		Button startButton=new Button("Start");
		GridPane.setConstraints(startButton, 0, 2);
		
		Button exitButton=new Button("Exit");
		GridPane.setConstraints(exitButton, 0, 3);
		exitButton.getStyleClass().add("button-red");
		
		Button controlsButton=new Button("Controls");
		startButton.setOnAction(e -> {
			System.out.println("Start pressed.");
			isInt(ageIn);
		});
		GridPane.setConstraints(controlsButton, 0, 4);
		
		grid.getChildren().addAll(name, nameIn, age, ageIn, startButton, exitButton, controlsButton);
		
		HBox topMenu=new HBox();		
		topMenu.getChildren().addAll();
		
				
		BorderPane borderPane=new BorderPane();
		borderPane.setTop(topMenu);
		borderPane.setLeft(grid);
		
		Scene scene = new Scene(borderPane, 1280, 720);
		scene.getStylesheets().add("coolStyle.css");
		stage.setScene(scene);
		stage.show();
	}
	
	private boolean isInt(TextField input){
		try {
			int age = Integer.parseInt(input.getText());
			System.out.println(age);
			return true;
		}
		catch(NumberFormatException e){
			System.out.println("No valid number.");
			return false;
		}
	}

}