package spiel;


import com.sun.prism.paint.Color;

import javafx.application.Application; 
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;






public class frage extends Application implements EventHandler<ActionEvent> {
	// FX Bestandteile
	CheckBox box1,box2,box3;
	Button start, weiter1, weiter2, weiter3,spielstart;
	Scene scene1, scene2, scene3,scene4 ,scene5;
	Label label1,label2, label3,label4,label5;
	Label nameLabel, ageLabel, gewLabel, hfLabel, FVLabel, StressLabel, RuheLabel;
	Stage window;
	TextField alter, gewicht, name;
	GridPane layout3,layout1,layout2,layout4,layout5;
	
	// Globale Variablen
	int Alter, Gewicht, sportlichkeit;
	String Name, Sportlich;
	int hfmax;
	int Frequenzvariabilität;
	int Mittelfrequenz = 0;
	int Stresszustand;
	int Ruhezustand = 72;
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Startbildschirm");
		window.setOnCloseRequest(e -> window.close() );
		
		// Szene 1 Startbildschirm
		label1 = new Label("Zum Starten Klicken");
		start = new Button("Click Here");
		layout1 = new GridPane();
		layout1.setPadding(new Insets(20,20,20,20));
		layout1.setVgap(10);
		layout1.setHgap(10);
		GridPane.setConstraints(label1,1,0);
		GridPane.setConstraints(start, 1,1);
		layout1.getChildren().addAll(label1,start);
		scene1 = new Scene(layout1,300, 250);
		
		
		// Szene 2 Name 
		weiter1 = new Button("Click to cintinue");
		layout2 = new GridPane();
		label2 = new Label("Name");
		name = new TextField();
		layout2.setPadding(new Insets(20,20,20,20));
		layout2.setVgap(10);
		layout2.setHgap(10);
		GridPane.setConstraints(label2, 0, 0);
		GridPane.setConstraints(name, 1, 0);
		GridPane.setConstraints(weiter1, 0,1);
		layout2.getChildren().addAll(label2,name,weiter1);
		scene2 = new Scene(layout2,300,250);
		
		
		//Szene 3 alter und gewicht
		weiter2 = new Button("Click to cintinue");
		layout3 = new GridPane();
		label3 = new Label("Geben Sie Ihr Alter an");
		label4 = new Label("Geben Sie Ihr Gewicht an");
		alter = new TextField();
		gewicht = new TextField();
		layout3.setPadding(new Insets(20,20,20,20));
		layout3.setVgap(10);
		layout3.setHgap(10);
		GridPane.setConstraints(label3, 0, 0);
		GridPane.setConstraints(alter, 1, 0);
		GridPane.setConstraints(label4, 0, 1);
		GridPane.setConstraints(gewicht, 1, 1);
		GridPane.setConstraints(weiter2, 0,2);
		layout3.getChildren().addAll(label3, alter,gewicht,label4, weiter2);
		scene3 = new Scene(layout3, 300, 250);
		
		
		// Szene 4 Sportlichkeit 
		weiter3 = new Button("Click to continue");
		box1 = new CheckBox("Sportlich");
		box2 = new CheckBox("Normal");
		box3 = new CheckBox("Unsportlich");
		layout4 = new GridPane();
		label5 = new Label("Sind Sie sportlich?");
		layout4.setPadding(new Insets(20,20,20,20));
		layout4.setVgap(10);
		layout4.setHgap(10);
		GridPane.setConstraints(label5, 0, 0);
		GridPane.setConstraints(box1, 1, 1);
		GridPane.setConstraints(box2, 1, 2);
		GridPane.setConstraints(box3, 1, 3);
		GridPane.setConstraints(weiter3, 0,4);
		layout4.getChildren().addAll(weiter3,box1,box2,box3,label5);
		scene4 = new Scene(layout4, 300,250);
		

		
		
		
		//Buttons und Stage
		weiter1.setOnAction(this);
		weiter2.setOnAction(this);
		weiter3.setOnAction(this);
		start.setOnAction(this);
		window.setScene(scene1);
		window.show();
	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource()==start) {
			
			window.setScene(scene2);
			window.setTitle("Userdaten Name");
			
		}else if(event.getSource()== weiter1) {
			
			if(isString(name.getText()) == true){
				window.setScene(scene3);
				window.setTitle("Userdaten Alter und Gewicht");
				System.out.println("User heisst " + name.getText());
				Name = name.getText();
			}else {
				window.setScene(scene2);
			}
			
		}else if(event.getSource() == weiter2) {
			
			if((isInt(alter.getText())== true)&&(isInt(gewicht.getText())== true)) {
				System.out.println("User ist " + alter.getText() + " Jahre alt");
				Alter = Integer.parseInt(alter.getText());
				System.out.println("User ist " + gewicht.getText() + " Kilo schwer");
				Gewicht = Integer.parseInt(gewicht.getText());
				window.setScene(scene4);
				window.setTitle("Userdaten Sportlichkeit");
			}else {
				window.setScene(scene3);
			}
			
		}else if(event.getSource() == weiter3) {
			if((handleOptions(box1)==1)^(handleOptions(box2)==1)^(handleOptions(box3)==1)) {
				if(box1.isSelected()== true){
					System.out.println("User ist sportlich");
					Sportlich = "Sportlich";
					sportlichkeit = 1;
				}else if(box2.isSelected()== true) {
					System.out.println("User ist normal");
					Sportlich = "Normal";
					sportlichkeit = 2;
				}else if (box3.isSelected()== true) {
					System.out.println("User ist unsportlich");
					Sportlich = "Unsportlich";
					sportlichkeit = 0;
				}
				berechne(sportlichkeit,Alter,Gewicht);
				setScene5();
			}else {
				window.setScene(scene4);
			}
		}else if(event.getSource()== spielstart) {
			System.out.println("Spiel wird jetzt gestartet");
			window.close();
		}
		
		
	}
	

	private void setScene5() {
		// Ausgabe der Werte in Szene 5
				spielstart = new Button("Hier Spiel starten");
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
				scene5 = new Scene(layout5, 400,400);
				window.setScene(scene5);
				window.setTitle("Spielstats");
				spielstart.setOnAction(this);
	}
	
	
	
	
	private int handleOptions(CheckBox b) {
		if(b.isSelected() == true) {
			return 1;
		}else {
			return 0;
		}
	}
	
	private boolean isInt(String message) {
		try {
			Integer.parseInt(message);
			return true;
		}catch(NumberFormatException e) {
			System.out.println("Falsche Eingabe! Bitte eine Nummer eingeben");
			return false;
		}
	}
	
	
	
	private boolean isString(String message) {
			String regex = "^[a-z A-Z]+$";
			if(message.matches(regex)== true) {
				
				return true;
			}else {
				System.out.println("Keine oder falsche Eingabe");
				return false;
			}
	}
	private void berechne(int s, int a, int g) {
		hfmax = 214 - (int)(0.5*a) - (int)(0.11*g);
		System.out.println("Ihre Maximalherzfrequenz liegt bei " + hfmax);
		if(s == 1) {
			Frequenzvariabilität = (30 + 2*a)/2;
			Ruhezustand = Ruhezustand - Frequenzvariabilität;
		}else if (s== 0) {
			Frequenzvariabilität = (30 - Alter/2);
			Ruhezustand = Ruhezustand + Frequenzvariabilität;
		}else {
			Frequenzvariabilität = 25;
			Ruhezustand = Ruhezustand + 0;
		}
		Stresszustand = (hfmax + 130 - Ruhezustand)/2;
		System.out.println("Stresszustand wird angenommen bei einer Herzfrequenz von " + Stresszustand);
		System.out.println("Ihre Frequenzvariabilität beträgt " + Frequenzvariabilität);
	}

}
