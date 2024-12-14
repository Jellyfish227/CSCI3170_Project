import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoryTable extends Table {
    public static final int COLUMNS = 2;
    private static String tableIdentifier = "category";

    public CategoryTable(String tableName) {
        super(tableName);
        tableIdentifier = tableName;
    }

    public CategoryTable() {
        super(tableIdentifier);
    }

    @Override
    public void createTable() throws SQLException {
        deleteTable();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE " + getTableName() + " (cID INTEGER PRIMARY KEY, cName VARCHAR(20) NOT NULL)");
    }

    @Override
    public void loadTable() throws SQLException, IOException {
        conn.setAutoCommit(false);
        super.loadTable();

        try (BufferedReader reader = new BufferedReader(new FileReader(Table.getSourceDir() + "category.txt"))) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO " + getTableName() + " VALUES (?, ?)");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t");
                ps.setInt(1, Integer.parseInt(data[0]));
                ps.setString(2, data[1]);
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
