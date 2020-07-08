package minispiele.jumpAndDuck;

public class Obstacle {
	//variables
	int height;
	boolean passed=false;
	boolean hit=false;
	
	//sets only allowed height values as height
	public void setHeight(int value) {
		if(value==0||value==1||value==2) {
			height=value;
		}		
	}
}
