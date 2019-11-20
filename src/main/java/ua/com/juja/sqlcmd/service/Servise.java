package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.model.DatabaseManager;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

public interface Servise {
    List<String> commandsList();

    DatabaseManager connect(String database, String userName, String password) throws ServiseException;

    List<List<String>> find(DatabaseManager manager, String tableName) throws ServiseException;

    String tables(DatabaseManager manager) throws ServiseException;

    void insert(DatabaseManager manager, String tableName, String column, String value, String columnsecond, String valuesecond) throws ServiseException;

    void create(DatabaseManager manager, String tableName, String columnPk, String columnone, String columntwo) throws ServiseException;

    void delete(DatabaseManager manager, String tableName, String column, String value) throws ServiseException;

    void drop(DatabaseManager manager, String tableName) throws ServiseException;

    void update(DatabaseManager manager, String tableName, int id, String column, String value) throws ServiseException;
}
