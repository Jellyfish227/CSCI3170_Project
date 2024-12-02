import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    // Database credentials for CUHK CSE Oracle server
    private static final String DB_URL = "jdbc:oracle:thin:@db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk";
    private static final String USERNAME = "h086";
    private static final String PASSWORD = "DilAkJay";

    private Connection conn;

    public Connection getConnection() {
        if (conn == null) {
            try {
                // Register JDBC driver
                Class.forName("oracle.jdbc.driver.OracleDriver");
                
                // Establish connection
                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                System.out.println("Database connected successfully!");
                
            } catch (ClassNotFoundException e) {
                System.out.println("Error: Oracle JDBC Driver not found.");
                return null;
            } catch (SQLException e) {
                System.out.println("Error: Connection failed! Check output console");
                e.printStackTrace();
                return null;
            }
        }
        return conn;
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.out.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
} 