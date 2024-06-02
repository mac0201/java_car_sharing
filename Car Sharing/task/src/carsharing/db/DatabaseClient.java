package carsharing.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import carsharing.model.Company;
import carsharing.model.CompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DatabaseClient {

    private final DatabaseConnection dbConnection;
    private final CompanyMapper mapper;
    Logger LOGGER = LoggerFactory.getLogger(DatabaseClient.class);

    public DatabaseClient(String dbName) {
        this.dbConnection = new DatabaseConnection(dbName);
        this.mapper = getMapper();
    }

    public void init() {
        try (Connection conn = getConnection()) {
            createTableCompany(conn);
        } catch (SQLException e) {
            LOGGER.error("Database connection failed", e);
        }
    }

    private void createTableCompany(Connection conn) {
        String create = """
            CREATE TABLE IF NOT EXISTS COMPANY (
            id INTEGER NOT NULL IDENTITY PRIMARY KEY,
            name VARCHAR(150) NOT NULL
            )
            """;
        try (Statement stm = conn.createStatement()) {
            stm.execute(create);
            LOGGER.info("Created table COMPANY");
        } catch (SQLException e) {
            LOGGER.error("Error creating COMPANY table {}", e.getMessage());
        }
    }

    public List<Company> findAll() {
        try (Connection conn = getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM COMPANY")
        ) {
            List<Company> companies = new ArrayList<>();
            while (rs.next()) {
                companies.add(mapper.mapCompany(rs));
            }
            return companies;
        } catch (SQLException e) {
            LOGGER.error("Error fetching all COMPANIES", e);
        }
        return List.of();
    }

    public void add(Company company) {
        String insert = "INSERT INTO COMPANY (name) VALUES (?)";
        try (Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(insert)) {
            ps.setString(1, company.getName());
            ps.executeUpdate();
            LOGGER.info("Company added to database - {}", company.getName());
        } catch (SQLException e) {
            LOGGER.error("Error adding company {} *** {}", company.getName(), e.getMessage());
        }
    }

    private Connection getConnection() throws RuntimeException {
        return dbConnection.connect();
    }

    private CompanyMapper getMapper() {
        return rs -> new Company(rs.getInt("id"), rs.getString("name"));
    }
}
