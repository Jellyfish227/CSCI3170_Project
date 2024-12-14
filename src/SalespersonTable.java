import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalespersonTable extends Table {
    public static final int COLUMNS = 5;

    @Override
    public void createTable() throws SQLException {

    }

    @Override
    public void deleteTable() throws SQLException {

    }

    @Override
    public void loadTable() throws SQLException, IOException {
        System.out.println("Loading salespersons...");
        try (BufferedReader reader = new BufferedReader(new FileReader(Table.getSouceDir() + "salesperson.txt"))) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO salesperson VALUES (?, ?, ?, ?, ?)"
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
        }
    }

    @Override
    public String[] queryTable(String query) {
        return new String[0];
    }
}
