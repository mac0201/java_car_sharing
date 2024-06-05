package carsharing;

import carsharing.db.DatabaseConnection;
import carsharing.menu.MenuController;
import carsharing.menu.menus.MainMenu;
import carsharing.service.CompanyCarService;
import carsharing.service.CustomerService;

public class CarSharing {

    private final DatabaseConnection dbConnection;
    private CompanyCarService companyService;
    private CustomerService customerService;
    private MenuController menuController;

    public CarSharing(String dbName) {
        this.dbConnection = new DatabaseConnection(dbName);
    }

    private void init() {
        dbConnection.connect();
        this.companyService = new CompanyCarService(dbConnection);
        this.customerService = new CustomerService(dbConnection);
        this.menuController = new MenuController(companyService, customerService);
    }

    public void launch() {
        init();
        System.out.println("APP LAUNCHED");
        menuController.launch();
//        var menu = new MainMenu(companyService, customerService);
//        menu.launch();
    }





}
