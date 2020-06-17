package config;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	
	static Properties prop = new Properties();
	
	String ms6;
	String ms5;
	String ms4;
	String ms3;
	String ms2;
	String ms1;
	String timeMS1;
	String timeMS2;
	String timeMS3;
	String timeMS4;
	String timeMS5;
	String timeMS6;
	
	
	public void getMS6() {
		
	}
	
	public static void readPropertiesFiles() {
		try (FileReader readerConfig = new FileReader("dataConfig")){
			
			InputStream input = new FileInputStream("C:\\Users\\schmi\\OneDrive\\Desktop\\SoftwareProjekt\\Workspace\\BioTesting_Jan\\src\\config\\dataConfig");
			
			prop.load(input);
			
			String laufzeit = prop.getProperty("laufzeit");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			/*int lz = Integer.parseInt(laufzeit);
			System.out.println("Die Standardlaufzeit des Spiels betr‰gt " + lz + " Minuten");
			
			String gamemode = prop.getProperty("gamemode");
			if(gamemode.equals("ohne")) {
				System.out.println("Testgruppe: keine aktivierenden oder deaktivierenden Maﬂnahmen!");
			} 
			
			String punkteHS = prop.getProperty("punkteHS");
			int punkteHauptspiel = Integer.parseInt(punkteHS);
			System.out.println("Der Spieler erh‰lt " + punkteHauptspiel + " Punkte pro Sekunde");
			
			String schwierigkeit = prop.getProperty("schwierigkeit");
			System.out.println("Die Standardschwierigkeit ist " + schwierigkeit + 
					". Diese kann sich w‰hrend des Spiels aufgrund der Herzrate ver‰ndern");	
						
			String minispiel1 = prop.getProperty("minispiel1");
			String minispiel2 = prop.getProperty("minispiel2");
			String minispiel3 = prop.getProperty("minispiel3");
			String minispiel4 = prop.getProperty("minispiel4");
			String minispiel5 = prop.getProperty("minispiel5");
			String minispiel6 = prop.getProperty("minispiel6");
			
			System.out.println("Die Reihenfolge der Spiele ist: ");
			System.out.println(minispiel1);
			System.out.println(minispiel2);
			System.out.println(minispiel3);
			System.out.println(minispiel4);
			System.out.println(minispiel5);
			System.out.println(minispiel6);*/

	}
	
	public static void writePropertiesFiles() {
		//add if necessary
	}

}
