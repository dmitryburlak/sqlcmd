package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.entity.UserAction;

import java.util.List;

public interface ServiceComponent {
    List<String> commandsList();

    void connect(String database, String userName, String password) throws ServiceComponentException;

    List<List<String>> find(String tableName) throws ServiceComponentException;

    String tables() throws ServiceComponentException;

    void insert(String tableName, String column, String value, String columnsecond,
                String valuesecond) throws ServiceComponentException;

    void create(String tableName, String columnPk, String columnone,
                String columntwo) throws ServiceComponentException;

    void delete(String tableName, String column, String value) throws ServiceComponentException;

    void drop(String tableName) throws ServiceComponentException;

    void update(String tableName, int id, String column, String value) throws ServiceComponentException;

    void clear(String tableName) throws ServiceComponentException;

    DatabaseManager getManager();

    List<UserAction> getAllFor(String userName);
}
