import java.sql.Connection;
import java.sql.SQLException;

public interface Connectable extends AutoCloseable {
    void setConnection() throws SQLException, ClassNotFoundException;
    Connection getConnection();
}
