import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SalespersonTable extends Table {
    public static final int COLUMNS = 5;
    private static String tableIdentifier = "salesperson";

    public SalespersonTable(String tableName) {
        super(tableName);
        tableIdentifier = tableName;
    }

    public SalespersonTable() {
        super(tableIdentifier);
    }

    @Override
    public void createTable() throws SQLException {
        deleteTable();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE " + getTableName() + " (sID INTEGER PRIMARY KEY, sName VARCHAR(20) NOT NULL, sAddress VARCHAR(50) NOT NULL, sPhoneNumber INTEGER NOT NULL, sExperience INTEGER NOT NULL)");
    }

    @Override
    public void loadTable() throws SQLException, IOException {
        conn.setAutoCommit(false);
        super.loadTable();

        try (BufferedReader reader = new BufferedReader(new FileReader(Table.getSourceDir() + "salesperson.txt"))) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO " + getTableName() + " VALUES (?, ?, ?, ?, ?)"
            );

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t");
                ps.setInt(1, Integer.parseInt(data[0]));
                ps.setString(2, data[1]);
                ps.setString(3, data[2]);
                ps.setInt(4, Integer.parseInt(data[3]));
                ps.setInt(5, Integer.parseInt(data[4]));
                ps.executeUpdate();
            }
            conn.commit();
        } catch (SQLException | IOException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public static String getTableIdentifier() {
        return tableIdentifier;
    }
}
