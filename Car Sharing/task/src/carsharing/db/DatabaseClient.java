package carsharing.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import carsharing.model.Company;

public class DatabaseClient {

    // JDBC driver name
    static final String JDBC_DRIVER = "org.h2.Driver";

    private final String DATABASE_NAME;
    private final String DB_URL;

    public DatabaseClient(String dbName) {
        this.DATABASE_NAME = dbName != null ? dbName : "carsharing";
        this.DB_URL = "jdbc:h2:./src/carsharing/db/" + DATABASE_NAME;
    }

    public void init() {
        System.out.println("Connecting to database - " + DATABASE_NAME);
        try (Connection conn = getConnection()) {
            System.out.println("Connected");
            conn.setAutoCommit(true);
            createTableCompany(conn);
        } catch (SQLException e) {
            e.printStackTrace();
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
            System.out.println("Creating table COMPANY...");
            stm.execute(create);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public List<Company> findAll() {
        try (Connection conn = getConnection();
            Statement stm = conn.createStatement()) {
            ResultSet rs = stm.executeQuery("SELECT * FROM COMPANY");
            List<Company> companies = new ArrayList<>();
            while (rs.next()) {
                var company = new Company(rs.getString("name"));
                company.setId(rs.getInt("id"));
                companies.add(company);
            }
            return companies;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    public void add(Company company) {
        String insert = "INSERT INTO COMPANY (name) VALUES (?)";
        try (Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(insert)) {
            ps.setString(1, company.getName());
            ps.executeUpdate();
            System.out.println("ADDED!");
        } catch (SQLException e) {
            e.getMessage();
        }
    }


    private Connection getConnection() throws RuntimeException {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(true);
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to establish database connection", e);
        }
    }
}
