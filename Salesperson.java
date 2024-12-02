import java.sql.*;
import java.util.Scanner;

public class Salesperson {
    private Connection conn;
    private Scanner scanner;

    public Salesperson(Connection conn) {
        this.conn = conn;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n-----Operations for salesperson menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Search for parts");
            System.out.println("2. Sell a part");
            System.out.println("3. Return to the main menu");
            System.out.print("Enter Your Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    searchParts();
                    break;
                case 2:
                    sellPart();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void searchParts() {
        System.out.println("Choose the Search criterion:");
        System.out.println("1. Part Name");
        System.out.println("2. Manufacturer Name");
        System.out.print("Choose the search criterion: ");
        
        int criterion = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Type in the Search Keyword: ");
        String keyword = scanner.nextLine();

        System.out.println("Choose ordering:");
        System.out.println("1. By price, ascending order");
        System.out.println("2. By price, descending order");
        System.out.print("Choose the search criterion: ");
        
        int ordering = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            // First check if manufacturer exists
            String checkSql = "SELECT mID, mName FROM manufacturer WHERE LOWER(mName) LIKE LOWER(?)";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, "%" + keyword + "%");
            ResultSet checkRs = checkStmt.executeQuery();
            
            System.out.println("\nMatching manufacturers:");
            while (checkRs.next()) {
                System.out.printf("Found manufacturer: ID=%d, Name=%s\n", 
                    checkRs.getInt("mID"), 
                    checkRs.getString("mName"));
            }

            String sql = 
                "SELECT p.pID as ID, p.pName as Name, m.mName as Manufacturer, " +
                "c.cName as Category, p.pAvailableQuantity as Quantity, " +
                "p.pWarrantyPeriod as Warranty, p.pPrice as Price " +
                "FROM part p " +
                "JOIN manufacturer m ON p.mID = m.mID " +
                "JOIN category c ON p.cID = c.cID " +
                "WHERE " + (criterion == 1 ? "LOWER(p.pName)" : "LOWER(m.mName)") + 
                " LIKE LOWER(?) " +
                "ORDER BY p.pPrice " + (ordering == 1 ? "ASC" : "DESC");

            // Debug: Print SQL and parameters
            System.out.println("Debug - SQL: " + sql);
            System.out.println("Debug - Keyword: " + keyword);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();

            // Print header
            System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warranty | Price |");

            // Debug: Print row count
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
                System.out.printf("| %d | %s | %s | %s | %d | %d | %d |\n",
                    rs.getInt("ID"),
                    rs.getString("Name"),
                    rs.getString("Manufacturer"),
                    rs.getString("Category"),
                    rs.getInt("Quantity"),
                    rs.getInt("Warranty"),
                    rs.getInt("Price")
                );
            }
            System.out.println("Debug - Rows found: " + rowCount);
            System.out.println("End of Query");

        } catch (SQLException e) {
            System.out.println("Error searching parts: " + e.getMessage());
            e.printStackTrace();  // Add stack trace for debugging
        }
    }

    private void sellPart() {
        System.out.print("Enter The Part ID: ");
        int partId = scanner.nextInt();
        
        System.out.print("Enter The Salesperson ID: ");
        int salespersonId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            // Start transaction
            conn.setAutoCommit(false);

            // Check part availability
            String checkSql = "SELECT pName, pAvailableQuantity FROM part WHERE pID = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, partId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt("pAvailableQuantity") > 0) {
                // Update part quantity
                String updateSql = "UPDATE part SET pAvailableQuantity = pAvailableQuantity - 1 WHERE pID = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, partId);
                updateStmt.executeUpdate();

                // Create transaction record
                String insertSql = "INSERT INTO transaction (tID, pID, sID, tDate) " +
                                 "VALUES ((SELECT NVL(MAX(tID), 0) + 1 FROM transaction), ?, ?, SYSDATE)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, partId);
                insertStmt.setInt(2, salespersonId);
                insertStmt.executeUpdate();

                conn.commit();
                System.out.println("Product: " + rs.getString("pName") + "(id: " + partId + 
                                 ") Remaining Quantity: " + (rs.getInt("pAvailableQuantity") - 1));
            } else {
                System.out.println("Error: Part not available!");
                conn.rollback();
            }

            conn.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("Error rolling back transaction: " + ex.getMessage());
            }
            System.out.println("Error performing transaction: " + e.getMessage());
        }
    }
} 