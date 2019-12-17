package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.model.DatabaseManager;

import java.util.List;

public interface Servise {
    List<String> commandsList();

    void connect(String database, String userName, String password) throws ServiseException;

    List<List<String>> find(String tableName) throws ServiseException;

    String tables() throws ServiseException;

    void insert(String tableName, String column, String value, String columnsecond,
                String valuesecond) throws ServiseException;

    void create(String tableName, String columnPk, String columnone,
                String columntwo) throws ServiseException;

    void delete(String tableName, String column, String value) throws ServiseException;

    void drop(String tableName) throws ServiseException;

    void update(String tableName, int id, String column, String value) throws ServiseException;

    void clear(String tableName) throws ServiseException;

    DatabaseManager getManager();
}
