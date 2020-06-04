package view;

import sound.MusicLoader;

public class FeedbackManager {
	private GameViewManager game;
	private MusicLoader music;
	private int mode=0;
	boolean changeMode=false;
	
	public FeedbackManager(GameViewManager g) {
		game=g;
		music=new MusicLoader();
		music.load("src/sound/resources/funk warte.wav");
		music.play();
		
		
	}
	
	//sets mode from outside
	public void setMode(int m) {
		if(mode==m) {
			return;
		}else if(m==0||m==1||m==2) {
			changeMode=true;
			mode=m;
			if(m==0){
				music.load("src/sound/resources/funk warte.wav");
			}else if(m==1) {			
				music.load("src/sound/resources/thief in the night.wav");
			}else if(m==2) {
				music.load("src/sound/resources/rage.wav");
			}
			music.play();
			game.changeColors(mode);
			game.createBackground();
			changeMode=false;
		}		
	}
	
	//returns mode
	public int getmode() {
		return mode;
	}
	
	public boolean getChanging() {
		return changeMode;
	}
}
