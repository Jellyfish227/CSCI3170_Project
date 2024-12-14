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
    public void loadTable() throws SQLException {
        try (BufferedReader reader = new BufferedReader(new FileReader(Table.getSourceDir() + "transaction.txt"))) {
            conn.setAutoCommit(false);
            super.loadTable();
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
            conn.setAutoCommit(true);
        } catch (IOException e) {
            conn.rollback();
            conn.setAutoCommit(true);
            System.out.println("[Error] Failed to read data file: " + e.getMessage());
            System.out.println("Error File: " + e.getStackTrace()[0].getFileName());
        } catch (SQLException e) {
            conn.rollback();
            conn.setAutoCommit(true);
            System.out.println("[Error] Database error: " + e.getMessage());
            System.out.println("Error code: " + e.getErrorCode());
            if (e.getMessage().contains("integrity constraint")) {
                System.out.println("Please check if the foreign key references in the data files are correct!");
                System.out.println("Ensure pID in transaction.txt exists in part.txt");
                System.out.println("Ensure sID in transaction.txt exists in salesperson.txt");
            }
        }
    }

    public void recordTransaction(int partId, int salespersonId) throws SQLException {
        try {
            conn.setAutoCommit(false);
            // Check part availability
            String checkSql = "SELECT pName, pAvailableQuantity FROM "
                    + PartTable.getTableIdentifier()
                    + " WHERE pID = "
                    + partId;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(checkSql);

            if (rs.next() && rs.getInt("pAvailableQuantity") > 0) {
                // Update part quantity
                String updateSql = "UPDATE " + PartTable.getTableIdentifier() + " SET pAvailableQuantity = pAvailableQuantity - 1 WHERE pID = " + partId;
                Statement updateStmt = conn.createStatement();
                updateStmt.executeUpdate(updateSql);

                // Create transaction record
                String insertSql = "INSERT INTO " + getTableName() + " (tID, pID, sID, tDate) "
                        + "VALUES ((SELECT NVL(MAX(tID), 0) + 1 FROM " + getTableName() + "), "
                        + partId + ", "
                        + salespersonId + ", SYSDATE)";
                Statement insertStmt = conn.createStatement();
                insertStmt.executeUpdate(insertSql);

                conn.commit();
                System.out.println("Product: " + rs.getString("pName") + "(id: "
                        + partId + ") Remaining Quantity: " + (rs.getInt("pAvailableQuantity") - 1));
            } else {
                System.out.println("Error: Part not available!");
                conn.rollback();
            }
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            conn.rollback();
            conn.setAutoCommit(true);
            System.out.println("Error performing transaction: " + e.getMessage());
        }
    }

    public static String getTableIdentifier() {
        return tableIdentifier;
    }
}
