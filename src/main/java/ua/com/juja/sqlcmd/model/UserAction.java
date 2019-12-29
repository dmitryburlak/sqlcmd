package ua.com.juja.sqlcmd.model;

public class UserAction {
    private String name;
    private String dbName;
    private String action;
    private int id;

    public UserAction() {
        //do nothing
    }

    public UserAction(String name, String dbName, String action) {
        this.name = name;
        this.dbName = dbName;
        this.action = action;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDbName() {
        return dbName;
    }

    public String getAction() {
        return action;
    }
}
