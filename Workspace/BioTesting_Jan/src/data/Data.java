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
		tableCount = "TEST" + ID;
		stmt.executeUpdate("CREATE TABLE tableCount (id integer,  times integer, heartRate integer, score integer,  mode char, stress char)");
	}
	
	public void saveDataInTable(int id, int times, int hr, int score, String mode, String stress) throws SQLException {
		tableCount = "TEST" + id;
		stmt.executeUpdate("INSERT INTO tableCount VALUES (id, times, hr, score, mode, stress)");
	}

}
