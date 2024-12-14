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
    public void loadTable() throws SQLException {
        try (BufferedReader reader = new BufferedReader(new FileReader(Table.getSourceDir() + "salesperson.txt"))) {
            conn.setAutoCommit(false);
            super.loadTable();
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
        }
    }

    public static String getTableIdentifier() {
        return tableIdentifier;
    }

    public void queryTable(int ordering) {
        String sql = "SELECT sID as ID, sName as Name, sPhoneNumber as \"Mobile Phone\", " +
                "sExperience as \"Years of Experience\" " +
                "FROM salesperson ORDER BY sExperience " +
                (ordering == 1 ? "ASC" : "DESC");

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Print headers
            System.out.println("| ID | Name | Mobile Phone | Years of Experience |");

            // Print results
            while (rs.next()) {
                System.out.printf("| %d | %s | %d | %d |\n",
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getInt("Mobile Phone"),
                        rs.getInt("Years of Experience")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error listing salespersons: " + e.getMessage());
        }
    }
}
