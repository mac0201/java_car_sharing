package carsharing.menu.menus;

import carsharing.menu.Menu;
import carsharing.menu.MenuOptions;
import carsharing.menu.MenuResponse;
import carsharing.menu.MenuUtils;
import carsharing.model.Customer;
import carsharing.service.CustomerService;

import java.text.ParseException;
import java.util.List;

public class CustomerMenu implements Menu {

    private final Menu parent;

    private final CustomerService service;

    public CustomerMenu(Menu parent, CustomerService service) {
        this.parent = parent;
        this.service = service;
    }

    @Override
    public MenuResponse launch() {
        System.out.println("=== CUSTOMER MENU ===");
        MenuUtils.printNumberedOptionsList(getOptions());

        // get list of customers
        List<Customer> customers = service.findAll();

        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
            return new MenuResponse(parent); // return to main menu
        }

        MenuUtils.printNumberedModelList(customers);


        while (true) {
            try {
                int choice = MenuUtils.getNumericInput();
                if (choice == 0) return new MenuResponse(parent);
                System.out.println("Selected: " + customers.get(choice - 1).getName());
                return new MenuResponse(new RentMenu());
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid input!");
            }
        }
    }

    private List<Customer> getCustomers() {
        return service.findAll();
    }

    @Override
    public List<String> getOptions() {
        return MenuOptions.CUSTOMER.getOptions();
    }
}
