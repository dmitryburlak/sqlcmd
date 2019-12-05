package ua.com.juja.sqlcmd.controller.web;

import ua.com.juja.sqlcmd.model.DatabaseManager;

import java.util.*;

public class NullDatabaseManager implements DatabaseManager {

    @Override
    public List<Map<String, Object>> getTableDataSet(String tableName) {
        return new LinkedList<>();
    }

    @Override
    public int getSize(String tableName) {
        return 0;
    }

    @Override
    public Set<String> getTables() {
        return new HashSet<>();
    }

    @Override
    public void clear(String tableName) {
        //do nothing
    }

    @Override
    public void insert(String tableName, Map<String, Object> input) {
        //do nothing
    }

    @Override
    public void create(String tableName, String keyName, Set<String> input) {
        //do nothing
    }

    @Override
    public void delete(String tableName, Map<String, Object> input) {
        //do nothing
    }

    @Override
    public void drop(String tableName) {
        //do nothing
    }

    @Override
    public void update(String tableName, int id, Map<String, Object> newValue) {
        //do nothing
    }

    @Override
    public Set<String> getTableCloumns(String tableName) {
        return new HashSet<>();
    }

    @Override
    public boolean isConnected() {
        return false;
    }
}
