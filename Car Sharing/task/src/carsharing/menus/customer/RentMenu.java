package carsharing.menus.customer;

import carsharing.menus.Menu;
import carsharing.menus.MenuUtils;
import carsharing.model.Customer;
import carsharing.service.CustomerService;

public class RentMenu implements Menu {

    private final CustomerService customerService;
    private final Menu parentMenu;
    private Customer customer;

    public RentMenu(CustomerService customerService, Menu parentMenu) {
        this.customerService = customerService;
        this.parentMenu = parentMenu;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void displayOptions() {
        String menu = """
                \n=== RENT MENU ===
                1. Rent a car
                2. Return a rented car
                3. My rented car
                0. Back
                """;
        System.out.println(menu);
    }

    @Override
    public void launch() {
        displayOptions();
        System.out.println("CUSTOMER: " + customer.getName());
        int choice = MenuUtils.getNumericInput();
        switch (choice) {
            case 0 -> {
                parentMenu.launch();
                return;
            }
            case 1 -> {
                System.out.println("renting...");
                return;
            }
            case 2 -> System.out.println("returning...");
            case 3 -> System.out.println("viewing...");
            default -> System.out.println("Invalid choice!");
        }
    }
}
