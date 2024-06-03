package carsharing.dao;

import carsharing.db.DatabaseConnection;
import carsharing.model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao implements Dao<Company> {

    private final DatabaseConnection dbConnection;
    private final Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

    public CompanyDao(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.initTable();
    }

    @Override
    public List<Company> findAll() {
        try (Statement stmt = dbConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM company");
            List<Company> companies = new ArrayList<>();
            while (rs.next()) {
                companies.add(new Company(rs.getInt("id"), rs.getString("name")));
            }
            return companies;
        } catch (SQLException e) {
            LOGGER.error("Error fetching all companies", e);
        }
        return List.of();
    }

    @Override
    public List<Company> findAllById(long id) {
        System.err.println("Not implemented");
        return List.of();
    }

    @Override
    public void save(Company company) {
        if (company.getName() == null || company.getName().isEmpty()) {
            LOGGER.error("Company name is null or empty");
            return;
        }
        String insert = "INSERT INTO COMPANY (name) VALUES (?)";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(insert)) {
            ps.setString(1, company.getName());
            ps.executeUpdate();
            LOGGER.info("COMPANY added - {}", company.getName());
        } catch (SQLException e) {
            LOGGER.error("Error adding company {} *** {}", company.getName(), e.getMessage());
        }
    }

    private void initTable() {
        String create = """
            CREATE TABLE IF NOT EXISTS COMPANY (
            id INTEGER PRIMARY KEY AUTO_INCREMENT,
            name VARCHAR(150) NOT NULL UNIQUE
            )
            """;
        try (Statement stm = dbConnection.getConnection().createStatement()) { // connection created in CarSharingService constructor
            stm.execute(create);
            LOGGER.info("Created table COMPANY");
        } catch (SQLException e) {
            LOGGER.error("Error creating COMPANY table {}", e.getMessage());
        }
    }
}
