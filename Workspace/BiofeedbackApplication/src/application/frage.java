package application;

//import com.sun.prism.paint.Color;

import javafx.application.Application; 

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
/*import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;*/
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
/*import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;*/
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.Testing;
import org.ini4j.Wini;

public class frage extends Application implements EventHandler<ActionEvent> {
	// FX Parts
	CheckBox box1,box3;
	Button spielstart, weiter1;
	Scene scene2, scene5;
	Label label1,label3,label4,label5;
	Label nameLabel, ageLabel, gewLabel, hfLabel, FVLabel, StressLabel, RuheLabel;
	Stage window;
	TextField alter, gewicht;
	GridPane layout3,layout1,layout2,layout4,layout5;
	Initialization ini = new Initialization();
	
	//Variables
	int Alter, Gewicht, sportlichkeit;
	String Name, Sportlich;
	int hfmax;
	int Frequenzvariabilität;
	int Mittelfrequenz = 0;
	int Stresszustand;
	int Ruhezustand = 72;
	Testing test;
	
	// main method
	public void main(String[] args) {
		launch(args);
	}

	// starts the window in which operator can ask and collect information about the player
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Startbildschirm");
		window.setOnCloseRequest(e -> window.close() );
		
		//Fragebogen
		weiter1 = new Button("Weiter");
		layout2 = new GridPane();
		
		label3 = new Label("Geben Sie Ihr Alter an");
		alter = new TextField();
		label4 = new Label("Geben Sie Ihr Gewicht an");		
		gewicht = new TextField();
		label5 = new Label("Sind Sie sportlich?");
		box1 = new CheckBox("Sportlich");
		box3 = new CheckBox("Unsportlich");
		
		layout2.setPadding(new Insets(20,20,20,20));
		layout2.setVgap(10);
		layout2.setHgap(10);
		
		GridPane.setConstraints(label3, 0, 2);
		GridPane.setConstraints(alter, 1, 2);
		GridPane.setConstraints(label4, 0, 3);
		GridPane.setConstraints(gewicht, 1, 3);
		GridPane.setConstraints(label5, 0, 5);
		GridPane.setConstraints(box1, 1, 5);
		GridPane.setConstraints(box3, 1, 6);
		GridPane.setConstraints(weiter1, 0, 7);
		
		layout2.getChildren().addAll(weiter1,label3, alter,gewicht,label4,box1,box3,label5);
		scene2 = new Scene(layout2,400,320);		
		
		//Button und Stage
		weiter1.setOnAction(this);
		window.setScene(scene2);
		window.show();
	}

	// handles input
	// can only continue if every needed value is collected and every data necessary has been calculated and saved
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource()== weiter1) {
			
			if((isInt(alter.getText())== true)&&(isInt(gewicht.getText())== true)&&((handleOptions(box1)==1)^(handleOptions(box3)==1))){
				
				System.out.println("Nutzer ist " + alter.getText() + " Jahre alt");
				Alter = Integer.parseInt(alter.getText());
				System.out.println("Nutzer ist " + gewicht.getText() + " Kilo schwer");
				Gewicht = Integer.parseInt(gewicht.getText());
				if(box1.isSelected()== true){
					System.out.println("Nutzer ist sportlich");
					Sportlich = "Sportlich";
					sportlichkeit = 1;				
				}else if (box3.isSelected()== true) {
					System.out.println("Nutzer ist unsportlich");
					Sportlich = "Unsportlich";
					sportlichkeit = 0;
				}
				berechne(sportlichkeit,Alter,Gewicht);
				setScene5();
			}else {
				window.setScene(scene2);
			}
		}else if(event.getSource()== spielstart) {
			System.out.println("Testphase wird jetzt gestartet");
			test = new Testing();
			test.createHRSTest(window, hfmax);
		}		
	}
	
	
	// sets up scene in which all collected stats will be shown
	private void setScene5() {
		// Ausgabe der Werte in Szene 5
				spielstart = new Button("Hier Test starten");
				nameLabel = new Label("Name: " + Name);
				ageLabel = new Label("Alter: "+ Alter);
				gewLabel = new Label("Gewicht: " + Gewicht);
				hfLabel = new Label("Maximale Herzfrequenz: "+ hfmax);
				FVLabel = new Label("Frequenzvariabilität: " + Frequenzvariabilität);
				StressLabel = new Label("Stresszustand angenommen ab: " +Stresszustand);
				RuheLabel = new Label("Ruhepuls: "+Ruhezustand);
				layout5 = new GridPane();
				layout5.setPadding(new Insets(20,20,20,20));
				layout5.setVgap(10);
				layout5.setHgap(10);
				GridPane.setConstraints(nameLabel, 0, 0);
				GridPane.setConstraints(ageLabel, 0, 1);
				GridPane.setConstraints(gewLabel, 0, 2);
				GridPane.setConstraints(hfLabel, 0, 3);
				GridPane.setConstraints(FVLabel, 0,4);
				GridPane.setConstraints(StressLabel, 0,5);
				GridPane.setConstraints(RuheLabel, 0,6);
				GridPane.setConstraints(spielstart, 0,7);
				layout5.getChildren().addAll(spielstart,nameLabel, ageLabel,gewLabel,hfLabel,FVLabel,StressLabel, RuheLabel);
				scene5 = new Scene(layout5, 400,320);
				window.setScene(scene5);
				window.setTitle("Spielerstats");
				spielstart.setOnAction(this);
	}
	
	
	
	// checks selected checkbox input
	private int handleOptions(CheckBox b) {
		if(b.isSelected() == true) {
			return 1;
		}else {
			return 0;
		}
	}
	
	// checks if given input is actually of type integer
	private boolean isInt(String message) {
		try {
			Integer.parseInt(message);
			return true;
		}catch(NumberFormatException e) {
			System.out.println("Falsche Eingabe! Bitte eine Nummer eingeben");
			return false;
		}
	}
	
	

	
	// calculates important values and sets boarders for biofeedback
	// also saves values to config
	private void berechne(int s, int a, int g) {
		hfmax = 214 - (int)(0.5*a) - (int)(0.11*g);
		System.out.println("Ihre Maximalherzfrequenz liegt bei " + hfmax);
		if(s == 1) {
			Frequenzvariabilität = (30 - a/4);
		}else if (s== 0) {
			Frequenzvariabilität = (30 - a/3);
		}else {
			Frequenzvariabilität = 25;
		}
		Stresszustand = (Ruhezustand + Frequenzvariabilität);
		System.out.println("Stresszustand wird angenommen bei einer Herzfrequenz von " + Stresszustand);
		System.out.println("Ihre Frequenzvariabilität beträgt " + Frequenzvariabilität);
		
		ini.updateGrenze(Stresszustand);
		ini.updateAlter(Alter);
		ini.updateGewicht(Gewicht);
		ini.updateSportlichkeit(Sportlich);
	}
	
}
