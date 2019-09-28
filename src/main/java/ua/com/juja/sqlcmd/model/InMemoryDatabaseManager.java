package ua.com.juja.sqlcmd.model;

import java.sql.SQLException;
import java.util.*;

public class InMemoryDatabaseManager implements DatabaseManager {

    private Map<String, List<DataSet>> tables = new LinkedHashMap<>();

    @Override
    public List<DataSet> getTableDataSet(String tableName) {
        return get(tableName);
    }

    @Override
    public int getSize(String tableName) {
        return get(tableName).size();
    }


    @Override
    public Set<String> getTables() {
        return tables.keySet();
    }

    @Override
    public void connect(String database, String userName, String password) {

    }

    @Override
    public void clear(String tableName) {
        get(tableName).clear();
    }

    private List<DataSet> get(String tableName) {
        if (!tables.containsKey(tableName)) {
            tables.put(tableName, new LinkedList<DataSet>());
        }
        return tables.get(tableName);
    }

    @Override
    public void create(String tableName, DataSet input) {
        get(tableName).add(input);
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        for (int index = 0; index < get(tableName).size(); index++) {
            DataSet dataSet = get(tableName).get(index);
            Object objected = 0;
            if (objected.equals(dataSet.get("id"))) objected = true;
            else objected = false;
            Object objectid = 0;
            if (objectid.equals(id)) objectid = true;
            else objectid = false;
            if (objected == objectid) {
                dataSet.updateFrom(newValue);
            }
        }
    }

    @Override
    public Set<String> getTableCloumns(String tableName) {
        return new LinkedHashSet<String>(Arrays.asList("id", "name", "password"));
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
