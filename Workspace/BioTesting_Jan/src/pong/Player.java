package pong;

public class Player {
	private int moving=0;
	
	public void moveRight(){
		moving=2;
	}
	
	public void moveLeft() {
		moving=1;
	}
	
	public void stop() {
		moving=0;
	}
	
	public int getMovement() {
		return moving;
	}
}
