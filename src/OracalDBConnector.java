import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracalDBConnector implements Connectable {
    // Database credentials for CUHK CSE Oracle server
    private static final String DB_URL = "jdbc:oracle:thin:@db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk";
    private static final String USERNAME = "h086";
    private static final String PASSWORD = "DilAkJay";

    private Connection conn = null;

    public OracalDBConnector() throws Exception{
        setConnection();
    }

    @Override
    public void setConnection() throws Exception {
        if (conn == null) {
            try {
                // Register JDBC driver
                Class.forName("oracle.jdbc.driver.OracleDriver");

                // Establish connection
                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                System.out.println("Database connected successfully!");

            } catch (ClassNotFoundException e) {
                System.out.println("Error: Oracle JDBC Driver not found.");
                throw e;
            } catch (SQLException e) {
                System.out.println("Error: Connection failed! Check output console");
                throw e;
            }
        }
    }
    @Override
    public Connection getConnection() {
        return this.conn;
    }

    @Override
    public void close() throws Exception {
        if (conn != null) {
            this.conn.close();
            System.out.println("Database connection closed.");
        }
    }
}
