package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

	private static String URL = ("jdbc:postgresql://localhost:5433/crypto_wallet");
	private static String USER = "postgres";
	private static String PASSWORD = "admin";
	
	private static Connection connection;
	public static Connection getConnection() throws SQLException{
		if(connection == null || connection.isClosed()) {
			try {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				
			}catch(ClassNotFoundException e) {
				
				throw new SQLException("driver not found",e);
			}
		}
		return connection;
	}

}
