import java.util.Scanner;

public class Console {
    private static Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt) {
        System.out.print(prompt);
        return scanner.nextInt();
    }

    public static int readInt(String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            value = scanner.nextInt();
            if (value > min || value < max) {
                break;
            }
            System.out.print("Enter a value between " + min + " and " + max + ": ");
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
