import java.sql.Connection;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Connection conn = null;

    public static void main(String[] args) {
        // Initialize database connection
        DatabaseConnector dbConnector = new DatabaseConnector();
        conn = dbConnector.getConnection();

        if (conn == null) {
            System.out.println("Failed to connect to the database!");
            return;
        }

        while (true) {
            // Display welcome message and main menu
            System.out.println("Welcome to sales system!");
            System.out.println("\n-----Main menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Operations for administrator");
            System.out.println("2. Operations for salesperson");
            System.out.println("3. Operations for manager");
            System.out.println("4. Exit this program");
            System.out.print("Enter Your Choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        Administrator admin = new Administrator(conn);
                        admin.showMenu();
                        break;
                    case 2:
                        Salesperson salesperson = new Salesperson(conn);
                        salesperson.showMenu();
                        break;
                    case 3:
                        Manager manager = new Manager(conn);
                        manager.showMenu();
                        break;
                    case 4:
                        System.out.println("Thank you for using the sales system!");
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (Exception e) {
                                System.out.println("Error closing database connection: " + e.getMessage());
                            }
                        }
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
} 