package carsharing;

import carsharing.db.DatabaseConnection;
import carsharing.menu.MainMenu;
import carsharing.service.CompanyCarService;
import carsharing.service.CustomerService;

public class CarSharing {

    private final DatabaseConnection dbConnection;
    private CompanyCarService companyService;
    private CustomerService customerService;


    public CarSharing(String dbName) {
        this.dbConnection = new DatabaseConnection(dbName);
    }

    private void init() {
        dbConnection.connect();
        this.companyService = new CompanyCarService(dbConnection);
        this.customerService = new CustomerService(dbConnection);

    }

    public void launch() {
        init();

        var mainMenu = new MainMenu(customerService, companyService);
        mainMenu.launch();


//        while (true) {
//
//            menuController.setActiveMenu(new MainMenu());
//            menuController.displayMenu();
//            int choice = MenuUtils.getNumericInput();
//            menuController.handleInput(choice);
//
//        }

//
//        init();
//        System.out.println("APP LAUNCHED");
//        menuController.launch();
//        var menu = new MainMenu(companyService, customerService);
//        menu.launch();
    }





}
