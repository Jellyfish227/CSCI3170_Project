import java.sql.Connection;

public interface Connectable extends AutoCloseable {
    void setConnection();
    Connection getConnection();
}
