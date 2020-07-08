package minispiele.jumpAndDuck;

import javafx.scene.layout.AnchorPane;

public class Main {
	
	private AnchorPane minispielPane;
	
	public Main(AnchorPane mP) {
		this.minispielPane = mP;
	}

	public void start() {
		JumpGame game = new JumpGame(minispielPane);
		game.run();
	}
}
