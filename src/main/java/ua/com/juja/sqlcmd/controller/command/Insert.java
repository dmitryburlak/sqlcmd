package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import static ua.com.juja.sqlcmd.view.MessageList.*;
import ua.com.juja.sqlcmd.view.View;

public class Insert implements Command {
    private DatabaseManager manager;
    private View view;

    public Insert(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("insert|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException(WRONG_INSERT_TABLE_DATA.getMessage() + command);
        }
        String tableName = data[1];
        DataSet dataSet = new DataSetImpl();
        int middledatalength = data.length / 2;
        for (int index = 1; index < middledatalength; index++) {
            int freeindex = index * 2;
            String columnName = data[freeindex];
            String columnValue = data[freeindex + 1];
            dataSet.put(columnName, columnValue);
        }
        manager.insert(tableName, dataSet);
        view.write(String.format(INSERT_TABLE_DATA.getMessage(), tableName));
    }
}
