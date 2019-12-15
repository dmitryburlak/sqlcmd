package ua.com.juja.sqlcmd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.juja.sqlcmd.model.DatabaseConnect;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

@Component
public class ServiseConnect {

    @Autowired
    private DatabaseConnect connectmanager;

    @Autowired
    private JDBCDatabaseManager manager;

    public void connect(String database, String userName, String password) throws ServiseException {
        try{
            connectmanager.connect(database, userName, password);
        } catch (Exception e) {
            throw new ServiseException("сonnection error ", e);
        }
    }

    public JDBCDatabaseManager getManager() {
        return manager;
    }
    /*public DatabaseManager connect(String database, String userName, String password) throws ServiseException {
        try{
            connectmanager.connect(database, userName, password);
            if (connectmanager.getConnection() != null) {
                return manager;
            } else {
                throw new Exception("сonnection error ");
            }
        } catch (Exception e) {
            throw new ServiseException("сonnection error ", e);
        }
    }*/
}
