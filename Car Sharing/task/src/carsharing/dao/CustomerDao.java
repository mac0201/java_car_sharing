package carsharing.dao;

import carsharing.db.DatabaseConnection;
import carsharing.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class CustomerDao implements Dao<Customer> {

    private final DatabaseConnection dbConnection;
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerDao.class);

    public CustomerDao(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        initTable();
    }

    @Override
    public void save(Customer customer) {
        String insert = "INSERT INTO customer (name) VALUES (?)";
        try (PreparedStatement stm = dbConnection.getConnection().prepareStatement(insert)) {
            stm.setString(1, customer.getName());
            stm.executeUpdate();
            LOGGER.info("Created customer");
        } catch (SQLException e) {
            LOGGER.error("Failed adding customer {}", customer.getName());
        }
    }

    /**
     * Find all existing customers
     * @return List containing Customer instances
     */
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

    /**
     * Rent a car for customer
     */
    public void rentCar(long customerId, long carId) {
        String query = "UPDATE customer SET rented_car_id = ? WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(query)) {
            stmt.setLong(1, carId);
            stmt.setLong(2, customerId);
            stmt.executeUpdate();
            LOGGER.info("Customer {} has rented car '{}' of company X", customerId, carId);
        } catch (SQLException e) {
            LOGGER.error("Failed renting car '{}' by customer {}", carId, customerId);
        }
    }

    /**
     * Return customer's currently rented car
     */
    public void returnCar(long customerId) {
        String query = "UPDATE customer SET rented_car_id = null WHERE id = ?";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setLong(1, customerId);
            ps.executeUpdate();
            LOGGER.info("Customer {} has returned car", customerId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if customer with provided id is currently renting a car.
     * @return true if customer renting, else false
     */
    public boolean isCustomerRenting(long customerId) {
        String query = "SELECT rented_car_id FROM customer WHERE id = ?";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setLong(1, customerId);
            var rs = ps.executeQuery();
            if (rs.next()) {
                Long carId = rs.getObject("rented_car_id", Long.class);
                return carId != null;
            }
            return false;
        } catch (SQLException e) {
            LOGGER.error("Failed checking if customer is renting {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds information about customer's rented car - car name and company name.
     * @return Map with keys 'car' and 'company' which include the names respectively if data was found, else empty map
     */
    public Map<String, String> findRentedCarData(long customerId) {
        String query = """
                SELECT car.name, company.name 
                FROM customer
                JOIN car
                    ON customer.rented_car_id = car.id
                JOIN company
                    ON car.company_id = company.id
                WHERE customer.id = ?""";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setLong(1, customerId);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return Map.of(
                        "car", rs.getString("car.name"),
                        "company", rs.getString("company.name"));
            }
            return Map.of();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Create the CUSTOMER table
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

    // Implementation not needed
    @Deprecated
    @Override
    public List<Customer> findAllById(long id) {
        return List.of();
    }

}
