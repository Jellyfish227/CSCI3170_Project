import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class PartTable extends Table {
    public static final int COLUMNS = 7;
    private static String tableIdentifier = "part";

    public PartTable(String tableName) {
        super(tableName);
        tableIdentifier = tableName;
    }

    public PartTable() {
        super(tableIdentifier);
    }

    @Override
    public void createTable() throws SQLException {
        deleteTable();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE "+ getTableName() + " (pID INTEGER PRIMARY KEY, pName VARCHAR(20) NOT NULL, pPrice INTEGER NOT NULL, pWarrantyPeriod INTEGER NOT NULL, pAvailableQuantity INTEGER NOT NULL, " +
                "mID INTEGER REFERENCES " + ManufacturerTable.getTableIdentifier() + "(mID), " +
                "cID INTEGER REFERENCES " + CategoryTable.getTableIdentifier() + "(cID))");
    }

    @Override
    public void loadTable() throws SQLException {
        conn.setAutoCommit(false);
        super.loadTable();

        // First check if all manufacturer IDs exist
        Set<Integer> validManufacturerIds = new HashSet<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT mID FROM " + ManufacturerTable.getTableIdentifier())) {
            while (rs.next()) {
                validManufacturerIds.add(rs.getInt("mID"));
            }
        }

        // Check if all category IDs exist
        Set<Integer> validCategoryIds = new HashSet<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT cID FROM " + CategoryTable.getTableIdentifier())) {
            while (rs.next()) {
                validCategoryIds.add(rs.getInt("cID"));
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(Table.getSourceDir() + "part.txt"))) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO " + getTableName() + " VALUES (?, ?, ?, ?, ?, ?, ?)"
            );

            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] data = line.split("\t");
                try {
                    // Validate data format
                    int partId = Integer.parseInt(data[0]);
                    if (partId <= 0 || partId > 999) {
                        System.out.println("Error in line " + lineNumber + ": Part ID must be between 1 and 999");
                        continue;
                    }

                    int price = Integer.parseInt(data[2]);
                    if (price <= 0 || price > 99999) {
                        System.out.println("Error in line " + lineNumber + ": Price must be between 1 and 99999");
                        continue;
                    }

                    int mID = Integer.parseInt(data[3]);
                    if (mID <= 0 || mID > 99) {
                        System.out.println("Error in line " + lineNumber + ": Manufacturer ID must be between 1 and 99");
                        continue;
                    }

                    int cID = Integer.parseInt(data[4]);
                    if (cID <= 0 || cID > 9) {
                        System.out.println("Error in line " + lineNumber + ": Category ID must be between 1 and 9");
                        continue;
                    }

                    int warranty = Integer.parseInt(data[5]);
                    if (warranty <= 0 || warranty > 99) {
                        System.out.println("Error in line " + lineNumber + ": Warranty period must be between 1 and 99");
                        continue;
                    }

                    int quantity = Integer.parseInt(data[6]);
                    if (quantity < 0 || quantity > 99) {
                        System.out.println("Error in line " + lineNumber + ": Available quantity must be between 0 and 99");
                        continue;
                    }

                    // Check foreign key constraints
                    if (!validManufacturerIds.contains(mID)) {
                        System.out.println("Error in line " + lineNumber + ": Invalid manufacturer ID " + mID);
                        System.out.println("Valid manufacturer IDs are: " + validManufacturerIds);
                        continue;
                    }
                    if (!validCategoryIds.contains(cID)) {
                        System.out.println("Error in line " + lineNumber + ": Invalid category ID " + cID);
                        System.out.println("Valid category IDs are: " + validCategoryIds);
                        continue;
                    }

                    // Insert data
                    stmt.setInt(1, partId);        // Part ID
                    stmt.setString(2, data[1]);    // Part Name
                    stmt.setInt(3, price);         // Part Price
                    stmt.setInt(4, warranty);      // Part Warranty Period
                    stmt.setInt(5, quantity);      // Part Available Quantity
                    stmt.setInt(6, mID);           // Part Manufacturer ID
                    stmt.setInt(7, cID);           // Part Category ID

                    stmt.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Error in line " + lineNumber + ": " + line);
                    throw e;
                } catch (NumberFormatException e) {
                    System.out.println("Error in line " + lineNumber + ": Invalid number format");
                    System.out.println("Line content: " + line);
                }
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
                System.out.println("Ensure mID in part.txt exists in manufacturer.txt");
                System.out.println("Ensure cID in part.txt exists in category.txt");
            }
        }
    }

    public static String getTableIdentifier() {
        return tableIdentifier;
    }
}
