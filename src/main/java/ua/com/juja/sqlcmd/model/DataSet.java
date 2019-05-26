package ua.com.juja.sqlcmd.model;

import java.util.List;
import java.util.Set;

public interface DataSet {
    void put(String name, Object value);

    List<Object> getValue();

    Set<String> getName();

    Object get(String name);

    void updateFrom(DataSet newValue);
}
