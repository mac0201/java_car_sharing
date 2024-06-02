package carsharing.dao;

import carsharing.db.DatabaseConnection;
import carsharing.model.Company;
import carsharing.model.CompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao implements Dao<Company> {

    private final DatabaseConnection dbConnection;
    private final CompanyMapper companyMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

    public CompanyDao(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.initTable();
        this.companyMapper = getMapper();
    }

    @Override
    public List<Company> findAll() {
        try (Statement stmt = dbConnection.getConnection().createStatement()) {
            ResultSet results = stmt.executeQuery("SELECT * FROM company");
            List<Company> companies = new ArrayList<>();
            while (results.next()) {
                companies.add(companyMapper.mapCompany(results));
            }
            return companies;
        } catch (SQLException e) {
            LOGGER.error("Error fetching all COMPANIES", e);
        }
        return List.of();
    }

    @Override
    public void save(Company company) {
        String insert = "INSERT INTO COMPANY (name) VALUES (?)";
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(insert)) {
            ps.setString(1, company.getName());
            ps.executeUpdate();
            LOGGER.info("Company added to database - {}", company.getName());
        } catch (SQLException e) {
            LOGGER.error("Error adding company {} *** {}", company.getName(), e.getMessage());
        }
    }

    private CompanyMapper getMapper() {
        return rs -> new Company(rs.getInt("id"), rs.getString("name"));
    }

    private void initTable() {
        String create = """
            CREATE TABLE IF NOT EXISTS COMPANY (
            id INTEGER NOT NULL IDENTITY PRIMARY KEY,
            name VARCHAR(150) NOT NULL UNIQUE
            )
            """;
        try (Connection conn = dbConnection.connect();
            Statement stm = conn.createStatement()) {
            stm.execute(create);
            LOGGER.info("Created table COMPANY");
        } catch (SQLException e) {
            LOGGER.error("Error creating COMPANY table {}", e.getMessage());
        }
    }
}
