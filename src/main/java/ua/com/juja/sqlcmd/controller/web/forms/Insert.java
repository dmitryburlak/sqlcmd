package ua.com.juja.sqlcmd.controller.web.forms;

public class Insert {
    private String tableName;
    private String column;
    private String value;
    private String columnSecond;
    private String valueSecond;

    public Insert() {
    }

    public String getTableName() {
        return tableName;
    }
    public String getColumn() {
        return column;
    }
    public String getValue() {
        return value;
    }
    public String getColumnSecond() {
        return columnSecond;
    }
    public String getValueSecond() {
        return valueSecond;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public void setColumn(String column) {
        this.column = column;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public void setColumnSecond(String columnSecond) {
        this.columnSecond = columnSecond;
    }
    public void setValueSecond(String valueSecond) {
        this.valueSecond = valueSecond;
    }
}
