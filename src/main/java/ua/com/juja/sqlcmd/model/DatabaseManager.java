package ua.com.juja.sqlcmd.model;

import ua.com.juja.sqlcmd.controller.web.NullDatabaseManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DatabaseManager {

    DatabaseManager NULL = new NullDatabaseManager();

    List<Map<String, Object>> getTableDataSet(String tableName);

    int getSize(String tableName);

    Set<String> getTables();

    void clear(String tableName);

    void insert(String tableName, Map<String, Object> input);

    void create(String tableName, String keyName, Set<String> input);

    void delete(String tableName, Map<String, Object> input);

    void drop(String tableName);

    void update(String tableName, int id, Map<String, Object> newValue);

    Set<String> getTableCloumns(String tableName);

    boolean isConnected();
}
