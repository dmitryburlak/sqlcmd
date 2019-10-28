package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static ua.com.juja.sqlcmd.view.MessageList.*;

public class Create implements Command {
    private DatabaseManager manager;
    private View view;

    public Create(DatabaseManager manager, View view){
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
        if (data.length < 3){
            throw new IllegalArgumentException(WRONG_CREATE_TABLE_PARAM.getMessage() + command);
        }
        String tableName = data[1];
        String dataKey = data[2];
        DataSet keyName = new DataSetImpl();
        keyName.put(dataKey, "");
        DataSet dataSet = new DataSetImpl();
        for (int index = 3; index < data.length; index++) {
            String columnNames = data[index];
            dataSet.put(columnNames, "");
        }
        manager.create(tableName, keyName, dataSet);
        view.write(String.format(CREATE_TABLE.getMessage(), tableName));
    }
}
