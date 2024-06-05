package carsharing.service;

import carsharing.dao.CustomerDao;
import carsharing.db.DatabaseConnection;
import carsharing.model.Customer;

import java.util.List;

public class CustomerService {

    // customer dao
    private final CustomerDao customerDao;
    private final DatabaseConnection dbConn;
//    private final CompanyCarService companyService;

    public CustomerService(DatabaseConnection dbConn) { // CompanyCarService companyService
        this.dbConn = dbConn;
        this.customerDao = new CustomerDao(dbConn);
//        this.companyService = companyService;
    }



    public void save(Customer customer) {
        customerDao.save(customer);
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    public String getRentedCarInfo(long customerId) {
        return customerDao.getRentedCarInfo(customerId);
    }

    public void rentCar(long customerId, long carId) {
        customerDao.rentCar(customerId, carId);
    }

}
