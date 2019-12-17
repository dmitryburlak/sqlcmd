package ua.com.juja.sqlcmd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ua.com.juja.sqlcmd.model.*;

import java.util.*;

@Component
public class ServiceImpl implements Servise {

    @Autowired
    private DatabaseConnect connectmanager;

    @Autowired
    private DatabaseManager manager;

    public DatabaseManager getManager() {
        return manager;
    }

    @Override
    public List<String> commandsList() {
        return Arrays.asList("help", "tables", "find", "create", "drop", "clear", "insert", "delete", "update");
    }

   @Override
    public void connect(String database, String userName, String password) throws ServiseException {
       try{
           connectmanager.connect(database, userName, password);
       } catch (Exception e) {
           throw new ServiseException("сonnection error ", e);
       }
    }

   @Override
    public List<List<String>> find(String tableName) throws ServiseException {
        List<List<String>> result = new LinkedList<>();
        try{
            getList(tableName, result);
            return result;
        } catch (Exception e){
            throw new ServiseException("find error ", e);
        }
    }

    private void getList(String tableName, List<List<String>> result) {
        List<String> columns = new LinkedList<>(manager.getTableCloumns(tableName));
        List<Map<String, Object>> tableData = manager.getTableDataSet(tableName);
        result.add(columns);
        for (Map<String, Object> data : tableData) {
            List<String> row = new ArrayList<>();
            for (Object column : data.values()) {
                row.add(String.valueOf(column));
            }
            result.add(row);
        }
    }

    @Override
    public String tables() throws ServiseException {
        try {
            Set<String> tableNames = manager.getTables();
            String tables = tableNames.toString();
            return tables;
        }catch (Exception e){
            throw new ServiseException("list tables error", e);
        }
    }

    @Override
    public void insert(String tableName, String column, String value, String columnsecond, String valuesecond) throws ServiseException {
        Map<String, Object> input = new LinkedHashMap<>();
        input.put(column, value);
        input.put(columnsecond, valuesecond);
        try{
            manager.insert(tableName, input);
        } catch (Exception e){
            throw new ServiseException("insert record error", e);
        }
    }

    @Override
    public void delete(String tableName, String column, String value) throws ServiseException {
        Map<String, Object> input = new LinkedHashMap<>();
        input.put(column, value);
        try{
            manager.delete(tableName, input);
        } catch (Exception e){
            throw new ServiseException("delete record error", e);
        }
    }

    @Override
    public void create(String tableName, String columnPk, String columnone, String columntwo) throws ServiseException {
        Set<String> input = new LinkedHashSet<>();
        input.add(columnone);
        input.add(columntwo);
        try{
            manager.create(tableName, columnPk, input);
        } catch (Exception e){
            throw new ServiseException("create table error", e);
        }
    }

    @Override
    public void drop(String tableName) throws ServiseException {
        try{
            manager.drop(tableName);
        } catch (Exception e){
            throw new ServiseException("drop table error", e);
        }
    }

    @Override
    public void update(String tableName, int id, String column, String value) throws ServiseException {
        Map<String, Object> input = new LinkedHashMap<>();
        input.put(column, value);
        try{
            manager.update(tableName, id, input);
        } catch (Exception e){
            throw new ServiseException("update record error", e);
        }
    }

    @Override
    public void clear(String tableName) throws ServiseException {
        try{
            manager.clear(tableName);
        } catch (Exception e){
            throw new ServiseException("clear table error", e);
        }
    }
}
