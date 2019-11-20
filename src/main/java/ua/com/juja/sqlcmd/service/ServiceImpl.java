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
    public DatabaseManager connect(String database, String userName, String password) throws ServiseException {
        try{
            connectmanager.connect(database, userName, password);
            DatabaseManager manager = new JDBCDatabaseManager();
            return manager;
        } catch (Exception e) {
            throw new ServiseException("Connection error ", e);
        }
    }

   @Override
    public List<List<String>> find(DatabaseManager manager, String tableName) throws ServiseException {
        List<List<String>> result = new LinkedList<>();
        try{
            getList(manager, tableName, result);
            return result;
        } catch (Exception e){
            throw new ServiseException("find error ", e);
        }
    }

    private void getList(DatabaseManager manager, String tableName, List<List<String>> result) {
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
    }

    @Override
    public String tables(DatabaseManager manager) throws ServiseException {
        try {
            Set<String> tableNames = manager.getTables();
            String tables = tableNames.toString();
            return tables;
        }catch (Exception e){
            throw new ServiseException("list tables error", e);
        }

    }

    @Override
    public void insert(DatabaseManager manager, String tableName, String column, String value, String columnsecond, String valuesecond) throws ServiseException {
        DataSet dataSet = new DataSetImpl();
        dataSet.put(column, value);
        dataSet.put(columnsecond, valuesecond);
        try{
            manager.insert(tableName, dataSet);
        } catch (Exception e){
            throw new ServiseException("insert record error", e);
        }
    }

    @Override
    public void delete(DatabaseManager manager, String tableName, String column, String value) throws ServiseException {
        DataSet dataSet = new DataSetImpl();
        dataSet.put(column, value);
        try{
            manager.delete(tableName, dataSet);
        } catch (Exception e){
            throw new ServiseException("delete record error", e);
        }
    }

    @Override
    public void create(DatabaseManager manager, String tableName, String columnPk, String columnone, String columntwo) throws ServiseException {
        DataSet keyName = new DataSetImpl();
        keyName.put(columnPk, "");
        DataSet dataSet = new DataSetImpl();
        dataSet.put(columnone, "");
        dataSet.put(columntwo, "");
        try{
            manager.create(tableName, keyName, dataSet);
        } catch (Exception e){
            throw new ServiseException("create table error", e);
        }
    }

    @Override
    public void drop(DatabaseManager manager, String tableName) throws ServiseException {
        try{
            manager.drop(tableName);
        } catch (Exception e){
            throw new ServiseException("drop table error", e);
        }
    }

    @Override
    public void update(DatabaseManager manager, String tableName, int id, String column, String value) throws ServiseException {
        DataSet dataSet = new DataSetImpl();
        dataSet.put(column, value);
        try{
            manager.update(tableName, id, dataSet);
        } catch (Exception e){
            throw new ServiseException("update record error", e);
        }
    }
}
