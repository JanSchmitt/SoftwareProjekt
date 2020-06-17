package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class OpSettings extends Application {
	String id;
	int pointRange;
	int difficulty;
	int gamemode;
	int time;

	String iniID;
	String iniPointRange;
	String iniDifficulty;
	String iniGamemode;
	String iniTime;
	Initialization ini = new Initialization();

	// start method
	@Override
	public void start(Stage primaryStage) {
		try {
			// initialize latest values
			// Initialization ini= new Initialization();
			// ini.read(this);

			// create grid layout
			GridPane grid = new GridPane();
			grid.setPadding(new Insets(50, 50, 50, 50));
			grid.setVgap(20);
			grid.setHgap(10);

			// control elements
			Label gmLabel = new Label("Spielmodus:");
			GridPane.setConstraints(gmLabel, 0, 0);

			ChoiceBox<String> gamemodeDD = new ChoiceBox<>();
			gamemodeDD.getItems().addAll("Entgegenwirken", "Neutral", "Verstärkend");
			gamemodeDD.setValue(gmIni());
			GridPane.setConstraints(gamemodeDD, 1, 0);

			Label timeLabel = new Label("Zeit:");
			GridPane.setConstraints(timeLabel, 0, 1);

			TextField timeIn = new TextField();
			timeIn.setText(iniTime);
			GridPane.setConstraints(timeIn, 1, 1);

			Label diffLabel = new Label("Schwierigkeit:");
			GridPane.setConstraints(diffLabel, 0, 2);

			ChoiceBox<String> difficultyDD = new ChoiceBox<>();
			difficultyDD.getItems().addAll("Einfach", "Normal", "Schwer");
			difficultyDD.setValue(diffIni());
			GridPane.setConstraints(difficultyDD, 1, 2);

			Label idLabel = new Label("ID: ");
			GridPane.setConstraints(idLabel, 0, 3);

			// Label pointRangeLabel=new Label("Punktebereich:");
			// GridPane.setConstraints(pointRangeLabel,0,3);

			// TextField pointRangeIn =new TextField();
			// pointRangeIn.setText(iniPointRange);
			// GridPane.setConstraints(pointRangeIn,1,3);

			TextField idField = new TextField();
			idField.setText(iniID);
			GridPane.setConstraints(idField, 1, 3);

			Button applyButton = new Button("Übernehmen");
			applyButton.setOnAction(e -> {
				getGamemode(gamemodeDD);
				getDifficulty(difficultyDD);

				if (getTime(timeIn) && getID(idField)/* &&getPointRange(pointRangeIn) */) {
					ini.write(this);
					if (getTime(timeIn) && getID(idField)) {
						// ini.write(this);
						primaryStage.close();
						frage questions = new frage();
						try {
							questions.start(primaryStage);
						} catch (Exception f) {
							f.printStackTrace();
						}
					}
				}
			});
			GridPane.setConstraints(applyButton, 0, 4);

			// add scene with layout to stage
			grid.getChildren().addAll(gmLabel, timeLabel, gamemodeDD, timeIn, diffLabel, difficultyDD, idLabel, idField,
					applyButton);
			Scene scene = new Scene(grid, 400, 320);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Experimentator-Einstellungen");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// method to convert gamemode from chiocebox into number
	private void getGamemode(ChoiceBox<String> cb) {
		switch (cb.getValue()) {
		case "Entgegenwirken":
			gamemode = 1; // break;
		case "Neutral":
			gamemode = 0; // break;
		case "Verstärkend":
			gamemode = 2; // break;
		}
		System.out.println("Spielmodus: " + gamemode);
	}

	// method to get selected time from textfield
	public boolean getTime(TextField tf) {
		try {
			time = Integer.parseInt(tf.getText());
			System.out.println("Zeit: " + time);
			return true;
		} catch (NumberFormatException e) {
			System.out.println("Falsche Eingabe! Bitte eine Nummer eingeben");
			return false;
		}
	}

	// method to set ID
	public boolean getID(TextField tf) {
		try {
			iniID = tf.getText();
			id = iniID;
			return true;
		} catch (NumberFormatException e) {
			System.out.println("Falsche Eingabe! Bitte eine Nummer eingeben");
			return false;
		}
	}

	// method to get selected point range from textfield
	/*
	 * public boolean getPointRange(TextField tf) { try {
	 * pointRange=Integer.parseInt(tf.getText());
	 * System.out.println("Punktebereich: "+pointRange); return true;
	 * }catch(NumberFormatException e) {
	 * System.out.println("Falsche Eingabe! Bitte eine Nummer eingeben"); return
	 * false; } }
	 */

	// method to convert difficulty from chiocebox into number
	public void getDifficulty(ChoiceBox<String> cb) {
		switch (cb.getValue()) {
		case "Einfach":
			difficulty = 0;
			break;
		case "Normal":
			difficulty = 1;
			break;
		case "Schwer":
			difficulty = 2;
			break;
		}
		System.out.println("Schwierigkeit: " + difficulty);
	}

	// method to convert gamemode index into a word
	public String gmIni() {
		switch (iniGamemode) {
		case "1":
			return "Entgegenwirken";
		case "0":
			return "Neutral";
		case "2":
			return "Verstärkend";
		default:
			return "No gamemode initialized";
		}
	}

	// method to convert dificulty index into a word
	public String diffIni() {
		switch (iniDifficulty) {
		case "0":
			return "Einfach";
		case "1":
			return "Normal";
		case "2":
			return "Schwer";
		default:
			return "No difficulty initialized";
		}
	}

	public void main(String[] args) {
		launch(args);
	}
}
