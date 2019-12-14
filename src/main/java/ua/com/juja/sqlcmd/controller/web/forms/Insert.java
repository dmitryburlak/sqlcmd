package ua.com.juja.sqlcmd.controller.web.forms;

public class Insert {
    private String tableName;
    private String column;
    private String value;
    private String columnsecond;
    private String valuesecond;

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
    public String getColumnsecond() {
        return columnsecond;
    }
    public String getValuesecond() {
        return valuesecond;
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
    public void setColumnsecond(String columnsecond) {
        this.columnsecond = columnsecond;
    }
    public void setValuesecond(String valuesecond) {
        this.valuesecond = valuesecond;
    }
}
