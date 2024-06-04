package carsharing.menus;

import carsharing.menus.customer.CustomerMenu;
import carsharing.menus.manager.ManagerMenu;
import carsharing.service.CompanyCarService;
import carsharing.service.CustomerService;

public class MainMenu implements Menu {

    private final CompanyCarService managerService;
    private final CustomerService customerService;

    private final Menu managerMenu;
    private final Menu customerMenu;

    public MainMenu(CompanyCarService managerService, CustomerService customerService) {
        this.managerService = managerService;
        this.customerService = customerService;
        this.managerMenu = new ManagerMenu(managerService, this);
        this.customerMenu = new CustomerMenu(customerService, this);
    }

    @Override
    public void launch() {
        while (true) {
            displayOptions();
            int choice = MenuUtils.getNumericInput();
            switch (choice) {
                case 0 -> { // exit app
                    managerService.closeAllConnections(); // close from carSharing
                    MenuUtils.closeScanner();
                    System.exit(0);
                }
                case 1 -> {
                    managerMenu.launch();
                    return;
                }
                case 2 -> {
                    // launch customer menu
                    customerMenu.launch();
                    return;
                }
                case 3 -> {
                    ((CustomerMenu) customerMenu).addCustomer();
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    @Override
    public void displayOptions() {
        System.out.println("""
                \n=== MAIN MENU ===
                  1. Log in as a manager
                  2. Log in as a customer
                  3. Create a customer
                  0. Exit
                """);
    }
}
