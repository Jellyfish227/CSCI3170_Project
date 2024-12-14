import java.sql.*;

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

    public void queryTable() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        // Print column headers
        System.out.print("|");
        for (int i = 1; i <= columnCount; i++) {
            System.out.printf(" %s |", rsmd.getColumnName(i));
        }
        System.out.println();

        // Print data rows with proper formatting
        while (rs.next()) {
            System.out.print("|");
            for (int i = 1; i <= columnCount; i++) {
                String value = rs.getString(i);
                System.out.printf(" %s |", value != null ? value : "");
            }
            System.out.println();
        }
    }

    public void deleteTable() throws SQLException{
        conn.createStatement().executeUpdate("BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + tableName + " CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -942 THEN RAISE; END IF; END;");
    }

    public void loadTable() throws SQLException {
        System.out.println("Loading " + tableName + "...");
        conn.createStatement().executeUpdate("DELETE FROM " + tableName);
    }

    public String getTableName() {
        return tableName;
    }
}
