package minispielMerken;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Random;

public class Merken extends Application implements EventHandler<ActionEvent> {

	Scene scene1, scene2, scene3;
	VBox box1, box2, box3;
	Label label1, label2, label3, label4, label5;
	Stage window;
	int number = 0;
	int zahl;
	int vorherigeZahl = 0;
	Button weiter1, weiter2, weiter3;
	TextField text;
	String message;
	int gamepoints = 100;
	

	public static void main(String[] args) {
		launch(args);
	}
	
	
	public int createRandomNumber() {
		Random rnd = new Random();
		int rand = 1 + rnd.nextInt(998);
		return rand;
	}

	public void gameScene() {
		vorherigeZahl = number;
		number = createRandomNumber();
		
		box3 = new VBox(20);
		box3.setSpacing(40);
		box3.styleProperty().set("-fx-background-image: url(/minispielMerken/1.jpg)");
		text = new TextField(message);
		text.setPrefSize(50, 50);
		text.setFont(new Font("Cambria", 30));
		label2 = new Label("Merken Sie sich diese Zahl: ");
		label5 = new Label("                        " + number);
		label2.setTextFill(Color.web("1968EB"));
		label2.setFont(new Font( "Cambria" , 41));
		label5.setTextFill(Color.web("1968EB"));
		label5.setFont(new Font( "Cambria" , 40));
		label5.setLayoutY(200);
		weiter3 = new Button("Weiter");
		weiter3.setPrefSize(100, 50);
		label3 = new Label("Und geben Sie hier die vorherige Zahl ein: ");
		label3.setTextFill(Color.web("010203"));
		label3.setFont(new Font( "Cambria" , 15));
		label4 = new Label("Points: " + gamepoints);
		label4.setTextFill(Color.web("1AC843"));
		label4.setFont(new Font( "Cambria" , 25));
		box3.getChildren().addAll(label2, label5,  label3, text, weiter3, label4);
		scene3 = new Scene(box3, 500, 500);
		weiter3.setOnAction(this);
		window.setScene(scene3);
		window.setTitle("Minispiel: Zahlen merken");
		weiter3.setFocusTraversable(false);
		
		/*weiter3.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ek) {
				if(ek.getCode() == KeyCode.ENTER) {
					weiter3.fire();
				}
			}
			
		});*/
		
		text.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.matches("\\d*")) {
				text.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		
	}

	public void check(String ausText) {
		zahl = Integer.parseInt(ausText);
		if (zahl == vorherigeZahl) {
			gamepoints += 100;
		} else {
			gamepoints -= 100;
		}
		gameScene();
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == weiter1) {

			Random rnd = new Random();
			int rand = 1 + rnd.nextInt(998);
			number = rand;

			box2 = new VBox(20);
			box2.styleProperty().set("-fx-background-image: url(/minispielMerken/1.jpg)");
			label2 = new Label("Merken Sie sich diese Zahl: ");
			label5 = new Label("                        " + number);
			label2.setTextFill(Color.web("1968EB"));
			label2.setFont(new Font( "Cambria" , 41));
			label5.setTextFill(Color.web("1968EB"));
			label5.setFont(new Font( "Cambria" , 40));
			weiter2 = new Button("Weiter");
			weiter2.setPrefSize(100, 50);
			box2.getChildren().addAll(label2, label5, weiter2);
			scene2 = new Scene(box2, 500, 500);
			window.setScene(scene2);
			weiter2.setOnAction(this);
			window.setTitle("Minispiel: Zahlen merken");
		}

		if (event.getSource() == weiter2) {
			gameScene();
		}
		
		if(event.getSource() == weiter3) {
			check(text.getText());
		}
	}

	@Override
	public void start(Stage primaryScene) throws Exception {
		window = primaryScene;

		// Explanation
		box1 = new VBox(20);
		box1.styleProperty().set("-fx-background-image: url(/minispielMerken/1.jpg)");
		label1 = new Label("How it works!");
		label1.setTextFill(Color.web("1968EB"));
		label1.setFont(new Font( "Cambria" , 40));
		Label label6 = new Label("Die Zahl vom vorherigen Screen wird abgefragt!");
		Label label7 = new Label("Gleichzeitig wird eine neue Zahl gezeigt, die dann");
		Label label8 = new Label("wiederum im nächsten Screen abgefragt wird.");
		label6.setTextFill(Color.web("010203"));
		label6.setFont(new Font( "Cambria" , 20));
		label7.setTextFill(Color.web("010203"));
		label7.setFont(new Font( "Cambria" , 20));
		label8.setTextFill(Color.web("010203"));
		label8.setFont(new Font( "Cambria" , 20));
		weiter1 = new Button("WEITER");
		weiter1.setPrefSize(100, 50);
		box1.getChildren().addAll(label1, label6, label7, label8,  weiter1);
		scene1 = new Scene(box1, 500, 500);
		weiter1.setOnAction(this);
		window.setScene(scene1);
		window.setTitle("Erklärung");
		window.show();
		
	}

}
