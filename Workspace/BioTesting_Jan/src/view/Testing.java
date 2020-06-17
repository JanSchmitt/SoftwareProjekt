package view;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.ini4j.Wini;

import HRS.Port;
import application.Game;
import application.Initialization;
import data.Data;
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
	Port hrs = new Port();
	Data db;
	Initialization ini = new Initialization();
	Label labelTest, labelErgebnis, labelMinispiele, labelPoints, labelTime;
	Scene sceneTest, sceneErgebnis, sceneMinispiele, sceneRTT, sceneAfter;
	Button buttonStartTestHRS, buttonWeiter, buttonMinispieleWeiter, buttonNext, buttonOhneTest;
	int result, points, time, resultTest;
	Stage window;
	Reaktionstest rtt;

	public void createHRSTest(Stage window, int hfmax) {
		boxHRS = new VBox(20);
		//hrs = new Port();
		buttonStartTestHRS = new Button("Drücken um Test des HRS zu starten");
		buttonStartTestHRS.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent ae) {
				hrs.test();
				resultTest = hrs.getResult();
				ini.updateRuhepuls(resultTest);
				/*try {
					db.createTableForTest(ini.getID());					//DB
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				setResultScene(resultTest, window);
			}
		});

		buttonOhneTest = new Button("Ohne HRS fortfahren");
		buttonOhneTest.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg) {
				// TODO Auto-generated method stub
				result  = 70;
				ini.updateRuhepuls(result);
				/*try {
					db.createTableForTest(ini.getID());					//DB
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				setResultScene(result, window);
			}
			
		});
		
		labelTest = new Label("Kurzer Test des Herzratensensors!");
		boxHRS.getChildren().addAll(labelTest, buttonStartTestHRS, buttonOhneTest);
		sceneTest = new Scene(boxHRS, 400, 400);
		window.setScene(sceneTest);
		window.show();
		
		try {
			Wini init = new Wini(new File("src/application/settings.ini"));
			init.put("OpSettings", "Grenze" , hfmax);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setResultScene(int result, Stage window) {
		buttonWeiter = new Button("Weiter zum Test der Minispiele");
		buttonWeiter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent aeb) {
				createMinispielTest(window);
				//hrs.start();
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
					/*try {												//DB
						db.saveDataInTable(ini.getID(), 0, hrs.getHeartRate(), points, "test", "test");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
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
				//hrs.start();
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
