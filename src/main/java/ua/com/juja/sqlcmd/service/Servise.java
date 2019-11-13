package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.model.DatabaseManager;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

public interface Servise {
    List<String> commandsList();

    DatabaseManager connect(String database, String userName, String password);

    List<List<String>> find(DatabaseManager manager, String tableName);

    String tablesList(DatabaseManager manager);

    void insert(DatabaseManager manager, String tableName, String column, String value, String columnsecond, String valuesecond);
}
