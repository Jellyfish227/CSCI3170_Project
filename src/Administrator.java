import java.io.File;
import java.sql.SQLException;

public class Administrator extends User {
    public Administrator(Table[] tables) {
        super(tables);
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
            for (Table table : tables) {
                table.loadTable();
            }
            System.out.println("Processing...Done! Data is inputted to the database!");
        } catch (SQLException e) {
            System.out.println("Error rolling back transaction: " + e.getMessage());
        }
    }

    private void showTableContent() {
        String showTableName = Console.readString("Which table would you like to show: ").toLowerCase();

        try {
            for (Table table : tables) {
                if (table.getTableName().equals(showTableName)) {
                    table.queryTable();
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error showing table content: " + e.getMessage());
        }
    }
}
