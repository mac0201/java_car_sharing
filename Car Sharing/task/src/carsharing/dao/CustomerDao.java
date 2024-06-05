package carsharing.dao;

import carsharing.db.DatabaseConnection;
import carsharing.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao implements Dao<Customer> {

    private final DatabaseConnection dbConnection;
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerDao.class);

    public CustomerDao(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        initTable();
    }

    // find all customers
    @Override
    public List<Customer> findAll() {
        String select = "SELECT * FROM customer";
        List<Customer> customers = new ArrayList<>();
        try (Statement stm = dbConnection.getConnection().createStatement()) {
            ResultSet rs = stm.executeQuery(select);
            while (rs.next()) {
                customers.add(
                        new Customer(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getLong("rented_car_id")));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed retrieving all customers - {}", e.getMessage());
        }
        return customers;
    }

    // get rented car info
    public String getRentedCarInfo(long customerId) {

        String query1 = "SELECT rented_car_id FROM customer WHERE id = ?";

        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(query1)) {
            stmt.setLong(1, customerId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("RENTED CAR: " + rs.getString("rented_car_id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "yes";

    }

    // rent a car
    public void rentCar(long customerId, long carId) {

        String query = "UPDATE customer SET rented_car_id = ? WHERE id = ?";

        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(query)) {
            stmt.setLong(1, carId);
            stmt.setLong(2, customerId);
            stmt.executeUpdate();
            LOGGER.info("Customer {} has rented car '{}' of company X", customerId, carId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }









    @Override
    public List<Customer> findAllById(long id) {
        return List.of();
    }

    @Override
    public void save(Customer customer) {
        String insert = "INSERT INTO customer (name) VALUES (?)";
        try (PreparedStatement stm = dbConnection.getConnection().prepareStatement(insert)) {
            stm.setString(1, customer.getName());
            stm.executeUpdate();
            LOGGER.info("Created customer");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void findRentedCar(long carId) {
        String select =  "SELECT * FROM customer WHERE fk_car = ? JOIN TABLE car ON car.fk_car = car.fk_car";
    }

    private void initTable() {
        String create = """
                CREATE TABLE IF NOT EXISTS CUSTOMER (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(150) UNIQUE NOT NULL,
                    rented_car_id INT,
                    CONSTRAINT fk_car FOREIGN KEY (rented_car_id)
                    REFERENCES car (id)
                    ON DELETE SET NULL
                )
                """;

        try (Statement stm = dbConnection.getConnection().createStatement()) {
            stm.execute(create);
            LOGGER.info("Created table CUSTOMER");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
