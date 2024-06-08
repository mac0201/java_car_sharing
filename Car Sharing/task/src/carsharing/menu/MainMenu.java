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
                case 0 -> {
                    MenuUtils.closeScanner();
                    return;
                }
                case 1 -> new ManagerCompanyCarMenu(ccs).launch(); // launch manager menu
                case 2, 3 -> {
                    var customerMenu = new CustomerMenu(cs, ccs);
                    // if '2' launch customer menu, else invoke only the add customer method
                    if (choice == 2)  customerMenu.launch();
                    else customerMenu.addCustomer();
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
