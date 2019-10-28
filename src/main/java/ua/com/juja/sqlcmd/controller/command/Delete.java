package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static ua.com.juja.sqlcmd.view.MessageList.*;

public class Delete implements Command {
    private DatabaseManager manager;
    private View view;

    public Delete(DatabaseManager manager, View view){
        this.manager = manager;
        this.view = view;
    }
    @Override
    public boolean canProcess(String command) {
        return command.startsWith("delete|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (!(data.length == 4)){
            throw new IllegalArgumentException(WRONG_DELETE_TABLE_DATA.getMessage() + command);
        }
        String tableName = data[1];
        String columnName = data[2];
        String value = data[3];
        DataSet dataSet = new DataSetImpl();
        dataSet.put(columnName, value);
        manager.delete(tableName, dataSet);
        view.write(String.format(DELETE_TABLE_DATA.getMessage(), tableName, columnName, value));

    }
}
