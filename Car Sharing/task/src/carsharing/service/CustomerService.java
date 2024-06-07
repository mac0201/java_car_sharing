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
    }

    public void save(Customer customer) {
        customerDao.save(customer);
    }

    public void save(String name) {
        customerDao.save(new Customer(name));
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    public boolean isRentingCar(long customerId) {
        return customerDao.getRentedCarId(customerId).isPresent();
    }

    public String getRentedCarInfo(long customerId) {
        return customerDao.findRentedCarInfo(customerId);
    }

    public void rentCar(long customerId, long carId) {
        customerDao.rentCar(customerId, carId);
    }

    public void returnCar(long customerId) {
        customerDao.returnCar(customerId);
    }

}
