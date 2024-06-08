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

    /**
     * Find all cars owned by the company with given id. Skips the cars that are booked.
     * @return List of non-booked cars
     */
    public List<Car> findAllByIdSkipBooked(long companyId) {
        String query = """
                SELECT car.* FROM car
                LEFT JOIN customer ON customer.rented_car_id = car.id
                WHERE customer.rented_car_id IS NULL
                    AND car.company_id = ?
                """;
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setLong(1, companyId);
            ResultSet rs = ps.executeQuery();
            return extractCarList(rs);
        } catch (SQLException e) {
            LOGGER.error("Error fetching all cars", e);
            return List.of();
        }
    }

    /**
     * Find all cars owned by the company with given id. Does not skip booked cars.
     * @return List of all cars
     */
    @Override
    public List<Car> findAllById(long companyId) {
        String select = "SELECT * FROM car WHERE company_id = ?";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(select)) {
            ps.setLong(1, companyId);
            ResultSet rs = ps.executeQuery();
            return extractCarList(rs);
        } catch (SQLException e) {
            LOGGER.error("Error fetching all cars", e);
        }
        return List.of();
    }

    // Extracts a list of cars from provided ResultSet
    private List<Car> extractCarList(ResultSet rs) throws SQLException {
        List<Car> cars = new ArrayList<>();
        while (rs.next()) {
            cars.add(new Car(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("company_id")));
        }
        return cars;
    }

    // Implementation not needed
    @Override
    public List<Car> findAll() {
        return List.of();
    }

    // Create the CAR table
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
