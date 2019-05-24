package ua.com.juja.sqlcmd.model;

import java.util.List;
import java.util.Set;

public interface DatabaseManager {

    List<DataSet> getTableDataSet(String tablename);

    Set<String> getTables();

    void connect(String database, String userName, String password);

    void clear(String tableName);

    void create(String tableName, DataSet input);

    void update(String tableName, int id, DataSet newValue);

    Set<String> getTableCloumns(String tableName);

    boolean isConnected();
}
