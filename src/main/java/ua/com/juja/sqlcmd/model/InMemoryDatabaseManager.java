package ua.com.juja.sqlcmd.model;

import java.lang.reflect.Array;
import java.util.*;

public class InMemoryDatabaseManager implements DatabaseManager {

    public static final String TABLE_NAME = "newlist";
    private List<DataSet> data = new LinkedList<DataSet>();

    @Override
    public List<DataSet> getTableDataSet(String tablename) {
        validateTable(tablename);
        return data;
    }

    private void validateTable(String tablename) {
        if (!"newlist".equals(tablename)) {
            throw new UnsupportedOperationException("only for 'newlist'");
        }
    }

    @Override
    public Set<String> getTables() {
        return new LinkedHashSet<String>(Arrays.asList(TABLE_NAME));
    }

    @Override
    public void connect(String database, String userName, String password) {

    }

    @Override
    public void clear(String tableName) {
        validateTable(tableName);
        data.clear();

    }

    @Override
    public void create(String tableName, DataSet input) {
        validateTable(tableName);
        data.add(input);
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        validateTable(tableName);
        for (int index = 0; index < data.size(); index++) {
            DataSet dataSet = data.get(index);
          /*  if(id == dataSet.get("id")){
               dataSet.updateFrom(newValue);
            }*/
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
        return new LinkedHashSet<String>(Arrays.asList("id", "name", "lastname"));
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
