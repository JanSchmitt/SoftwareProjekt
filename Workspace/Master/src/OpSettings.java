
	
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
	int difficulty;
	int gamemode;
	int time;
	String iniDifficulty;
	String iniGamemode;
	String iniTime;
	
	//start method
	@Override
	public void start(Stage primaryStage) {
		try {			
			//initialize latest values
			Initialization ini= new Initialization();
			ini.read(this);
			
			//create grid layout
			GridPane grid = new GridPane();
			grid.setPadding(new Insets(50,50,50,50));
			grid.setVgap(20);
			grid.setHgap(10);
			
			//control elements
			Label gmLabel = new Label("Spielmodus:");
			GridPane.setConstraints(gmLabel,0,0);
			
			ChoiceBox<String> gamemodeDD=new ChoiceBox<>();
			gamemodeDD.getItems().addAll("Entgegenwirken","Neutral","Verstärkend");		
			gamemodeDD.setValue(gmIni());
			GridPane.setConstraints(gamemodeDD, 1, 0);
			
			Label timeLabel=new Label("Zeit:");
			GridPane.setConstraints(timeLabel,0,1);
			
			TextField timeIn=new TextField();
			timeIn.setText(iniTime);
			GridPane.setConstraints(timeIn,1,1);
			
			Label diffLabel = new Label("Schwierigkeit:");
			GridPane.setConstraints(diffLabel,0,2);
			
			ChoiceBox<String> difficultyDD=new ChoiceBox<>();
			difficultyDD.getItems().addAll("Einfach","Normal","Schwer");		
			difficultyDD.setValue(diffIni());
			GridPane.setConstraints(difficultyDD,1,2);
			
			Button applyButton=new Button("Übernehmen");
			applyButton.setOnAction(e->{				
			getGamemode(gamemodeDD);
			getDifficulty(difficultyDD);
			if(getTime(timeIn)){
				ini.write(this);
				primaryStage.close();
				frage questions=new frage();
				try {
					questions.start(primaryStage);
				}catch(Exception f) {
					f.printStackTrace();
				}							
			};			
			
			});
			GridPane.setConstraints(applyButton,0,3);			
			
			//add scene with layout to stage
			grid.getChildren().addAll(gmLabel,timeLabel,gamemodeDD,timeIn,diffLabel,difficultyDD,applyButton);
			Scene scene = new Scene(grid,400,320);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Experimentator-Einstellungen");
			primaryStage.show();			
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	//method to convert gamemode from chiocebox into number
	private void getGamemode(ChoiceBox<String> cb) {
		switch(cb.getValue()) {
		case "Entgegenwirken": gamemode=1; break;
		case "Neutral": gamemode=0; break;
		case "Verstärkend": gamemode=2; break;
		}
		System.out.println("Spielmodus:"+gamemode);
	}
	
	//method to get selected time from textfield
	public boolean getTime(TextField tf) {
		try {
			time=Integer.parseInt(tf.getText());
			System.out.println("Zeit:"+time+"\n");
			return true;
		}catch(NumberFormatException e) {
			System.out.println("Falsche Eingabe! Bitte eine Nummer eingeben");
			return false;
		}		
	}
	
	public void getDifficulty(ChoiceBox<String> cb){
		switch(cb.getValue()) {
		case "Einfach": difficulty=0; break;
		case "Normal": difficulty=1; break;
		case "Schwer": difficulty=2; break;
		}
		System.out.println("Schwierigkeit"+difficulty);
	}
	
	public String gmIni(){
		switch(iniGamemode){
		case "1": return "Entgegenwirken";
		case "0": return "Neutral";
		case "2": return "Verstärkend";
		default: return "No gamemode initialized";
		}
	}
	
	public String diffIni(){
		switch(iniDifficulty){
		case "0": return "Einfach";
		case "1": return "Normal";
		case "2": return "Schwer";
		default: return "No difficulty initialized";
		}
	}
	
	public void main(String[] args) {
		launch(args);
	}
}
