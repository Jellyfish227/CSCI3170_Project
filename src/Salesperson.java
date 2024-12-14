import java.sql.SQLException;

public class Salesperson extends User {
    private Connectable db;

    public Salesperson(Connectable db) {
        this.db = db;
    }

    @Override
    public void executeMenu() {
        while (true) {
            System.out.println("\n-----Operations for salesperson menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Search for parts");
            System.out.println("2. Sell a part");
            System.out.println("3. Return to the main menu");

            int choice = Console.readInt("Enter Your Choice: ", 1, 3);
            switch (choice) {
                case 1:
                    searchParts();
                    break;
                case 2:
                    sellPart();
                    break;
                case 3:
                    return;
            }
        }
    }

    private void searchParts() {
        // handle query generation, pass the query to fn queryTable(query)
        System.out.println("Choose the Search criterion:");
        System.out.println("1. Part Name");
        System.out.println("2. Manufacturer Name");

        int criterion = Console.readInt("Choose the search criterion: ", 1, 2);

        String keyword = Console.readString("Type in the Search Keyword: ");

        System.out.println("Choose ordering:");
        System.out.println("1. By price, ascending order");
        System.out.println("2. By price, descending order");

        int ordering = Console.readInt("Choose the search criterion: ", 1, 2);

        String sql =
                "SELECT p.pID as ID, p.pName as Name, m.mName as Manufacturer, " +
                        "c.cName as Category, p.pAvailableQuantity as Quantity, " +
                        "p.pWarrantyPeriod as Warranty, p.pPrice as Price " +
                        "FROM part p " +
                        "JOIN manufacturer m ON p.mID = m.mID " +
                        "JOIN category c ON p.cID = c.cID " +
                        "WHERE " + (criterion == 1 ? "LOWER(p.pName)" : "LOWER(m.mName)") +
                        " LIKE LOWER(%" + keyword + "%) " +
                        "ORDER BY p.pPrice " + (ordering == 1 ? "ASC" : "DESC");

        PartTable parts = new PartTable();
        try {
            parts.queryTable();
        } catch (SQLException e) {
            System.out.println("Error searching parts: " + e.getMessage());
            e.printStackTrace();  // Add stack trace for debugging
        }
    }

    private void sellPart() {
        // generate the check sql
        int partId = Console.readInt("Enter The Part ID: ");
        int salespersonId = Console.readInt("Enter The Salesperson ID: ");
        TransactionTable transactions = new TransactionTable();
        try {
            transactions.recordTransaction(partId, salespersonId);
        } catch (SQLException e) {
            System.out.println("Error rolling back transaction: " + e.getMessage());
        }
    }

}
