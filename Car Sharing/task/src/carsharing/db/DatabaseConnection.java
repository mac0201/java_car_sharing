package carsharing.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    static final String JDBC_DRIVER = "org.h2.Driver";
    private final String DATABASE_NAME;
    private final String DB_URL;
    private Connection connection;

    private final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnection.class);

    public DatabaseConnection(String databaseName) {
        this.DATABASE_NAME = databaseName;
        this.DB_URL = "jdbc:h2:./src/carsharing/db/" + DATABASE_NAME;
    }

    public Connection connect() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
            LOGGER.info("Database connection with '{}' established.", DATABASE_NAME);
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error("Database connection with '{}' failed.", DATABASE_NAME, e);
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        if (this.connection == null) {
            return this.connect();
        }
        return connection;
    }

    public void close() {
        try {
            if (connection != null) connection.close();
            LOGGER.info("Connection closed with database '{}'.", DATABASE_NAME);
        } catch (SQLException e) {
            LOGGER.warn("Unable to close connection with database '{}'.", DATABASE_NAME, e);
        }
    }

}
