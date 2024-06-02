package carsharing.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    static final String JDBC_DRIVER = "org.h2.Driver";
    private final String DATABASE_NAME;
    private final String DB_URL;
    private Connection connection;

    public DatabaseConnection(String databaseName) {
        this.DATABASE_NAME = databaseName;
        this.DB_URL = "jdbc:h2:./src/carsharing/db/" + DATABASE_NAME;
    }

    public Connection connect() {
        try {
            System.out.println("Connecting to database - " + DATABASE_NAME);
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
            System.out.println("Connected!");
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
