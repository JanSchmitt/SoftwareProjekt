package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;


public class OpSettings extends Application {
	int gamemode;
	int time;
	
	@Override
	public void start(Stage primaryStage) {
		try {			
			GridPane grid = new GridPane();
			grid.setPadding(new Insets(50,50,50,50));
			grid.setVgap(20);
			grid.setHgap(10);
			
			Label gmLabel = new Label("Gamemode:");
			GridPane.setConstraints(gmLabel,0,0);
			
			ChoiceBox<String> gamemodeDD=new ChoiceBox<>();
			gamemodeDD.getItems().addAll("counteracting","neutral","reinforcing");		
			gamemodeDD.setValue("counteracting");
			GridPane.setConstraints(gamemodeDD, 1, 0);
			
			Label timeLabel=new Label("Time:");
			GridPane.setConstraints(timeLabel,0,1);
			
			ChoiceBox<Integer> timeDD=new ChoiceBox<>();
			timeDD.getItems().addAll(1,2,5,10,15,20,30);
			timeDD.setValue(10);
			GridPane.setConstraints(timeDD,1,1);
			
			Button applyButton=new Button("Apply");
			applyButton.setOnAction(e->{				
			getGamemode(gamemodeDD);
			getTime(timeDD);
			primaryStage.close();
			});
			GridPane.setConstraints(applyButton,0,2);
			
			grid.getChildren().addAll(gmLabel,timeLabel,gamemodeDD,timeDD,applyButton);
			Scene scene = new Scene(grid,400,250);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void getGamemode(ChoiceBox<String> cb) {
		switch(cb.getValue()) {
		case "counteracting": gamemode=1; break;
		case "neutral": gamemode=0; break;
		case "reinforcing": gamemode=2; break;
		}
		System.out.println("Gamemode:"+gamemode);
	}
	
	private void getTime(ChoiceBox<Integer> cb) {
		time=cb.getValue();
		System.out.println("Time:"+time+"\n");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
