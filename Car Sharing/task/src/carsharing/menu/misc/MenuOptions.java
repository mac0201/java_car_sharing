package carsharing.menu.misc;

import java.util.Arrays;
import java.util.List;

public enum MenuOptions {

    MAIN("Log in as a manager", "Log in as a customer", "Create a customer", "Exit"),
    MANAGER_COMPANY("Company list", "Create a company", "Back"),
    MANAGER_CAR("Car list", "Create a car", "Back"),
    RENT("Rent a car", "Return a rented car", "My rented car", "Back");

    private final List<String> options;

    MenuOptions(String... options) {
        this.options = Arrays.asList(options);
    }

    public List<String> getOptions() {
        return options;
    }

    public void printOptionsNumbered() {
        int counter = 1;
        String returnString = null;
        for (String option : options) {
            if (option.equalsIgnoreCase("Back") || option.equalsIgnoreCase("Exit")) {
                returnString = option;
            } else {
                System.out.printf("%d. %s\n", counter++, option);
            }
        }
        if (returnString != null) System.out.println("0. " + returnString);
    }

}
