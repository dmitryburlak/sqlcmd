package ua.com.juja.sqlcmd.model;

import java.util.*;

public class DataSetImpl implements DataSet {

    private Map<String, Object> data = new LinkedHashMap<String, Object>();

    @Override
    public void put(String name, Object value) {
        data.put(name, value);
    }

    @Override
    public List<Object> getValue(){
        return new ArrayList<Object>(data.values());
    }

    @Override
    public Set<String> getName(){
        return data.keySet();
    }

    @Override
    public Object get(String name) {
        return data.get(name);
    }

    @Override
    public void updateFrom(DataSet newValue) {
        Set<String> columns = newValue.getName();
        for (String name : columns) {
            Object data = newValue.get(name);
            put(name, data);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "names:" + getName().toString() + ", " +
                "values:" + getValue().toString() +
                "}";
    }
}
