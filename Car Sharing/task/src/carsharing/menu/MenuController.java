package carsharing.menu;

import carsharing.menu.menus.CustomerMenu;
import carsharing.menu.menus.MainMenu;
import carsharing.menu.menus.ManagerMenu;
import carsharing.service.CompanyCarService;
import carsharing.service.CustomerService;

public class MenuController {

    private final CompanyCarService companyCarService;
    private final CustomerService customerService;

    private Menu mainMenu;
    private Menu managerMenu;
    private Menu customerMenu;


    public MenuController(CompanyCarService companyCarService, CustomerService customerService) {
        this.companyCarService = companyCarService;
        this.customerService = customerService;

    }

    public void launch() {

        // run main menu
//        initMenus();

        Menu mainMenu = new MainMenu(customerService);
        var response = mainMenu.launch();
        while (true) {
            if (response.next() == null) System.exit(0);
            response = response.next().launch();
        }

    }

//    private void initMenus() {
//        this.mainMenu = new MainMenu(customerService);
//        this.managerMenu = new ManagerMenu(mainMenu);
//        this.customerMenu = new CustomerMenu(mainMenu);
//
//    }

    private Menu getMenu() {
        int option = 2;
        Menu m = switch (option) {
            case 1 -> managerMenu;
            case 2 -> customerMenu;
            default -> null;
        };
        return m;
    }


}
