import java.sql.Connection;

public abstract class Table {
    private static Connection conn;
    private String sourceDir;
    private int columns;

    public void setConnection(Connection conn) {
        Table.conn = conn;
    };
    public abstract void createTable();
    public abstract void loadTable();
}
