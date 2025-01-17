import java.sql.SQLException;

public class Salesperson extends User {
    private PartTable pt;
    private TransactionTable tt;

    public Salesperson(Table[] tables) {
        super(tables);
        for (Table table : tables) {
            if (table instanceof PartTable) { pt = (PartTable) table; }
            else if (table instanceof TransactionTable) { tt = (TransactionTable) table; }
        }
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

        pt.queryPartTable(criterion, keyword, ordering);
    }

    private void sellPart() {
        // generate the check sql
        int partId = Console.readInt("Enter The Part ID: ");
        int salespersonId = Console.readInt("Enter The Salesperson ID: ");
        try {
            tt.recordTransaction(partId, salespersonId);
        } catch (SQLException e) {
            System.out.println("Error rolling back transaction: " + e.getMessage());
        }
    }

}
