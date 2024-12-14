import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Administrator extends User {
    private Connectable db;

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
            Statement stmt = db.createStatement();
            
            // Create tables with foreign key constraints
            String[] createStatements = {
                "CREATE TABLE category (cID INTEGER PRIMARY KEY, cName VARCHAR(20) NOT NULL)",
                
                "CREATE TABLE manufacturer (mID INTEGER PRIMARY KEY, mName VARCHAR(20) NOT NULL, mAddress VARCHAR(50) NOT NULL, mPhoneNumber INTEGER NOT NULL)",
                
                "CREATE TABLE salesperson (sID INTEGER PRIMARY KEY, sName VARCHAR(20) NOT NULL, sAddress VARCHAR(50) NOT NULL, sPhoneNumber INTEGER NOT NULL, sExperience INTEGER NOT NULL)",
                
                "CREATE TABLE part (pID INTEGER PRIMARY KEY, pName VARCHAR(20) NOT NULL, pPrice INTEGER NOT NULL, pWarrantyPeriod INTEGER NOT NULL, pAvailableQuantity INTEGER NOT NULL, " +
                "mID INTEGER REFERENCES manufacturer(mID), " +
                "cID INTEGER REFERENCES category(cID))",
                
                "CREATE TABLE transaction (tID INTEGER PRIMARY KEY, " +
                "pID INTEGER REFERENCES part(pID), " +
                "sID INTEGER REFERENCES salesperson(sID), " +
                "tDate DATE NOT NULL)"
            };

            // Execute create statements
            for (String createSql : createStatements) {
                stmt.execute(createSql);
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
            Statement stmt = db.createStatement();
            
            // Use the same drop statements as in createTables()
            String[] dropStatements = {
                "BEGIN EXECUTE IMMEDIATE 'DROP TABLE transaction CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -942 THEN RAISE; END IF; END;",
                "BEGIN EXECUTE IMMEDIATE 'DROP TABLE part CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -942 THEN RAISE; END IF; END;",
                "BEGIN EXECUTE IMMEDIATE 'DROP TABLE salesperson CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -942 THEN RAISE; END IF; END;",
                "BEGIN EXECUTE IMMEDIATE 'DROP TABLE manufacturer CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -942 THEN RAISE; END IF; END;",
                "BEGIN EXECUTE IMMEDIATE 'DROP TABLE category CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN IF SQLCODE != -942 THEN RAISE; END IF; END;"
            };

            // Execute drop statements
            for (String dropSql : dropStatements) {
                stmt.execute(dropSql);
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
            db.setAutoCommit(false);
            
            Statement stmt = db.createStatement();
            String[] deleteStatements = {
                "DELETE FROM transaction",
                "DELETE FROM part",
                "DELETE FROM salesperson",
                "DELETE FROM manufacturer",
                "DELETE FROM category"
            };
            
            for (String delete : deleteStatements) {
                stmt.executeUpdate(delete);
            }

            try {

                loadCategory(folderPath);
                

                loadManufacturer(folderPath);
                

                loadSalesperson(folderPath);
                

                loadPart(folderPath);
                

                loadTransaction(folderPath);

                db.commit();
                System.out.println("Data loaded successfully!");

            } catch (IOException e) {
                db.rollback();
                System.out.println("[Error] Failed to read data file: " + e.getMessage());
                System.out.println("File path: " + e.getStackTrace()[0].getFileName());
            } catch (SQLException e) {
                db.rollback();
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
                db.setAutoCommit(true);
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
