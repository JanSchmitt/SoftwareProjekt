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
		//String sql = "CREATE TABLE Test3 (id INTEGER,  timestamp INTEGER,  score INTEGER,  mode String)";
		stmt.executeUpdate("CREATE TABLE Test4 (id integer,  times integer,  score integer,  mode char)");

		//stmt.executeUpdate("INSERT INTO SPIELER VALUES (4, 'Felix', 123545)");
		
		/*ResultSet rs = stmt.executeQuery("SELECT SCORE FROM SPIELER");
		
		while(rs.next()) {
			int sc = rs.getInt(1);
			System.out.println(sc);
		}*/
		
	}

}
