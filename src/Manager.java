import java.sql.*;

public class Manager extends User {
    private Connectable db;

    public Manager(Connectable db){
        this.db = db;
    }

    @Override
    public void executeMenu() {
        while (true) {
            System.out.println("\n-----Operations for manager menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. List all salespersons");
            System.out.println("2. Count the no. of sales record of each salesperson under a specific range on years of experience");
            System.out.println("3. Show the total sales value of each manufacturer");
            System.out.println("4. Show the N most popular part");
            System.out.println("5. Return to the main menu");
            System.out.print("Enter Your Choice: ");

            int choice = Console.readInt("Enter Your Choice: ", 1, 5);

            switch (choice) {
                case 1:
                    listSalespersons();
                    break;
                case 2:
                    countTransactions();
                    break;
                case 3:
                    showManufacturerSales();
                    break;
                case 4:
                    showPopularParts();
                    break;
                case 5:
                    return;
            }
        }
    }

    private void listSalespersons() {
        System.out.println("Choose ordering:");
        System.out.println("1. By ascending order");
        System.out.println("2. By descending order");

        int ordering = Console.readInt("Choose the list ordering: ", 1, 2);

        try {
            String sql = "SELECT sID as ID, sName as Name, sPhoneNumber as \"Mobile Phone\", " +
                        "sExperience as \"Years of Experience\" " +
                        "FROM salesperson ORDER BY sExperience " + 
                        (ordering == 1 ? "ASC" : "DESC");

            Statement stmt = db.createStatement();
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

    private void countTransactions() {
        int lowerBound = Console.readInt("Type in the lower bound for years of experience: ");
        
        int upperBound = Console.readInt("Type in the upper bound for years of experience: ");

        try {
            String sql = "SELECT s.sID as ID, s.sName as Name, s.sExperience as Experience, " +
                        "COUNT(t.tID) as \"Number of Transaction\" " +
                        "FROM salesperson s LEFT JOIN transaction t ON s.sID = t.sID " +
                        "WHERE s.sExperience BETWEEN ? AND ? " +
                        "GROUP BY s.sID, s.sName, s.sExperience " +
                        "ORDER BY s.sID DESC";

            PreparedStatement pstmt = db.prepareStatement(sql);
            pstmt.setInt(1, lowerBound);
            pstmt.setInt(2, upperBound);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Transaction Record:");
            System.out.println("| ID | Name | Years of Experience | Number of Transaction |");
            
            while (rs.next()) {
                System.out.printf("| %d | %s | %d | %d |\n",
                    rs.getInt("ID"),
                    rs.getString("Name"),
                    rs.getInt("Experience"),
                    rs.getInt("Number of Transaction")
                );
            }
            System.out.println("End of Query");

        } catch (SQLException e) {
            System.out.println("Error counting transactions: " + e.getMessage());
        }
    }

    private void showManufacturerSales() {
        try {
            String sql = "SELECT m.mID as \"Manufacturer ID\", " +
                        "m.mName as \"Manufacturer Name\", " +
                        "COALESCE(SUM(p.pPrice), 0) as \"Total Sales Value\" " +
                        "FROM manufacturer m " +
                        "LEFT JOIN part p ON m.mID = p.mID " +
                        "LEFT JOIN transaction t ON p.pID = t.pID " +
                        "GROUP BY m.mID, m.mName " +
                        "ORDER BY \"Total Sales Value\" DESC";

            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("| Manufacturer ID | Manufacturer Name | Total Sales Value |");
            
            while (rs.next()) {
                System.out.printf("| %d | %s | %d |\n",
                    rs.getInt("Manufacturer ID"),
                    rs.getString("Manufacturer Name"),
                    rs.getInt("Total Sales Value")
                );
            }
            System.out.println("End of Query");

        } catch (SQLException e) {
            System.out.println("Error showing manufacturer sales: " + e.getMessage());
        }
    }

    private void showPopularParts() {
        int n = Console.readInt("Type in the number of parts: ");

        try {
            String sql = "SELECT p.pID, p.pName, COUNT(t.tID) as transaction_count " +
                        "FROM part p " +
                        "LEFT JOIN transaction t ON p.pID = t.pID " +
                        "GROUP BY p.pID, p.pName " +
                        "HAVING COUNT(t.tID) > 0 " +
                        "ORDER BY transaction_count DESC " +
                        "FETCH FIRST ? ROWS ONLY";

            PreparedStatement pstmt = db.prepareStatement(sql);
            pstmt.setInt(1, n);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("| Part ID | Part Name | No. of Transaction |");
            
            while (rs.next()) {
                System.out.printf("| %d | %s | %d |\n",
                    rs.getInt("pID"),
                    rs.getString("pName"),
                    rs.getInt("transaction_count")
                );
            }
            System.out.println("End of Query");

        } catch (SQLException e) {
            System.out.println("Error showing popular parts: " + e.getMessage());
        }
    }
}