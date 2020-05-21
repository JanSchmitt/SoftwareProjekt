import java.io.File;
import org.ini4j.*;

public class Initialization {
	public void read(OpSettings settings){
		try{
			Wini ini=new Wini(new File("files/settings.ini"));
			settings.iniTime=ini.get("OpSettings", "time");
			settings.iniGamemode=ini.get("OpSettings", "gamemode");
			settings.iniDifficulty=ini.get("OpSettings", "difficulty");
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	public void write(OpSettings settings) {
		try{
			Wini ini=new Wini(new File("files/settings.ini"));
			ini.put("OpSettings", "time", settings.time);
			ini.put("OpSettings", "gamemode", settings.gamemode);
			ini.put("OpSettings", "difficulty", settings.difficulty);
			ini.store();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
}
