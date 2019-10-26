package ua.com.juja.sqlcmd.model;


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
        for (DataSet dataSet: get(tableName)) {
            if (dataSet.get("id").equals(id)){
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
