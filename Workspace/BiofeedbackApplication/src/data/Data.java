package data;

import java.sql.*;
import java.lang.String;

public class Data {
	
	static Statement stmt;
	String tableCount;
	
	public Data()  {
		try {
			Class.forName("org.firebirdsql.jdbc.FBDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		Connection connection;
		try {
			connection = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:/Datenbank/Test/TEST.FDB",
					"SYSDBA", "masterkey");
			stmt = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/*public static void main(String[] args) throws Exception {
		
		Class.forName("org.firebirdsql.jdbc.FBDriver");
		
		Connection connection = DriverManager.getConnection(
				"jdbc:firebirdsql://localhost:3050/C:/Datenbank/Test/TEST.FDB",
				"SYSDBA", "masterkey");
		stmt = connection.createStatement();	
	}*/
	
	public void createTableForTest(int ID) throws Exception {
		String id = String.valueOf(ID);
		tableCount = "TEST" + id;
		stmt.executeUpdate("CREATE TABLE "+tableCount+"(id integer, sysTime char, alter integer, gewicht integer, sport char,  times integer, heartRate integer, score integer,  mode char, stress char, game char)");
	}
	
	public void saveDataInTable(int ID, String systime, int alter, int gewicht, String sport, int timestamp, int hr, int score, String modus, String stresslevel, String game) throws SQLException {
		String id = String.valueOf(ID);
		tableCount = "TEST" + id;
		stmt.executeUpdate("INSERT INTO "+tableCount+" VALUES ("+ID+", "+systime+", "+alter+", "+gewicht+", "+sport+", "+timestamp+", "+hr+", "+score+", "+modus+", "+stresslevel+", "+game+")");
	}

}
