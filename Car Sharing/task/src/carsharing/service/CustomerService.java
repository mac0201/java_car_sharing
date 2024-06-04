package carsharing.service;

import carsharing.dao.CustomerDao;
import carsharing.db.DatabaseConnection;
import carsharing.model.Customer;

import java.util.List;

public class CustomerService {

    // customer dao
    private final CustomerDao customerDao;
    private final DatabaseConnection dbConn;

    public CustomerService(DatabaseConnection dbConn) {
        this.dbConn = dbConn;
        this.customerDao = new CustomerDao(dbConn);
    }



    public void save(Customer customer) {
        customerDao.save(customer);
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

}
