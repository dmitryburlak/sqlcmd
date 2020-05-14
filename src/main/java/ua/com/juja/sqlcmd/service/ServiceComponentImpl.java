package ua.com.juja.sqlcmd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.juja.sqlcmd.model.*;
import ua.com.juja.sqlcmd.model.entity.UserAction;
import java.util.*;

@Service
@Transactional
public class ServiceComponentImpl implements ServiceComponent {
    private final DatabaseConnect connectManager;
    private final DatabaseManager manager;
    private final UserActionRepository userActions;

    @Autowired
    public ServiceComponentImpl(DatabaseConnect connectManager,
                                DatabaseManager manager,
                                UserActionRepository userActions) {
        this.connectManager = connectManager;
        this.manager = manager;
        this.userActions = userActions;
    }

    public DatabaseManager getManager() {
        return manager;
    }

    @Override
    public List<String> commandsList() {
        return Arrays.asList("help", "tables", "find", "create", "drop", "clear", "insert", "delete", "update");
    }

   @Override
    public void connect(String database, String userName, String password) throws ServiceComponentException {
       try{
           connectManager.connect(database, userName, password);
           userActions.createAction(manager.getUserName(), manager.getDbName(), "CONNECT");
       } catch (Exception e) {
           throw new ServiceComponentException("сonnection error ", e);
       }
   }

    @Override
    public List<List<String>> find(String tableName) throws ServiceComponentException {
        List<List<String>> result = new LinkedList<>();
        try{
            getList(tableName, result);
            userActions.createAction(manager.getUserName(), manager.getDbName(), "FIND (" + tableName + ")");
            return result;
        } catch (Exception e){
            throw new ServiceComponentException("find error ", e);
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
    public String tables() throws ServiceComponentException {
        try {
            Set<String> tableNames = manager.getTables();
            String tables = tableNames.toString();
            userActions.createAction(manager.getUserName(), manager.getDbName(), "LIST TABLES");
            return tables;
        }catch (Exception e){
            throw new ServiceComponentException("list tables error", e);
        }
    }

    @Override
    public void insert(String tableName, String column, String value, String columnsecond, String valuesecond) throws ServiceComponentException {
        Map<String, Object> input = new LinkedHashMap<>();
        input.put(column, value);
        input.put(columnsecond, valuesecond);
        try{
            manager.insert(tableName, input);
            userActions.createAction(manager.getUserName(), manager.getDbName(), "INSERT DATA");
        } catch (Exception e){
            throw new ServiceComponentException("insert record error", e);
        }
    }

    @Override
    public void delete(String tableName, String column, String value) throws ServiceComponentException {
        Map<String, Object> input = new LinkedHashMap<>();
        input.put(column, value);
        try{
            manager.delete(tableName, input);
            userActions.createAction(manager.getUserName(), manager.getDbName(), "DELETE DATA");
        } catch (Exception e){
            throw new ServiceComponentException("delete record error", e);
        }
    }

    @Override
    public void create(String tableName, String columnPk, String columnone, String columntwo) throws ServiceComponentException {
        Set<String> input = new LinkedHashSet<>();
        input.add(columnone);
        input.add(columntwo);
        try{
            manager.create(tableName, columnPk, input);
            userActions.createAction(manager.getUserName(), manager.getDbName(), "CREATE TABLE");
        } catch (Exception e){
            throw new ServiceComponentException("create table error", e);
        }
    }

    @Override
    public void drop(String tableName) throws ServiceComponentException {
        try{
            manager.drop(tableName);
            userActions.createAction(manager.getUserName(), manager.getDbName(), "DROP TABLE");
        } catch (Exception e){
            throw new ServiceComponentException("drop table error", e);
        }
    }

    @Override
    public void update(String tableName, int id, String column, String value) throws ServiceComponentException {
        Map<String, Object> input = new LinkedHashMap<>();
        input.put(column, value);
        try{
            manager.update(tableName, id, input);
            userActions.createAction(manager.getUserName(), manager.getDbName(), "UPDATE DATA");
        } catch (Exception e){
            throw new ServiceComponentException("update record error", e);
        }
    }

    @Override
    public void clear(String tableName) throws ServiceComponentException {
        try{
            manager.clear(tableName);
            userActions.createAction(manager.getUserName(), manager.getDbName(), "CLEAR TABLE");
        } catch (Exception e){
            throw new ServiceComponentException("clear table error", e);
        }
    }

    @Override
    public List<UserAction> getAllFor(String userName){
        if(Objects.isNull(userName)){
            throw new IllegalArgumentException("userName cant be null");
        }
        return userActions.findByName(userName);
    }
}
