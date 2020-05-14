package ua.com.juja.sqlcmd.controller.web.forms;

public class Create {
    private String tableName;
    private String columnPk;
    private String columnOne;
    private String columnTwo;

    public Create() {
        //do nothing
    }

    public String getTableName() {
        return tableName;
    }
    public String getColumnPk() {
        return columnPk;
    }
    public String getColumnOne() {
        return columnOne;
    }
    public String getColumnTwo() {
        return columnTwo;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public void setColumnPk(String columnPk) {
        this.columnPk = columnPk;
    }
    public void setColumnOne(String columnOne) {
        this.columnOne = columnOne;
    }
    public void setColumnTwo(String columnTwo) {
        this.columnTwo = columnTwo;
    }

}
