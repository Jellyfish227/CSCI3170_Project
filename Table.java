import java.sql.Connection;

public abstract class Table {
    private static Connection conn;
    private String sourceDir;
    private int columns;


    public abstract void createTable();
    public abstract void loadTable();
}
