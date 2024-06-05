package carsharing.menu;

import java.util.Arrays;
import java.util.List;

public enum MenuOptions {

    MAIN("Manager menu", "Customer menu", "Add customer", "Exit"),
    MANAGER("Company list", "Add company", "Back"),
    OPTIONS_RENT("Rent car", "Return rented car", "My rented car", "Back"),
    CUSTOMER(); // dynamic options,
//    OPTIONS_CAR_MANAGER("Car list", "Add car", "Back"),


    private final List<String> options;

    MenuOptions(String... options) {
        this.options = Arrays.asList(options);
    }

    public List<String> getOptions() {
        return options;
    }



}
