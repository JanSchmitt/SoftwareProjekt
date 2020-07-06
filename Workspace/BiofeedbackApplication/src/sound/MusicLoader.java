package sound;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
//import javax.sound.sampled.FloatControl;


public class MusicLoader {
	// Variables
	public static File sound;
	public static float value;
	Clip clip;
	boolean playing;
	
	//constructor for class musicloader
	public MusicLoader() {
		playing=false;
		value=0;
	}
	
	// loads file into the sound variable
	public void load(String file) {
		sound = new File(file);
	}
	
	// plays clip previously loaded into sound variable
	// if a clip is already playing it will be stopped and the new clip will start
	public void play(){
		if(playing==true) {
			stop();
		}
		try {
			playing=true;
			clip=AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// stops the clip audio 
	public void stop(){
		clip.stop();
		playing=false;
	}
}
