package ua.com.juja.sqlcmd.controller.web.forms;

public class Update {
    private String tableName;
    private int id;
    private String column;
    private String value;

    public Update() {
    }

    public String getTableName() {
        return tableName;
    }
    public int getId() {
        return id;
    }
    public String getColumn() {
        return column;
    }
    public String getValue() {
        return value;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setColumn(String column) {
        this.column = column;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
