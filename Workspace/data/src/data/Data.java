package data;

import java.sql.*;
import java.lang.String;

public class Data {

	public static void main(String[] args) throws Exception {
		
		Class.forName("org.firebirdsql.jdbc.FBDriver");
		
		Connection connection = DriverManager.getConnection(
				"jdbc:firebirdsql://localhost:3050/C:/Datenbank/Test/TEST.FDB",
				"SYSDBA", "masterkey");
		Statement stmt = connection.createStatement();

		stmt.executeUpdate("INSERT INTO SPIELER VALUES (3, 'Tim', 3)");
		
		ResultSet rs = stmt.executeQuery("SELECT SCORE FROM SPIELER");
		
		while(rs.next()) {
			int sc = rs.getInt(1);
			System.out.println(sc);
		}
		
	}

}
