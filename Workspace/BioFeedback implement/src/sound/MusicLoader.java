package sound;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
public class MusicLoader {
	
	public static File sound;
	public static float value;
	Clip clip;
	boolean playing;
	
	public MusicLoader() {
		playing=false;
		value=0;
	}
	public void load(String file) {
		sound = new File(file);
	}
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
	public void stop(){
		clip.stop();
		playing=false;
	}
}
