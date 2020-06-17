package application;

public class Ball {
	
	//Movement properties
	private static final int SPEED=5;
	private int vX=SPEED;
	private int vY=SPEED;
	
	//change the direction
	public void moveUp() {
		vY=-SPEED;
	}
	
	public void moveDown() {
		vY=SPEED;
	}
	
	public void moveLeft() {
		vX=-SPEED;
	}
	
	public void moveRight() {
		vX=SPEED;
	}
	
	public void stop(){
		vX=0;
		vY=0;
	}
	
	//return movement properties
	public int getVX() {
		return vX;
	}
	
	public int getVY() {
		return vY;
	}
	
	public int getSpeed() {
		return SPEED;
	}
}
