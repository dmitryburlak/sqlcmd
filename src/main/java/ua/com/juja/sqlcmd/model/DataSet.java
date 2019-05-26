package ua.com.juja.sqlcmd.model;

public interface DataSet {
    void put(String name, Object value);

    Object[] getValue();

    String[] getName();

    Object get(String name);

    void updateFrom(DataSet newValue);
}
