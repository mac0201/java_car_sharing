package carsharing.menu;

import carsharing.menu.misc.MenuOptions;
import carsharing.menu.misc.MenuUtils;
import carsharing.service.CompanyCarService;
import carsharing.service.CustomerService;

public class MainMenu implements Menu {


    private final CustomerService cs;
    private final CompanyCarService ccs;

    public MainMenu(CustomerService cs, CompanyCarService ccs) {
        this.cs = cs;
        this.ccs = ccs;
    }

    @Override
    public void launch() {
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            MenuOptions.MAIN.printOptionsNumbered();
            int choice = MenuUtils.scanInteger();
            switch (choice) {
                case 0 -> System.exit(0);
                case 1 -> { // manager
                    var managerMenu = new ManagerCompanyCarMenu(ccs);
                    managerMenu.launch();
                }
                case 2, 3 -> {

                    var customerMenu = new CustomerMenu(cs, ccs);

                    if (choice == 2) {
                        customerMenu.launch();
                    } else {
                        customerMenu.addCustomer();
                    }

                }
//                case 2 -> { // customer
//                    var customerMenu = new CustomerMenu(cs, ccs);
//                    customerMenu.launch();
//                }
//                case 3 -> addCustomer();
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    public void addCustomer() {
        String name = MenuUtils.scanString();
        cs.save(name);
        System.out.println("Added customer " + name);
    }


}
