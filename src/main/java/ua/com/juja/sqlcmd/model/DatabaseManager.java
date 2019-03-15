package ua.com.juja.sqlcmd.model;

public interface DatabaseManager {
    DataSet[] getTableDataSet(String tablename);

    String[] getTables();

    void connect(String database, String userName, String password);

    void clear(String tableName);


    void create(String tableName, DataSet input);

    void update(String tableName, int id, DataSet newValue);

    String[] getTableCloumns(String tableName);

    boolean isConnected();
}
