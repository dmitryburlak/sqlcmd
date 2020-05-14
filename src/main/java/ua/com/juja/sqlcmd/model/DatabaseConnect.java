package ua.com.juja.sqlcmd.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static ua.com.juja.sqlcmd.message.MessageList.*;

@Component
public class DatabaseConnect {
    private static JdbcTemplate jdbcTemplate;
    private static Connection connection;
    private static String dbName;
    private static String userName;

    public Connection connect(String dbName, String userName, String password) {
        String host = setupDriver();
        try {
            connection = DriverManager.getConnection(host + dbName, userName, password);
            jdbcTemplate = new JdbcTemplate(
                    new SingleConnectionDataSource(connection, false));
            this.dbName = dbName;
            this.userName = userName;
        } catch (SQLException e) {
            connection = null;
            jdbcTemplate = null;
            throw new RuntimeException(String.format(NOT_CONNECTION.getMessage(),
                    dbName, userName), e);
        }
        return connection;
    }

    public String setupDriver() {
        ConnectProperties connectProperties = new ConnectProperties().readProperties();
        String driver = connectProperties.getDriver();
        String host = connectProperties.getHost();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(DRIVER_NOT_FOUND.getMessage() + e);
        }
        return host;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }

    public static String getDbName() {
        return dbName;
    }

    public static String getUserName() {
        return userName;
    }
}
