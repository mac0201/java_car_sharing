package carsharing.menus;

import carsharing.model.Model;

import java.util.InputMismatchException;
import java.util.List;
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

    public static void printNumberedList(List<? extends Model> modelList) {
        int counter = 1;
        for (Model model : modelList) {
            System.out.printf("%d. %s\n", counter++, model.getName());
        }
    }

    public static void closeScanner() {
        scanner.close();
    }
}
