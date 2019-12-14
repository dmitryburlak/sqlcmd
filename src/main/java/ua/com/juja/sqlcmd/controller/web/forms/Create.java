package ua.com.juja.sqlcmd.controller.web.forms;

public class Create {
    private String tableName;
    private String columnPk;
    private String columnone;
    private String columntwo;

    public Create() {
    }

    public String getTableName() {
        return tableName;
    }
    public String getColumnPk() {
        return columnPk;
    }
    public String getColumnone() {
        return columnone;
    }
    public String getColumntwo() {
        return columntwo;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public void setColumnPk(String columnPk) {
        this.columnPk = columnPk;
    }
    public void setColumnone(String columnone) {
        this.columnone = columnone;
    }
    public void setColumntwo(String columntwo) {
        this.columntwo = columntwo;
    }

}
