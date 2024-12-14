import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class Table {
    protected static Connection conn;
    private static String sourceDir;

    private String tableName;

    public Table(String tableName) {
        this.tableName = tableName;
    }

    public static void setSourceDir(String sourceDir) {
        Table.sourceDir = sourceDir;
    }

    protected static String getSourceDir() {
        return sourceDir;
    }

    public abstract void createTable() throws SQLException;
    public abstract String[] queryTable(String query);

    public void deleteTable() throws SQLException{
        conn.createStatement().executeUpdate("BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + tableName + " CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -942 THEN RAISE; END IF; END;");
    }

    public void loadTable() throws SQLException, IOException {
        System.out.println("Loading " + tableName + "...");
        conn.createStatement().executeUpdate("DELETE FROM " + tableName);
    }

    public String getTableName() {
        return tableName;
    }
}
