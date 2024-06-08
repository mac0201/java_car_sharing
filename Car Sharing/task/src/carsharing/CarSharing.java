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
        this.dbConnection = DatabaseConnection.getInstance(dbName);
    }

    private void init() {
        dbConnection.getConnection();
        this.companyService = new CompanyCarService(dbConnection);
        this.customerService = new CustomerService(dbConnection);
    }

    public void launch() {
        init();
        new MainMenu(customerService, companyService)
                .launch();
        dbConnection.close();
    }
}
