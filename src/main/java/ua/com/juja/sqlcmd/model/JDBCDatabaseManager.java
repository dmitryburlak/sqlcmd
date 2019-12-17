package ua.com.juja.sqlcmd.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.sql.*;
import java.util.*;

@Component
public class JDBCDatabaseManager implements DatabaseManager {

    private Connection connection(){
        return DatabaseConnect.getConnection();
    }

    @Override
    public List<Map<String, Object>> getTableDataSet(String tableName) {
        String sqlquere = "SELECT * FROM public.";
        List<Map<String, Object>> result = new LinkedList<>();
        try (Statement stmt = connection().createStatement();
             ResultSet res = stmt.executeQuery(sqlquere + tableName)){
            ResultSetMetaData resmd = res.getMetaData();
            while (res.next()) {
                Map<String, Object> data = new LinkedHashMap<>();
                result.add(data);
                for (int i = 1; i <= resmd.getColumnCount(); i++) {
                    data.put(resmd.getColumnName(i), res.getObject(i));
                }
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }
    @Override
    public int getSize(String tableName) {
        String sqlquere = "SELECT COUNT (*) FROM public.";
        try (Statement stmt = connection().createStatement();
             ResultSet resCount = stmt.executeQuery(sqlquere + tableName)) {
            resCount.next();
            int size = resCount.getInt(1);
            return size;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Set<String> getTables() {
        String sqlquere = "SELECT table_name FROM information_schema.tables WHERE table_schema NOT IN ('information_schema','pg_catalog')";
        Set<String> tables = new LinkedHashSet<>();
        try (Statement stmt = connection().createStatement();
             ResultSet res = stmt.executeQuery(sqlquere)){
            while (res.next()) {
                tables.add(res.getString("table_name"));
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return tables;
        }
    }

    @Override
    public void clear(String tableName) {
        String sqlquere = "DELETE FROM public.";
        try(Statement stmt = connection().createStatement()){
            stmt.executeUpdate(sqlquere + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(String tableName, Map<String, Object> input) {
        try(Statement stmt = connection().createStatement()) {
            String tableNames = getNamesFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");
            stmt.executeUpdate("INSERT INTO public." + tableName + "(" + tableNames + ")" +
                    "VALUES (" + values + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void create(String tableName, String keyName, Set<String> input){
        try(Statement stmt = connection().createStatement()){
            String columnsName = "";
            String columnstype = " varchar(225)";
            for (String data: input) {
                columnsName += ", " + data + columnstype;
            }
            stmt.executeUpdate("CREATE TABLE " + tableName +
                    " ( " + keyName + " SERIAL PRIMARY KEY NOT NULL " + columnsName + ")");

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String tableName, Map<String, Object> input){
        try(Statement stmt = connection().createStatement()){
            String columnName = getNamesFormated(input, "%s,");
            String columnValue = getValuesFormated(input, "'%s',");
            stmt.executeUpdate("DELETE FROM " + tableName + " WHERE " + columnName + " = " + columnValue);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drop(String tableName){
        String sqlquere = "DROP TABLE ";
        try(Statement stmt = connection().createStatement()){
            stmt.executeUpdate(sqlquere + tableName);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String tableName, int id, Map<String, Object> newValue) {
        String tableNames = getNamesFormated(newValue, "%s = ?,");
        try(PreparedStatement ps = connection().prepareStatement(
                "UPDATE public." + tableName + " SET " + tableNames + " WHERE id = ?")) {
            int index = 1;
            for (Map.Entry value : newValue.entrySet()) {
                ps.setObject(index, value.getValue());
                index++;
            }
            ps.setInt(index, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> getTableCloumns(String tableName) {
        String sqlquere = "SELECT * FROM information_schema.columns WHERE table_schema = 'public' AND table_name = '";
        Set<String> tables = new LinkedHashSet<>();
        try(Statement stmt = connection().createStatement();
            ResultSet res = stmt.executeQuery(sqlquere + tableName + "'")) {
            while (res.next()) {
                tables.add(res.getString("column_name"));
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return tables;
        }
    }

   @Override
    public boolean isConnected() {
        return connection() != null;
    }

    private String getNamesFormated(Map<String, Object> input, String format) {
        String names = "";
        for (Map.Entry name : input.entrySet()) {
            names += String.format(format, name.getKey());
        }
        names = names.substring(0, names.length() - 1);
        return names;
    }

    private String getValuesFormated(Map<String, Object> input, String format) {
        String values = "";
        for (Map.Entry value : input.entrySet()) {
            values += String.format(format, value.getValue());
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }
}



