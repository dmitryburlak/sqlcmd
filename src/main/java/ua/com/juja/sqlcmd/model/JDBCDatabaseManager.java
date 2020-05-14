package ua.com.juja.sqlcmd.model;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;


import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JDBCDatabaseManager implements DatabaseManager {

    private JdbcTemplate jdbcTemplate(){
        return DatabaseConnect.getJdbcTemplate();
    }

    @Override
    public List<Map<String, Object>> getTableDataSet(String tableName) {
        String sqlQuery = "SELECT * FROM public.";
        return jdbcTemplate().query(sqlQuery + tableName,
                (res, row) -> {
                    ResultSetMetaData resMd = res.getMetaData();
                    Map<String, Object> data = new LinkedHashMap<>();
                    for (int i = 1; i <= resMd.getColumnCount(); i++) {
                        data.put(resMd.getColumnName(i), res.getObject(i));
                    }
                    return data;
                });
    }

    @Override
    public int getSize(String tableName) {
        String sqlQuery = "SELECT COUNT (*) FROM public.";
        return jdbcTemplate().queryForObject(sqlQuery + tableName, Integer.class);
    }

    @Override
    public Set<String> getTables() {
        String sqlQuery = "SELECT table_name FROM information_schema.tables WHERE table_schema NOT IN ('information_schema','pg_catalog')";
        return new LinkedHashSet<>(jdbcTemplate().query(sqlQuery,
                (res, row) -> res.getString("table_name")));
    }

    @Override
    public void clear(String tableName) {
        String sqlQuery = "DELETE FROM public.";
        jdbcTemplate().execute(sqlQuery + tableName);
    }

    @Override
    public void insert(String tableName, Map<String, Object> input) {
        String tableNames = getInputKey(input);
        String values = getInputValue(input);
        String sqlQuery = String.format("INSERT INTO public.%s (%s)" +
                "VALUES (%s)", tableName, tableNames, values);
        jdbcTemplate().update(sqlQuery);
    }

    @Override
    public void create(String tableName, String keyName, Set<String> input){
        String columnsName = input.stream()
                .collect(Collectors.joining(" varchar(225), ", "", " varchar(225)"));

        String sqlQuery = String.format("CREATE TABLE %s ( %s SERIAL PRIMARY KEY NOT NULL, %s)",
                tableName, keyName, columnsName);
        jdbcTemplate().update(sqlQuery);
    }

    @Override
    public void delete(String tableName, Map<String, Object> input){
        String columnName = getInputKey(input);
        String columnValue = getInputValue(input);
        String sqlQuery = String.format("DELETE FROM %s WHERE %s = %s",
                tableName, columnName, columnValue);
        jdbcTemplate().update(sqlQuery);
    }

    @Override
    public void drop(String tableName){
        String sqlQuery = "DROP TABLE ";
        jdbcTemplate().execute(sqlQuery + tableName);
    }

    @Override
    public void update(String tableName, int id, Map<String, Object> newValue) {
        String tableNames = newValue.entrySet().stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.joining("", "", " = ?"));

        List<Object> objects = new LinkedList<>(newValue.values());
        objects.add(id);

        String sqlQuery = String.format("UPDATE public.%s SET %s WHERE id = ?", tableName, tableNames);
        jdbcTemplate().update(sqlQuery, objects.toArray());
    }

    @Override
    public Set<String> getTableCloumns(String tableName) {
        String sqlQuery = "SELECT * FROM information_schema.columns WHERE table_schema = 'public' AND table_name = '";
        return new LinkedHashSet<>(jdbcTemplate().query(sqlQuery + tableName + "'",
                (res, row) -> res.getString("column_name")));
    }

   @Override
    public boolean isConnected() {
        return Objects.nonNull(DatabaseConnect.getConnection()) ? true : false;
    }

    @Override
    public String getDbName() {
        return DatabaseConnect.getDbName();
    }

    @Override
    public String getUserName() {
        return DatabaseConnect.getUserName();
    }

    private String getInputKey(Map<String, Object> input) {
        return input.entrySet().stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));
    }

    private String getInputValue(Map<String, Object> input) {
        return input.entrySet().stream()
                .map(Map.Entry::getValue)
                .map(String::valueOf)
                .collect(Collectors.joining("', '", "'", "'"));
    }
}



