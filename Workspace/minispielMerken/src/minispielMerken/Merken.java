package minispielMerken;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Random;

public class Merken extends Application implements EventHandler<ActionEvent> {

	Scene scene1, scene2, scene3;
	VBox box1, box2, box3;
	Label label1, label2 , label3;
	Stage window;
	int number = 0;
	int vorherigeZahl = 0;
	Button weiter1, weiter2;
	TextField text;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() == weiter1) {
			Random rnd = new Random();
			int rand = 1 + rnd.nextInt(998);
			number = rand;
			
			box2 = new VBox(20);
			label2 = new Label("Merken Sie sich diese Zahl: " + number);
			weiter2 = new Button("Weiter");
			box2.getChildren().addAll(label2, weiter2);
			scene2 = new Scene(box2, 400, 400);
			window.setScene(scene2);
			weiter2.setOnAction(this);
			window.setTitle("Minispiel: Zahlen merken");
		}
		
		if(event.getSource() == weiter2) {
			vorherigeZahl = number;
			Random rnd = new Random();
			int rand = 1 + rnd.nextInt(998);
			number = rand;
			
			box3 = new VBox(20);
			label2 = new Label("Merken Sie sich diese Zahl: " + number);
			weiter2 = new Button("Weiter");
			label3 = new Label("Geben Sie hier die vorherige Zahl ein: ");
			text = new TextField();
			box3.getChildren().addAll(label2, label3, text , weiter2);
			scene3 = new Scene(box3, 400, 400);
			window.setScene(scene3);
			weiter2.setOnAction(this);
			window.setTitle("Minispiel: Zahlen merken");
		}
	}

	@Override
	public void start(Stage primaryScene) throws Exception {
		window = primaryScene;
		
		//Explanation
		box1 = new VBox(20);
		label1 = new Label("How it works!");
		weiter1 = new Button("WEITER");
		box1.getChildren().addAll(label1, weiter1);
		scene1 = new Scene(box1, 400, 400);	
		weiter1.setOnAction(this);
		window.setScene(scene1);
		window.setTitle("Erklärung");
		window.show();	
	}

}
