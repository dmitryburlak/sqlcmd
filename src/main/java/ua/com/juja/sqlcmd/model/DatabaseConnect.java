package ua.com.juja.sqlcmd.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static ua.com.juja.sqlcmd.view.MessageList.*;

public class DatabaseConnect {

    private static Connection connection;

    public static Connection connect(String database, String userName, String password) {
        String host = setupDriver();
        try {
            connection = DriverManager.getConnection(host + database, userName,
                    password);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(String.format(NOT_CONNECTION.getMessage(),
                    database, userName), e);
        }
        return connection;
    }

    private static String setupDriver() {
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
}
