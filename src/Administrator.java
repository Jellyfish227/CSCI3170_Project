import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Administrator extends User {
    private Connectable db;
    private Table[] tables = {new CategoryTable("category"), new ManufacturerTable("manufacturer"), new PartTable("part"), new SalespersonTable("salesperson"), new TransactionTable("transaction")};

    public Administrator(Connectable db) {
        this.db = db;
        Table.conn = db.getConnection();
    }

    @Override
    public void executeMenu() {
        while (true) {
            System.out.println("\n-----Operations for administrator menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Create all tables");
            System.out.println("2. Delete all tables");
            System.out.println("3. Load from datafile");
            System.out.println("4. Show content of a table");
            System.out.println("5. Return to the main menu");

            int choice = Console.readInt("Enter Your Choice: ", 1, 5);

            switch (choice) {
                case 1:
                    createTables();
                    break;
                case 2:
                    deleteTables();
                    break;
                case 3:
                    loadFromDatafile();
                    break;
                case 4:
                    showTableContent();
                    break;
                case 5:
                    return;
            }
        }
    }

    private void createTables() {
        try {
            for (Table table : tables) {
                table.createTable();
            }

            System.out.println("Processing...Done! Database is initialized!");
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
            if (e.getErrorCode() != 955) { // Ignore "table already exists" error
                e.printStackTrace();
            }
        }
    }

    private void deleteTables() {
        try {
            for (Table table : tables) {
                table.deleteTable();
            }
            System.out.println("Processing...Done! Database is removed!");
        } catch (SQLException e) {
            System.out.println("Error deleting tables: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadFromDatafile() {
        String folderPath = Console.readString("Please enter the folder path: ").trim();
        
        // Ensure path ends with slash
        if (!folderPath.endsWith(File.separator)) {
            folderPath += File.separator;
        }

        Table.setSourceDir(folderPath);
        
        try {
            db.getConnection().setAutoCommit(false);

            try {
                for (Table table : tables) {
                    table.loadTable();
                }

                db.getConnection().commit();
                System.out.println("Data loaded successfully!");

            } catch (IOException e) {
                db.getConnection().rollback();
                System.out.println("[Error] Failed to read data file: " + e.getMessage());
                System.out.println("File path: " + e.getStackTrace()[0].getFileName());
            } catch (SQLException e) {
                db.getConnection().rollback();
                System.out.println("[Error] Database error: " + e.getMessage());
                System.out.println("Error code: " + e.getErrorCode());
                if (e.getMessage().contains("integrity constraint")) {
                    System.out.println("Please check if the foreign key references in the data files are correct!");
                    System.out.println("Ensure mID in part.txt exists in manufacturer.txt");
                    System.out.println("Ensure cID in part.txt exists in category.txt");
                }
            }
        } catch (Exception e) {
            System.out.println("[Error] Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                db.getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("[Error] Failed to reset auto-commit: " + e.getMessage());
            }
        }
    }

    private void showTableContent() {
        String tableName = Console.readString("Which table would you like to show: ").toLowerCase();

        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Print column headers with proper formatting
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-20s", metaData.getColumnName(i));
            }
            System.out.println();

            // Print separator line
            for (int i = 1; i <= columnCount; i++) {
                System.out.print("--------------------");
            }
            System.out.println();

            // Print data rows with proper formatting
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String value = rs.getString(i);
                    System.out.printf("%-20s", value != null ? value : "");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Error showing table content: " + e.getMessage());
        }
    }
}
