package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.MessageList;
import ua.com.juja.sqlcmd.view.View;

public class Create implements Command {
    private DatabaseManager manager;
    private View view;

    public Create(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException(MessageList.WRONG_CREATE_TABLE_ENTRY.getMessage() + command);
        }
        String tableName = data[1];
        DataSet dataSet = new DataSetImpl();
        int middledatalength = data.length / 2;
        for (int index = 1; index < middledatalength; index++) {
            int freeindex = 2;
            String columnName = data[index * freeindex];
            String columnValue = data[index * freeindex + 1];
            dataSet.put(columnName, columnValue);
        }
        manager.create(tableName, dataSet);
        view.write(String.format(MessageList.CREATE_TABLE_ENTRY.getMessage(), dataSet, tableName));
    }
}
