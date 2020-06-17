package zMTest;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class zmTest extends Application{

	static ZahlenMerken zm;
	static AnchorPane ap;
	Stage win;
	static Scene s;
	static Label label;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage win) throws Exception {
		ap = new AnchorPane();
		label = new Label("Test");
		ap.getChildren().add(label);
		s = new Scene(ap, 400, 400);
		zm = new ZahlenMerken(ap);
		zm.start();
		win = new Stage();
		win.setScene(s);
		win.show();
		
		s.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if(e.getCode() == KeyCode.DIGIT0 || e.getCode() == KeyCode.NUMPAD0) {
					zm.writeNumber(0);
				}
				if(e.getCode() == KeyCode.DIGIT1 || e.getCode() == KeyCode.NUMPAD1) {
					zm.writeNumber(1);
				}
				if(e.getCode() == KeyCode.DIGIT2 || e.getCode() == KeyCode.NUMPAD2) {
					zm.writeNumber(2);
				}
				if(e.getCode() == KeyCode.DIGIT3 || e.getCode() == KeyCode.NUMPAD3) {
					zm.writeNumber(3);
				}
				if(e.getCode() == KeyCode.DIGIT4 || e.getCode() == KeyCode.NUMPAD4) {
					zm.writeNumber(4);
				}
				if(e.getCode() == KeyCode.DIGIT5 || e.getCode() == KeyCode.NUMPAD5) {
					zm.writeNumber(5);
				}
				if(e.getCode() == KeyCode.DIGIT6 || e.getCode() == KeyCode.NUMPAD6) {
					zm.writeNumber(6);
				}
				if(e.getCode() == KeyCode.DIGIT7 || e.getCode() == KeyCode.NUMPAD7) {
					zm.writeNumber(7);
				}
				if(e.getCode() == KeyCode.DIGIT8 || e.getCode() == KeyCode.NUMPAD8) {
					zm.writeNumber(8);
				}
				if(e.getCode() == KeyCode.DIGIT9 || e.getCode() == KeyCode.NUMPAD9) {
					zm.writeNumber(9);
				}		
			}
			
		});
	}

}
