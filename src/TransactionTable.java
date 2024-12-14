import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionTable extends Table {
    public static final int COLUMNS = 4;
    private static String tableIdentifier = "transaction";

    public TransactionTable(String tableName) {
        super(tableName);
        tableIdentifier = tableName;
    }
    public TransactionTable() {
        super(tableIdentifier);
    }

    @Override
    public void createTable() throws SQLException {
        deleteTable();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE " + getTableName() + " (tID INTEGER PRIMARY KEY, " +
                "pID INTEGER REFERENCES "+ PartTable.getTableIdentifier() + "(pID), " +
                "sID INTEGER REFERENCES " + SalespersonTable.getTableIdentifier() + "(sID), " +
                "tDate DATE NOT NULL)");
    }

    @Override
    public void loadTable() throws SQLException, IOException {
        conn.setAutoCommit(false);
        super.loadTable();
        try (BufferedReader reader = new BufferedReader(new FileReader(Table.getSourceDir() + "transaction.txt"))) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO " + getTableName() + " VALUES (?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'))"
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
            conn.commit();
        } catch (SQLException | IOException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void recordTransaction(int partId, int salespersonId) throws SQLException {
        conn.setAutoCommit(false);
        try {
            // Check part availability
            String checkSql = "SELECT pName, pAvailableQuantity FROM "
                    + PartTable.getTableIdentifier()
                    + " WHERE pID = "
                    + partId;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(checkSql);

            if (rs.next() && rs.getInt("pAvailableQuantity") > 0) {
                // Update part quantity
                String updateSql = "UPDATE part SET pAvailableQuantity = pAvailableQuantity - 1 WHERE pID = ?";
                PreparedStatement updateStmt = db.prepareStatement(updateSql);
                updateStmt.setInt(1, partId);
                updateStmt.executeUpdate();

                // Create transaction record
                String insertSql = "INSERT INTO transaction (tID, pID, sID, tDate) " +
                        "VALUES ((SELECT NVL(MAX(tID), 0) + 1 FROM transaction), ?, ?, SYSDATE)";
                PreparedStatement insertStmt = db.prepareStatement(insertSql);
                insertStmt.setInt(1, partId);
                insertStmt.setInt(2, salespersonId);
                insertStmt.executeUpdate();

                db.commit();
                System.out.println("Product: " + rs.getString("pName") + "(id: " + partId +
                        ") Remaining Quantity: " + (rs.getInt("pAvailableQuantity") - 1));
            } else {
                System.out.println("Error: Part not available!");
                db.rollback();
            }

            db.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                db.rollback();
                db.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("Error rolling back transaction: " + ex.getMessage());
            }

        }
    }

    public static String getTableIdentifier() {
        return tableIdentifier;
    }
}
