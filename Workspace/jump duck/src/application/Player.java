package application;

import java.util.Timer;
import java.util.TimerTask;

//class Player that controls the players figure
public class Player {
	boolean isJumping=false;
	boolean isDucked=false;
	int running=0;
	
	//method to handle player jump
	public void jump() {
		if(!isJumping&&!isDucked) {
			isDucked=false;
			isJumping=true;
			Timer timer=new Timer();
			timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
			    isJumping=false;
			  }
			}, 300);
			
		}		
	}
	
	//method to handle ducking player
	public void duck() {
		if(!isDucked&&!isJumping) {			
			isJumping=false;
			isDucked=true;
			Timer timer=new Timer();
			timer.schedule(new TimerTask() {
				  @Override
				  public void run() {
				    isDucked=false;
				  }
				}, 300);
		}
	}
	public void runAnimation(){
		if(!isDucked&&!isJumping) {
			if(running==0) {
				Timer timer=new Timer();
				timer.schedule(new TimerTask() {
				  @Override
				  public void run() {
				    running=1;
				  }
				}, 150);
			}else if(running==1) {
				Timer timer=new Timer();
				timer.schedule(new TimerTask() {
				  @Override
				  public void run() {
				    running=0;
				  }
				}, 150);
			}
		}
	}
}
