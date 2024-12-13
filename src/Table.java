import java.sql.Connection;

public abstract class Table {
    private static Connection conn;
    private static String sourceDir;
    private int columns;

    public static void setSourceDir(String sourceDir) {
        Table.sourceDir = sourceDir;
    }

    public abstract void createTable();
    public abstract void loadTable();
    public abstract String[] queryTable(String query);
}
