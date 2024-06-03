package carsharing.menus;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuUtils {

    private static final Scanner scanner = new Scanner(System.in);

    public static int getNumericInput() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Invalid input!");
                scanner.next(); // clear
            }
        }
    }

    public static String getStringInput() {
        scanner.nextLine(); // ?
        return scanner.nextLine();
    }

    public static void closeScanner() {
        scanner.close();
    }
}
