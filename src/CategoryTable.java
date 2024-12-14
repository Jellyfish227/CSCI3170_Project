import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoryTable extends Table {
    public static final int COLUMNS = 2;

    public CategoryTable(String tableName) {
        super(tableName);
    }

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
    public void loadTable() throws SQLException, IOException {
        System.out.println("Loading categories...");
        try (BufferedReader reader = new BufferedReader(new FileReader(Table.getSouceDir() + "category.txt"))) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO category VALUES (?, ?)");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t");
                ps.setInt(1, Integer.parseInt(data[0]));
                ps.setString(2, data[1]);
                ps.executeUpdate();
            }
        }
    }

    @Override
    public String[] queryTable(String query) {
        return new String[0];
    }
}
