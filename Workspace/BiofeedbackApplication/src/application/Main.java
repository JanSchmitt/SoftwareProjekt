package application;

import javafx.application.Application;
import application.OpSettings;
import javafx.stage.Stage;
import view.ViewManager;

public class Main  {
	
	public static void main(String args[]) {
		OpSettings opsettings = new OpSettings();
		opsettings.main(args);
	}
}
