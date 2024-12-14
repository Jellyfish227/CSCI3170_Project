import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class Table {
    protected static Connection conn;
    private static String sourceDir;

    public static void setSourceDir(String sourceDir) {
        Table.sourceDir = sourceDir;
    }

    protected static String getSouceDir() {
        return sourceDir;
    }

    public abstract void createTable() throws SQLException;
    public abstract void deleteTable() throws SQLException;
    public abstract void loadTable() throws SQLException, IOException;
    public abstract String[] queryTable(String query);
}
