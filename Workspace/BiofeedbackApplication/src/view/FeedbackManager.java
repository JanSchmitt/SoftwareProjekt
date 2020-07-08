package view;

import sound.MusicLoader;

public class FeedbackManager {
	private GameViewManager game;
	private MusicLoader music;
	int mode;
	boolean changing=false;
	
	//constructor with GameViewManager input
	public FeedbackManager(GameViewManager g) {
		game=g;
		music=new MusicLoader();
		music.load("src/sound/resources/funk warte.wav");
		music.play();			
	}
	
	//sets mode from outside
	public void setMode(int m) {
		/*if(mode == m) {
			return;
		}else if(m==0||m==1||m==2) {
			changing=true;
			mode=m;
			if(m==0){
				music.load("src/sound/resources/funk warte.wav");
			}else if(m==1) {			
				music.load("src/sound/resources/Quiet Place.wav");
			}else if(m==2) {
				music.load("src/sound/resources/rage.wav");
			}
			music.play();
			game.changeColors(mode);
			game.changeText(mode);
			game.createBackground();
			changing=false;
		}	*/	
		if ( m == 0) {
			changing=true;
			mode = 0;
			music.load("src/sound/resources/funk warte.wav");
			music.play();
			game.changeColors(0);
			game.changeText(0);
			game.createBackground();
			changing=false;
		} else if ( m == 1) {
			changing=true;
			mode = 1;
			music.load("src/sound/resources/Quiet Place.wav");
			music.play();
			game.changeColors(1);
			game.changeText(1);
			game.createBackground();
			changing=false;
		} else if ( m == 2) {
			changing=true;
			mode = 2;
			music.load("src/sound/resources/rage.wav");
			music.play();
			game.changeColors(2);
			game.changeText(2);
			game.createBackground();
			changing=false;
		}
	}
	
	//returns mode
	public int getmode() {
		return mode;
	}
	
	//answers if mode is currently changing
	public boolean getChanging() {
		return changing;
	}
	
	//calls stop method in MusicLoader
	public void stopMusic() {
		music.stop();
	}
	
	//calls play method in MusicPlayer
	public void playMusic() {
		music.play();
	}
	
	//returns variable music
	public MusicLoader getML() {
		return music;
	}
}
