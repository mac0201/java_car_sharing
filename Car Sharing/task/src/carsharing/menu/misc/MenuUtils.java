package carsharing.menu.misc;

import carsharing.model.Model;

import java.util.List;
import java.util.Scanner;

/** Provides utility methods for menus */
public class MenuUtils {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Reads an integer value from the standard input. Repeatedly prompts for input until valid integer provided.
     */
    public static int scanInteger() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format!");
            }
        }
    }

    /** Reads a string value from the standard input - the entire next line */
    public static String scanString() {
        return scanner.nextLine();
    }

    /** Prints a numbered list of models (entities) to the standard output
     *  The models need to extend the {@link Model } interface
     * */
    public static void printModelListNumbered(List<? extends Model> models) {
        int counter = 1;
        for (Model model : models) {
            System.out.printf("%d. %s\n", counter++, model.getName());
        }
    }

    /** Prints a numbered list of models (entities) with a "Back" option to the standard output.
     *  The models need to extend the {@link Model } interface
     * */
    public static void printModelListNumberedWithReturn(List<? extends Model> models) {
        printModelListNumbered(models);
        System.out.println("0. Back");
    }

    public static boolean validModelName(String name) {
        boolean valid = name != null && !name.trim().isEmpty();
        if (!valid) System.out.println("The name cannot be empty!");
        return valid;
    }

    /** Close scanner */
    public static void closeScanner() {
        scanner.close();
    }
}
