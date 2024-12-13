import java.sql.SQLException;
import java.sql.Statement;

public class CatagoryTable extends Table {
    public static final int COLUMNS = 2;

    @Override
    public void createTable() throws SQLException {
        deleteTable();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE category (cID INTEGER PRIMARY KEY, cName VARCHAR(20) NOT NULL)");
    }

    @Override
    public void deleteTable() throws SQLException {
        Statement stmt = conn.createStatement();

    }

    @Override
    public void loadTable() throws SQLException {

    }

    @Override
    public String[] queryTable(String query) {
        return new String[0];
    }
}
