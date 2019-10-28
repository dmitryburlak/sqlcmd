package ua.com.juja.sqlcmd.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DatabaseManager {

    List<DataSet> getTableDataSet(String tableName);

    int getSize(String tableName);

    Set<String> getTables();

    void clear(String tableName);

    void insert(String tableName, DataSet input);

    void create(String tableName, DataSet keyName, DataSet input);

    void delete(String tableName, DataSet input);

    void drop(String tableName);

    void update(String tableName, int id, DataSet newValue);

    Set<String> getTableCloumns(String tableName);

    boolean isConnected();
}
