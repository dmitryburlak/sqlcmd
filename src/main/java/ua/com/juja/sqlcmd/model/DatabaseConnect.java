package ua.com.juja.sqlcmd.model;

import ua.com.juja.sqlcmd.view.MessageList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {

    private static Connection connection;

    public static Connection connect(String database, String userName, String password) {

        ConnectProperties connectProperties = new ConnectProperties().readProperties();
        String driver = connectProperties.getDriver();
        String host = connectProperties.getHost();

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(MessageList.DRIVER_NOT_FOUND.getMessage() + e);
        }
        try {
            connection = DriverManager.getConnection(host + database, userName,
                    password);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(String.format(MessageList.NOT_CONNECTION.getMessage(),
                    database, userName), e);
        }
        return connection;
    }
    public static Connection getConnection() {
        return connection;
    }
}
