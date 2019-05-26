package ua.com.juja.sqlcmd.model;

import java.sql.*;
import java.util.*;

import static java.lang.Class.*;


public class JDBCDatabaseManager implements DatabaseManager {

    private Connection connection;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {


    }

    @Override
    public List<DataSet> getTableDataSet(String tableName) {
        List<DataSet> result = new LinkedList<DataSet>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM public." + tableName);
            ResultSetMetaData resmd = res.getMetaData();
            while (res.next()) {
                DataSet dataSet = new DataSetImpl();
                result.add(dataSet);
                for (int i = 1; i <= resmd.getColumnCount(); i++) {
                    dataSet.put(resmd.getColumnName(i), res.getObject(i));
                }
            }
            res.close();
            stmt.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

    private int getSize(String tablename) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet resCount = stmt.executeQuery("SELECT COUNT (*) FROM public." + tablename);
        resCount.next();
        int size = resCount.getInt(1);
        resCount.close();
        return size;
    }

    @Override
    public Set<String> getTables() {
        Set<String> tables = new LinkedHashSet<String>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT table_name FROM information_schema.tables\n" +
                    "WHERE table_schema NOT IN ('information_schema','pg_catalog')");
            while (res.next()) {
                tables.add(res.getString("table_name"));
            }
            res.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return tables;
        }
    }


    @Override
    public void connect(String database, String userName, String password) {
        try {
            forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("add jdbc driver to project" + e);
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + database, userName,
                    password);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(String.format("cant get connection for model:%s user:%s",
                    database, userName), e);
        }
    }

    private Connection getConnection() {
        return connection;

    }

    @Override
    public void clear(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM public." + tableName);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(String tableName, DataSet input) {

        try {
            Statement stmt = connection.createStatement();
            String tableNames = getNamesFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");
            stmt.executeUpdate("INSERT INTO public." + tableName + "(" + tableNames + ")" +
                    "VALUES (" + values + ")");
            stmt.close();
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
        try {
            String tableNames = getNamesFormated(newValue, "%s = ?,");
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE public." + tableName + " SET " + tableNames + "WHERE id = ?");

            int index = 1;
            for (Object value : newValue.getValue()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setInt(index, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> getTableCloumns(String tableName) {
        Set<String> tables = new LinkedHashSet<String>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM information_schema.columns WHERE table_schema = 'public' AND table_name = '" + tableName + "'");
            while (res.next()) {
                tables.add(res.getString("column_name"));
            }
            res.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return tables;
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
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



