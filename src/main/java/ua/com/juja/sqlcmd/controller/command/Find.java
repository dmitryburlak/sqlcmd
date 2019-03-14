package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

public class Find implements Command {

    private DatabaseManager manager;
    private View view;

    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        String tableName = data[1];

        String[] tableColumns = manager.getTableCloumns(tableName);
        printHeader(tableColumns);

        DataSet[] tableData = manager.getTableDataSet(tableName);
        printTable(tableData);

    }

    private void printTable(DataSet[] tableData) {

        for(DataSet row : tableData){
            printRow(row);
        }
    }

    private void printRow(DataSet row) {
        Object[] values = row.getValue();
        String result = "|";
        for (Object value : values){
            result += value + "|";
        }
        view.write(result);
    }

    private void printHeader(String[] tableColumns) {
        String result = "|";
        for (String name : tableColumns){
            result += name + "|";
        }
        view.write("-----------------");
        view.write(result);
        view.write("-----------------");
    }
}
