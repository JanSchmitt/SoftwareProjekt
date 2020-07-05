package application;

import javafx.application.Application;
import application.OpSettings;
import javafx.stage.Stage;
import view.ViewManager;

public class Main /* extends Application */ {
	/*
	 * @Override public void start(Stage primaryStage) throws Exception{ try {
	 * ViewManager manager = new ViewManager(); primaryStage =
	 * manager.getMainStage(); primaryStage.show();
	 * 
	 * } catch(Exception e) { e.printStackTrace(); } }
	 * 
	 * public static void main(String[] args) { launch(args); }
	 */
	public static void main(String args[]) {
		OpSettings opsettings = new OpSettings();
		opsettings.main(args);
	}
}
