package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static final String URL ="jdbc:postgresql://localhost:5433/crypto_wallet";
    private static final String USER="postgres";
    private static final String PASSWORD ="admin";
    
    
    private static Connection connection;
    
    public static Connection getConnection() throws SQLException{
    	
    	if(connection == null ||connection.isClosed()) {
    		try {
    			Class.forName("org.postgresql.Driver");
    			connection=DriverManager.getConnection(URL,USER,PASSWORD);
    			System.out.println("connection établie");
    		}
    		catch(ClassNotFoundException e) {
    			
    			throw new SQLException("driver not found",e);
    		}
    	}
    	return connection;
    }
    
}
