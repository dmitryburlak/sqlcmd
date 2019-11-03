package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;
import static ua.com.juja.sqlcmd.view.MessageList.*;

public class Update implements Command {
    private DatabaseManager manager;
    private View view;

    public Update(DatabaseManager manager, View view){
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("update|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        int countinputparameters = 5;
        if (data.length < countinputparameters || data.length % 2 == 0){
            throw new IllegalArgumentException(WRONG_UPDATE_TABLE_DATA.getMessage() + command);
        }
        String tableName = data[1];
        int id = Integer.valueOf(data[2]);
        String columns = data[3];
        String newValue = data[4];
        DataSet dataSet = new DataSetImpl();
        dataSet.put(columns, newValue);

        manager.update(tableName, id, dataSet);
        view.write(String.format(UPDATE_TABLE_DATA.getMessage(), tableName, id, columns));
    }
}
