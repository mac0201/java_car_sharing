package carsharing.menu.menus;

import carsharing.Main;
import carsharing.menu.Menu;
import carsharing.menu.MenuOptions;
import carsharing.menu.MenuResponse;
import carsharing.menu.MenuUtils;
//import carsharing.menu.customer.CustomerMenu;
//import carsharing.menu.manager.ManagerMenu;
import carsharing.service.CompanyCarService;
import carsharing.service.CustomerService;

import java.util.List;

public class MainMenu implements Menu {

    private final CustomerService customerService;

    public MainMenu(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public MenuResponse launch() {

        System.out.println("=== MAIN MENU ===");
        MenuUtils.printNumberedOptionsList(getOptions());

        while (true) {
            int option = MenuUtils.getNumericInput();
            //! menu might not contain return, so check if options include return or add boolean to MenuOptions,
            //! indicating whether options have exit/back
            switch (option) {
                case 0 -> { return new MenuResponse(null); }
                case 1 -> { return new MenuResponse(new ManagerMenu(this)); }
                case 2 -> { return new MenuResponse(new CustomerMenu(this, customerService)); }
                case 3 -> {
//                    customerService.save();
//                    return new MenuResponse(new CustomerMenu(this));
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    @Override
    public List<String> getOptions() {
        return MenuOptions.MAIN.getOptions();
    }

//    @Override
//    public void launch() {
//        while (true) {
//            displayOptions();
//            int choice = MenuUtils.getNumericInput();
//            switch (choice) {
//                case 0 -> { }
//                case 1 -> {
//                    managerMenu.launch();
//                    return;
//                }
//                case 2 -> {
//                    // launch customer menu
//                    customerMenu.launch();
//                    return;
//                }
//                case 3 -> {
//                    ((CustomerMenu) customerMenu).addCustomer();
//                }
//                default -> System.out.println("Invalid choice!");
//            }
//        }
//    }
//
//    @Override
//    public void displayOptions() {
//        System.out.println("=== MAIN MENU ===");
//        MenuUtils.printNumberedOptionsList(MenuOptions.MAIN.getOptions());
//    }
}
