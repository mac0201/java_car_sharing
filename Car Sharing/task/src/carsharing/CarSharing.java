package carsharing;

import carsharing.db.DatabaseConnection;
import carsharing.menus.MainMenu;
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
        System.out.println("APP LAUNCHED");
        var menu = new MainMenu(companyService, customerService);
        menu.launch();
    }





}
