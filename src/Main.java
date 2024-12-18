public class Main {
    public static void main(String[] args) {
        // Initialize database connection by try with resources block
        try (Connectable db = new OracalDBConnector()) {
            Table.conn = db.getConnection();
            Table[] tables = {new CategoryTable(), new ManufacturerTable(), new PartTable(), new SalespersonTable(), new TransactionTable()};
            System.out.println("Welcome to sales system!");

            while (true) {
                // Display welcome message and main menu
                System.out.println("\n-----Main menu-----");
                System.out.println("What kinds of operation would you like to perform?");
                System.out.println("1. Operations for administrator");
                System.out.println("2. Operations for salesperson");
                System.out.println("3. Operations for manager");
                System.out.println("4. Exit this program");
                User user = null;
                int choice = Console.readInt("Enter Your Choice: ", 1, 4);
                switch (choice) {
                    case 1:
                        user = new Administrator(tables); // Inject tables into the corresponding user
                        break;
                    case 2:
                        user = new Salesperson(tables);
                        break;
                    case 3:
                        user = new Manager(tables);
                        break;
                    case 4:
                        System.out.println("Thank you for using the sales system!");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
                assert user != null;
                user.executeMenu();
            }
        } catch (Exception e) {
            System.out.println("Program terminated with an error. Check stack trace!");
            e.printStackTrace(); // FIXME: remove after debug
        }
    }
} 
