package ua.com.juja.sqlcmd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.juja.sqlcmd.model.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class ServiseCreate {

    @Autowired
    private JDBCDatabaseManager manager;

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
}
