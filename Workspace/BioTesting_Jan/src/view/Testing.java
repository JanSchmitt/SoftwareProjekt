package view;

import HRS.Port;
import application.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import minispiele.Reaktionstest;

public class Testing {

	VBox boxHRS, boxTest, boxMinispiele, boxResult,v;
	HBox h;
	Port hrs;
	Label labelTest, labelErgebnis, labelMinispiele, labelPoints, labelTime;
	Scene sceneTest, sceneErgebnis, sceneMinispiele, sceneRTT, sceneAfter;
	Button buttonStartTestHRS, buttonWeiter, buttonMinispieleWeiter, buttonNext;
	int result, points, time;
	Stage window;
	Reaktionstest rtt;

	public void createHRSTest(Stage window) {
		boxHRS = new VBox(20);
		hrs = new Port();
		buttonStartTestHRS = new Button("Drücken um Test des HRS zu starten");
		buttonStartTestHRS.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent ae) {
				// result = hrs.test();
				result = 70;
				setResultScene(result, window);
			}
		});

		labelTest = new Label("Kurzer Test des Herzratensensors!");
		boxHRS.getChildren().addAll(labelTest, buttonStartTestHRS);
		sceneTest = new Scene(boxHRS, 400, 400);
		window.setScene(sceneTest);
		window.show();
	}

	public void setResultScene(int result, Stage window) {
		buttonWeiter = new Button("Weiter zum Test der Minispiele");
		buttonWeiter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeb) {
				createMinispielTest(window);
			}
		});
		labelErgebnis = new Label("" + result);
		boxResult = new VBox(20);
		boxResult.getChildren().addAll(labelErgebnis, buttonWeiter);
		sceneErgebnis = new Scene(boxResult, 400, 400);
		window.setScene(sceneErgebnis);
	}

	public void createMinispielTest(Stage window) {
		boxMinispiele = new VBox(20);
		labelMinispiele = new Label("Hier kommt der Minispiel Test hin");
		buttonMinispieleWeiter = new Button("Zum Spielstart");
		buttonMinispieleWeiter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeMST) {
				reaktionstesttest(window);
			}
		});
		boxMinispiele.getChildren().addAll(labelMinispiele, buttonMinispieleWeiter);
		sceneMinispiele = new Scene(boxMinispiele, 400, 400);
		window.setScene(sceneMinispiele);
	}
	
	public void reaktionstesttest(Stage window) {
		h = new HBox();
		sceneRTT = new Scene(h, 400,400);
		rtt = new Reaktionstest();
		rtt.test(h, sceneRTT, window);
		sceneRTT.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent keRTT) {
				if(keRTT.getCode() == KeyCode.ENTER) {
					rtt.testReact();
					points = rtt.getPoints();
					time = 1000 - points;
					sceneAfterRTT(window);
				}
			}
			
		});	
	}
	
	public void sceneAfterRTT(Stage window) {
		v = new VBox(20);
		labelPoints = new Label("Punkte im Test: " + points);
		labelTime = new Label("Zeit im Test: " + time);
		buttonNext = new Button("Zum Hauptmenü");
		buttonNext.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeRTT) {
				window.close();
				Game game = new Game();
				try {
					game.start(window);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		v.getChildren().addAll(labelPoints, labelTime, buttonNext);
		sceneAfter = new Scene(v, 400, 400);
		window.setScene(sceneAfter);
	}
		/*points = rtt.getPoints();
		while(rtt.isEnded() == false) {
			System.out.println("Waiting for test end");
		}
		if(rtt.isEnded() == true) {
			v = new VBox();
			buttonNext = new Button("Zum Hauptmenü");
			buttonNext.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent aeRTT) {
					window.close();
					Game game = new Game();
					try {
						game.start(window);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			});
			labelPoints = new Label("Testpoints  " + points);
			v.getChildren().addAll(labelPoints, buttonNext);
			sceneRTT = new Scene(v, 400,400);
			window.setScene(sceneRTT);
		}*/

}
