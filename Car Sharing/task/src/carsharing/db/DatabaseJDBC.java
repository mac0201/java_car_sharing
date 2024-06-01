package carsharing.db;

import java.sql.*;

import org.h2.Driver;

public class DatabaseJDBC {

    // JDBC driver name
    static final String JDBC_DRIVER = "org.h2.Driver";

    private final String DATABASE_NAME;
    private final String DB_URL;

    public DatabaseJDBC(String dbName) {
        this.DATABASE_NAME = dbName != null ? dbName : "carsharing";
        this.DB_URL = "jdbc:h2:./src/carsharing/db/" + DATABASE_NAME;
    }

    public void init() {
        System.out.println("Connecting to database - " + DATABASE_NAME);
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
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
            id INTEGER NOT NULL UNIQUE,
            name VARCHAR(150),
            PRIMARY KEY (id)
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
}
