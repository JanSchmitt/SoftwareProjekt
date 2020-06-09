package data;

import java.sql.*;
import java.lang.String;

public class Data {
	
	static Statement stmt;
	String tableCount;

	public static void main(String[] args) throws Exception {
		
		Class.forName("org.firebirdsql.jdbc.FBDriver");
		
		Connection connection = DriverManager.getConnection(
				"jdbc:firebirdsql://localhost:3050/C:/Datenbank/Test/TEST.FDB",
				"SYSDBA", "masterkey");
		stmt = connection.createStatement();	
	}
	
	public void createTableForTest(int ID) throws Exception {
		tableCount = "TEST" + ID;
		stmt.executeUpdate("CREATE TABLE tableCount (id integer,  times integer,  score integer,  mode char)");
	}
	
	public void saveDataInTable() {
		
	}

}
