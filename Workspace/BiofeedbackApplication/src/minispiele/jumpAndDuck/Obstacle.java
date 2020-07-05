package minispiele.jumpAndDuck;

public class Obstacle {
	int height;
	boolean passed=false;
	boolean hit=false;
	
	public void setHeight(int value) {
		if(value==0||value==1||value==2) {
			height=value;
		}		
	}
}
