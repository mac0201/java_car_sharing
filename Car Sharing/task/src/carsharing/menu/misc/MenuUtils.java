package carsharing.menu.misc;

import carsharing.model.Model;

import java.util.List;
import java.util.Scanner;

public class MenuUtils {

    private static final Scanner scanner = new Scanner(System.in);


    public static int scanInteger() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format!");
            }
        }
    }

    public static String scanString() {
        return scanner.nextLine();
    }

    public static void printModelListNumbered(List<? extends Model> models) {
        int counter = 1;
        for (Model model : models) {
            System.out.printf("%d. %s\n", counter++, model.getName());
        }
    }

    public static void printModelListNumberedWithReturn(List<? extends Model> models) {
        printModelListNumbered(models);
        System.out.println("0. Back");
    }


//
//    public static void printNumberedModelList(List<? extends Model> modelList) {
//        int counter = 1;
//        for (Model model : modelList) {
//            System.out.printf("%d. %s\n", counter++, model.getName());
//        }
//        System.out.println("0. Back"); //! added
//    }
//
//    public static int printNumberedModelListAndGetInput(List<? extends Model> models) {
//        int counter = 1;
//        for (Model model : models) {
//            System.out.printf("%d. %s\n", counter++, model.getName());
//        }
//        System.out.println("0. Back"); //! added
//        while (true) {
//            int choice = MenuUtils.getNumericInput();
//            if (choice >= 0 && choice < models.size()) {
//                return choice;
//            }
//            System.out.println("Invalid input!");
//        }
//    }
//
//
//
//    public static void printNumberedOptionsList(List<String> options) {
//        int counter = 1;
//        String returnStr = null;
//        for (String option : options) {
//            if (option.equalsIgnoreCase("Exit") || option.equalsIgnoreCase("Back")) {
//                returnStr = option;
//            } else {
//                System.out.printf("%d. %s\n", counter++, option);
//            }
//        }
//        if (returnStr != null) System.out.println("0. " + returnStr); // back/exit as last element
//    }
//
//    public static int printNumberedOptionsListAndGetInput(List<String> options) {
//        int counter = 1;
//        String returnStr = null;
//        for (String option : options) {
//            if (option.equalsIgnoreCase("Exit") || option.equalsIgnoreCase("Back")) {
//                returnStr = option;
//            } else {
//                System.out.printf("%d. %s\n", counter++, option);
//            }
//        }
//        if (returnStr != null) System.out.println("0. " + returnStr); // back/exit as last element
//
//
//        while (true) {
//            int choice = MenuUtils.getNumericInput();
//            if (choice >= 0 && choice < options.size()) {
//                return choice;
//            }
//            System.out.println("Invalid input!");
//        }
//
//    }

    public static void closeScanner() {
        scanner.close();
    }
}
