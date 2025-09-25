package app;

import utils.DataBaseConnection;
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DataBaseConnection.getConnection();
            if (conn != null) {
                System.out.println("✅ Connexion PostgreSQL OK !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
