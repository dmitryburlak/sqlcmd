package ua.com.juja.sqlcmd.model;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class InMemoryDatabaseManager implements DatabaseManager {

    public static final String TABLE_NAME = "newlist";
    private DataSet[] data = new DataSet[1000];
    private int freeIndex = 0;

    @Override
    public DataSet[] getTableDataSet(String tablename) {
        validateTable(tablename);
        return Arrays.copyOf(data, freeIndex);
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
        data = new DataSet[1000];
        freeIndex = 0;
    }

    @Override
    public void create(String tableName, DataSet input) {
        validateTable(tableName);
        data[freeIndex] = input;
        freeIndex++;
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        for (int index = 0; index < freeIndex; index++) {
            Object objected = 0;
            if (objected.equals(data[index].get("id"))) objected = true;
            else objected = false;
            Object objectid = 0;
            if (objectid.equals(id)) objectid = true;
            else objectid = false;
            if (objected == objectid) {
                data[index].updateFrom(newValue);
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
