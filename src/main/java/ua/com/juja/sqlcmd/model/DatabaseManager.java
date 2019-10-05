package ua.com.juja.sqlcmd.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface DatabaseManager {

    List<DataSet> getTableDataSet(String tableName);

    int getSize(String tableName);

    Set<String> getTables();

    void clear(String tableName);

    void create(String tableName, DataSet input);

    void update(String tableName, int id, DataSet newValue);

    Set<String> getTableCloumns(String tableName);

    boolean isConnected();
}
