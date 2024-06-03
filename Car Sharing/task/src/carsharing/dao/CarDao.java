package carsharing.dao;

import carsharing.db.DatabaseConnection;
import carsharing.model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDao implements Dao<Car> {

    private final DatabaseConnection dbConnection;
    private final Logger LOGGER = LoggerFactory.getLogger(CarDao.class);

    public CarDao(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        initTable();
    }

    @Override
    public List<Car> findAllById(long companyId) {
        String select = "SELECT * FROM car WHERE company_id = ?";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(select)) {
            ps.setLong(1, companyId);
            ResultSet rs = ps.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (rs.next()) {
                cars.add(new Car(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("company_id")));
            }
            return cars;
        } catch (SQLException e) {
            LOGGER.error("Error fetching all cars", e);
        }
        return List.of();
    }

    @Override
    public void save(Car car) {
        String insert = "INSERT INTO car (name, company_id) VALUES (?, ?)";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(insert)) {
            ps.setString(1, car.getName());
            ps.setLong(2, car.getCompanyId());
            ps.executeUpdate();
            LOGGER.info("CAR added - {}", car.getName());
        } catch (SQLException e) {
            LOGGER.error("Error adding CAR", e);
        }
    }

    @Override
    public List<Car> findAll() {
        System.err.println("Not implemented");
        return List.of();
    }

    private void initTable() {
        try (Statement stmt = dbConnection.getConnection().createStatement()) {
            String create = """
                    CREATE TABLE IF NOT EXISTS car (
                        id INTEGER PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(150) NOT NULL,
                        company_id INTEGER NOT NULL,
                        CONSTRAINT fk_company FOREIGN KEY (company_id)
                        REFERENCES company (id)
                        ON DELETE CASCADE
                    )""";
            stmt.execute(create);
            LOGGER.info("Created table CAR");
        } catch (SQLException e) {
            LOGGER.error("Error creating CAR table {}", e.getMessage());
        }
    }

}
