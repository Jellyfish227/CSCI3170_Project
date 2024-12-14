import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionTable extends Table {
    public static final int COLUMNS = 4;

    public TransactionTable(String tableName) {
        super(tableName);
    }

    @Override
    public void createTable() throws SQLException {
        deleteTable();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE transaction (tID INTEGER PRIMARY KEY, " +
                "pID INTEGER REFERENCES part(pID), " +
                "sID INTEGER REFERENCES salesperson(sID), " +
                "tDate DATE NOT NULL)");
    }

    @Override
    public void deleteTable() throws SQLException {

    }

    @Override
    public void loadTable() throws SQLException, IOException {
        super.loadTable();

        try (BufferedReader reader = new BufferedReader(new FileReader(Table.getSouceDir() + "transaction.txt"))) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO transaction VALUES (?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'))"
            );

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t");
                stmt.setInt(1, Integer.parseInt(data[0]));
                stmt.setInt(2, Integer.parseInt(data[1]));
                stmt.setInt(3, Integer.parseInt(data[2]));
                stmt.setString(4, data[3]);
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public String[] queryTable(String query) {
        return new String[0];
    }
}
