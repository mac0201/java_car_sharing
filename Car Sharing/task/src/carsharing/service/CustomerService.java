package carsharing.service;

import carsharing.dao.CustomerDao;
import carsharing.db.DatabaseConnection;
import carsharing.model.Customer;

import java.util.List;
import java.util.Map;

public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(DatabaseConnection dbConn) {
        this.customerDao = new CustomerDao(dbConn);
    }

    public void save(String name) {
        customerDao.save(new Customer(name));
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    public boolean isRentingCar(long customerId) {
        return customerDao.isCustomerRenting(customerId);
    }

    public Map<String, String> findRentedCarData(long customerId) {
        return customerDao.findRentedCarData(customerId);
    }

    public void rentCar(long customerId, long carId) {
        customerDao.rentCar(customerId, carId);
    }

    public void returnCar(long customerId) {
        customerDao.returnCar(customerId);
    }

}
