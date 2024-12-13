public class Main {
    public static void main(String[] args) {
        // Initialize database connection by try with resources block
        try (Connectable db = new OracalDBConnector()) {
            while (true) {
                // Display welcome message and main menu
                System.out.println("Welcome to sales system!");
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
                        user = new Administrator(db); // Inject database dependencies into the corresponding user
                        break;
                    case 2:
                        user = new Salesperson(db);
                        break;
                    case 3:
                        user = new Manager(db);
                        break;
                    case 4:
                        System.out.println("Thank you for using the sales system!");
                        return;
                }
                assert user != null;
                user.executeMenu();
            }
        } catch (Exception e) {
            System.out.println("Error with database connection! ");
        }
    }
} 