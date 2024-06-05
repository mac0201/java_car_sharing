package carsharing.menu;

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

    public static void printNumberedModelList(List<? extends Model> modelList) {
        int counter = 1;
        for (Model model : modelList) {
            System.out.printf("%d. %s\n", counter++, model.getName());
        }
        System.out.println("0. Back"); //! added
    }

    public static void printNumberedOptionsList(List<String> options) {
        int counter = 1;
        String returnStr = null;
        for (String option : options) {
            if (option.equalsIgnoreCase("Exit") || option.equalsIgnoreCase("Back")) {
                returnStr = option;
            } else {
                System.out.printf("%d. %s\n", counter++, option);
            }
        }
        if (returnStr != null) System.out.println("0. " + returnStr); // back/exit as last element
    }

    public static void closeScanner() {
        scanner.close();
    }
}
