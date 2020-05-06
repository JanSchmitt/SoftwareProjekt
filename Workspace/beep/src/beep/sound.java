package beep;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class sound {

	public static void main(String[] args) {
		File Beep = new File("Beep.wav");
		playMusic(Beep);
	}
	
	public static void playMusic(File sound) {
		try {
		    Clip clip = AudioSystem.getClip();
		    clip.open(AudioSystem.getAudioInputStream(sound));
		    clip.start();
		    
		    Thread.sleep(clip.getMicrosecondLength()/1000);
		}catch(Exception e) {
			System.out.println("Fehler");
		}
	}
}
