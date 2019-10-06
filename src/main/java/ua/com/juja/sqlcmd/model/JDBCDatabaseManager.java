package ua.com.juja.sqlcmd.model;

import java.sql.*;
import java.util.*;

public class JDBCDatabaseManager implements DatabaseManager {

    private Connection connection(){
        return DatabaseConnect.getConnection();
    }

    @Override
    public List<DataSet> getTableDataSet(String tableName) {
        String sqlquere = "SELECT * FROM public.";
        List<DataSet> result = new LinkedList<DataSet>();
        try (Statement stmt = connection().createStatement();
             ResultSet res = stmt.executeQuery(sqlquere + tableName)){
            ResultSetMetaData resmd = res.getMetaData();
            while (res.next()) {
                DataSet dataSet = new DataSetImpl();
                result.add(dataSet);
                for (int i = 1; i <= resmd.getColumnCount(); i++) {
                    dataSet.put(resmd.getColumnName(i), res.getObject(i));
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
        Set<String> tables = new LinkedHashSet<String>();
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
    public void create(String tableName, DataSet input) {
        try(Statement stmt = connection().createStatement()) {
            String tableNames = getNamesFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");
            stmt.executeUpdate("INSERT INTO public." + tableName + "(" + tableNames + ")" +
                    "VALUES (" + values + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getValuesFormated(DataSet input, String format) {
        String values = "";
        for (Object value : input.getValue()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        String tableNames = getNamesFormated(newValue, "%s = ?,");
        try(PreparedStatement ps = connection().prepareStatement(
                "UPDATE public." + tableName + " SET " + tableNames + "WHERE id = ?")) {
            int index = 1;
            for (Object value : newValue.getValue()) {
                ps.setObject(index, value);
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
        Set<String> tables = new LinkedHashSet<String>();
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

    private String getNamesFormated(DataSet newValue, String format) {
        String string = "";
        for (String name : newValue.getName()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }
}



