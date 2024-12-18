import java.util.InputMismatchException;
import java.util.Scanner;

public class Console {
    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Wrong input type detected, please try again.");
            }
        }
    }

    public static int readInt(String prompt, int min, int max) {
        int value;
        while (true) {
            try {
                System.out.print(prompt);
                value = scanner.nextInt();
                if (value > min && value < max) {
                    break;
                }
                System.out.print("Enter a value between " + min + " and " + max + ": ");
            } catch (InputMismatchException e) {
                scanner.nextLine(); // empty buffer
                System.out.println("Wrong input type detected, please try again.");
            }
        }
        return value;
    }

    public static double readDouble(String prompt) {
        System.out.print(prompt);
        return scanner.nextDouble();
    }

    public static String readString(String prompt) {
        scanner.nextLine(); // empty the buffer
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
