package application;

import java.io.File;
import java.io.IOException;

import org.ini4j.*;

public class Initialization {

	//Variablen
	String ms1;
	String ms2;
	String ms3;
	String ms4;
	String ms5;
	String ms6;
	String msTime1;
	String msTime2;
	String msTime3;
	String msTime4;
	String msTime5;
	String msTime6;
	String id;
	String hrsOn;
	String grenze;
	int ID;
	int mode;
	String ruhepuls;

	public void read(OpSettings settings) {
		try {
			Wini ini = new Wini(new File("src/application/settings.ini"));
			settings.iniID = ini.get("OpSettings", "id");
			settings.iniTime = ini.get("OpSettings", "time");
			settings.iniGamemode = ini.get("OpSettings", "gamemode");
			settings.iniDifficulty = ini.get("OpSettings", "difficulty");
			settings.iniPointRange = ini.get("OpSettings", "pointRange");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void write(OpSettings settings) {
		try {
			Wini ini = new Wini(new File("src/application/settings.ini"));
			ini.put("OpSettings", "id", settings.id);
			ini.put("OpSettings", "time", settings.time);
			ini.put("OpSettings", "gamemode", settings.gamemode);
			ini.put("OpSettings", "difficulty", settings.difficulty);
			ini.put("OpSettings", "pointRange", settings.pointRange);
			ini.put("OpSettings", "minispiel1", "maze");
			ini.put("OpSettings", "minispiel2", "zahlenMerken");
			ini.put("OpSettings", "minispiel3", "reaktion");
			ini.put("OpSettings", "minispiel4", "jump");
			ini.put("OpSettings", "minispiel5", "catch");
			ini.put("OpSettings", "minispiel6", "pong");
			ini.put("OpSettings", "msZeit1", "40");
			ini.put("OpSettings", "msZeit2", "140");
			ini.put("OpSettings", "msZeit3", "240");
			ini.put("OpSettings", "msZeit4", "340");
			ini.put("OpSettings", "msZeit5", "440");
			ini.put("OpSettings", "msZeit6", "540");
			ini.put("OpSettings", "Ruhepuls", "70");
			ini.put("OpSettings", "Port", "1");
			ini.put("OpSettings", "Grenze" , "135");
			ini.put("OpSettings", "hrsOn", "1");
			ini.put("OpSettings", "Alter", "20");
			ini.put("OpSettings", "Gewicht", "70");
			ini.put("OpSettings", "Sportlichkeit", "unsportlich");
			ini.put("OpSettings", "Systemzeit", "0");
			ms1 = ini.get("OpSettings", "minispiel1");
			ms2 = ini.get("OpSettings", "minispiel2");
			ms3 = ini.get("OpSettings", "minispiel3");
			ms4 = ini.get("OpSettings", "minispiel4");
			ms5 = ini.get("OpSettings", "minispiel5");
			ms6 = ini.get("OpSettings", "minispiel6");
			msTime1 = ini.get("OpSettings", "msZeit1");
			msTime2 = ini.get("OpSettings", "msZeit2");
			msTime3 = ini.get("OpSettings", "msZeit3");
			msTime4 = ini.get("OpSettings", "msZeit4");
			msTime5 = ini.get("OpSettings", "msZeit5");
			msTime6 = ini.get("OpSettings", "msZeit6");
			ruhepuls = ini.get("OpSettings", "Ruhepuls");
			grenze = ini.get("OpSettings", "Grenze");
			hrsOn = ini.get("OpSettings", "hrsOn");
			ini.store();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//returns the mode saved in the init file
	public String getMode() {
		Wini ini;
		String modus = "";
		try {
			 ini = new Wini(new File("src/application/settings.ini"));
			/*if(ini.get("OpSettings", "gamemode") == "0") {
				mode = 0;
			}
			if(ini.get("OpSettings", "gamemode") == "1") {
				mode = 1;
			}
			if(ini.get("OpSettings", "gamemode") == "2") {
				mode = 2;
			}*/
			 modus =  ini.get("OpSettings", "gamemode");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modus;
	}

	//returns the current played game saved in the init file
	public String getGame(int zahl) {
		String game = "Hauptspiel";
		try {
			Wini init = new Wini(new File("src/application/settings.ini"));
			ms1 = init.get("OpSettings", "minispiel1");
			ms2 = init.get("OpSettings", "minispiel2");
			ms3 = init.get("OpSettings", "minispiel3");
			ms4 = init.get("OpSettings", "minispiel4");
			ms5 = init.get("OpSettings", "minispiel5");
			ms6 = init.get("OpSettings", "minispiel6");
			if(zahl == 1) {
				game =  ms1;
			}
			if(zahl == 2) {
				game =  ms2;
			}
			if(zahl == 3) {
				game = ms3;
			}
			if(zahl == 4) {
				game = ms4;
			}
			if(zahl == 5) {
				game = ms5;
			}
			if(zahl == 6) {
				game = ms6;
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return game;
	}
	
	//returns the time when the minigames will start, which are set in the init file
	public int getMinigameTime(int zahl) {
		int time = 0;
		try {
			Wini init = new Wini(new File("src/application/settings.ini"));
			msTime1 = init.get("OpSettings", "msZeit1");
			msTime2 = init.get("OpSettings", "msZeit2");
			msTime3 = init.get("OpSettings", "msZeit3");
			msTime4 = init.get("OpSettings", "msZeit4");
			msTime5 = init.get("OpSettings", "msZeit5");
			msTime6 = init.get("OpSettings", "msZeit6");
	
			if(zahl == 1) {
				time =  Integer.parseInt(msTime1);
			}
			if(zahl == 2) {
				time =  Integer.parseInt(msTime2);
			}
			if(zahl == 3) {
				time =  Integer.parseInt(msTime3);
			}
			if(zahl == 4) {
				time =  Integer.parseInt(msTime4);
			}
			if(zahl == 5) {
				time =  Integer.parseInt(msTime5);
			}
			if(zahl == 6) {
				time =  Integer.parseInt(msTime6);
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	//returns the ID of the current player which is set in the init file
	public String getID() {
		Wini iniID;
		try {
			iniID = new Wini(new File("src/application/settings.ini"));
			id = iniID.get("OpSettings" , "id");
		} catch (IOException e) {
			e.printStackTrace();
		}
		ID = Integer.parseInt(id);
		return id;
	}
	
	//updates the variable Ruhepuls in the init file (when it's checked on set up)
	public void updateRuhepuls(int rp) {
		Wini iniRP;
		try {
			iniRP = new Wini(new File("src/application/settings.ini"));
			iniRP.put("OpSettings" , "Ruhepuls", rp);
			iniRP.store();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//updates the used Port for the whole Game when it's first declared by user input 
	public void updatePort(int p) {
		Wini iniPort;
		try {
			iniPort = new Wini(new File("src/application/settings.ini"));
			iniPort.put("OpSettings" , "Port", p);
			iniPort.store();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//returns the used Port for later usage
	public int getPort() {
		Wini iniP;
		String port = "0";
		try {
			iniP = new Wini(new File("src/application/settings.ini"));
			port = iniP.get("OpSettings" , "Port");
		} catch (IOException e) {
			e.printStackTrace();
		}
		int p = Integer.parseInt(port);
		return p;
	}
	
	//returns the variable Ruhepuls from the init file
	public String getRP() {
		Wini iniPuls;
		String puls = "82";
		try {
			iniPuls = new Wini(new File("src/application/settings.ini"));
			puls = iniPuls.get("OpSettings" , "Ruhepuls");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return puls;
	}
	
	//updates the border after it was calculated
	public void updateGrenze(int grenze) {
		Wini iniPort;
		try {
			iniPort = new Wini(new File("src/application/settings.ini"));
			iniPort.put("OpSettings" , "Grenze", grenze);
			iniPort.store();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//returns the variable grenze from the init file
	public int getGrenze() {
		Wini iniG;
		String grenzeS = "130";
		try {
			iniG = new Wini(new File("src/application/settings.ini"));
			grenzeS = iniG.get("OpSettings" , "Grenze");
		} catch (IOException e) {
			e.printStackTrace();
		}
		int grenzeI = Integer.parseInt(grenzeS);
		return grenzeI;
	}
	
	//updates whether the heart rate sensor is in usage or not
	public void updateHRSUSage(int i) {
		Wini iniPort;
		try {
			iniPort = new Wini(new File("src/application/settings.ini"));
			iniPort.put("OpSettings" , "hrsOn", i);
			iniPort.store();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//returns whether the heart rate sensor is in usage or not
	public int getHRSUSage() {
		Wini iniPort;
		String hrsu = " ";
		int hrsUsage = 0;
		try {
			iniPort = new Wini(new File("src/application/settings.ini"));
			hrsu = iniPort.get("OpSettings" , "hrsOn");
			hrsUsage = Integer.parseInt(hrsu);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hrsUsage;
	}
	
	//updates the age of the user in the init file
	public void updateAlter(int alter) {
		Wini iniS;
		try {
			iniS = new Wini(new File("src/application/settings.ini"));
			iniS.put("OpSettings" , "Alter", alter);
			iniS.store();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//returns the age from the init file
	public int getAlter() {
		Wini iniS;
		String alterS = "130";
		try {
			iniS = new Wini(new File("src/application/settings.ini"));
			alterS = iniS.get("OpSettings" , "Alter");
		} catch (IOException e) {
			e.printStackTrace();
		}
		int alterI = Integer.parseInt(alterS);
		return alterI;
	}
	
	//updates the weight of the user in the init file
	public void updateGewicht(int gewicht) {
		Wini iniS;
		try {
			iniS = new Wini(new File("src/application/settings.ini"));
			iniS.put("OpSettings" , "Gewicht", gewicht);
			iniS.store();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//returns the weight of the user from the init file
	public int getGewicht() {
		Wini iniS;
		String gewichtS = "130";
		try {
			iniS = new Wini(new File("src/application/settings.ini"));
			gewichtS = iniS.get("OpSettings" , "Gewicht");
		} catch (IOException e) {
			e.printStackTrace();
		}
		int gewichtI = Integer.parseInt(gewichtS);
		return gewichtI;
	}
	
	//updates the fitness level of the user in the init file
	public void updateSportlichkeit(String sportlich) {
		Wini iniS;
		try {
			iniS = new Wini(new File("src/application/settings.ini"));
			iniS.put("OpSettings" , "Sportlichkeit", sportlich);
			iniS.store();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//returns the fitness level from the init file
	public String getSportlichkeit() {
		Wini iniS;
		String sportlichS = "";
		try {
			iniS = new Wini(new File("src/application/settings.ini"));
			sportlichS = iniS.get("OpSettings" , "Sportlichkeit");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sportlichS;
	}
	
	public void updateGamemode(int gamemode) {
		try {
			Wini iniG = new Wini(new File("src/application/settings.ini"));
			iniG.put("OpSettings" , "gamemode", gamemode);
			iniG.store();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
