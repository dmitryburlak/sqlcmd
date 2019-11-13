package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.model.*;

import java.util.*;

public class ServiceImpl implements Servise {
    private DatabaseConnect connectmanager;

    public ServiceImpl(){
        connectmanager = new DatabaseConnect();
    }

    @Override
    public List<String> commandsList() {
        return Arrays.asList("help", "tables", "find", "create", "drop", "clear", "insert", "delete", "update");
    }

    @Override
    public DatabaseManager connect(String database, String userName, String password) {
        connectmanager.connect(database, userName, password);
        DatabaseManager manager = new JDBCDatabaseManager();
        return manager;
    }

   @Override
    public List<List<String>> find(DatabaseManager manager, String tableName) {
        List<List<String>> result = new LinkedList<>();
        List<String> columns = new LinkedList<>(manager.getTableCloumns(tableName));
        List<DataSet> tableData = manager.getTableDataSet(tableName);
        result.add(columns);
        for (DataSet dataset : tableData){
            List<String> row = new ArrayList<>(columns.size());
            result.add(row);
            for (String column : columns){
                row.add(dataset.get(column).toString());
            }
        }
        return result;
    }

    @Override
    public String tablesList(DatabaseManager manager) {
        Set<String> tableNames = manager.getTables();
        String tables = tableNames.toString();
        return tables;
    }

    @Override
    public void insert(DatabaseManager manager, String tableName, String column, String value, String columnsecond, String valuesecond) {
        DataSet dataSet = new DataSetImpl();
        dataSet.put(column, value);
        dataSet.put(columnsecond, valuesecond);
        manager.insert(tableName, dataSet);
    }
}
