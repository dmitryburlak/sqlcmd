package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static ua.com.juja.sqlcmd.view.MessageList.*;

public class Drop implements Command {
    private DatabaseManager manager;
    private View view;

    public Drop(DatabaseManager manager, View view){
        this.manager = manager;
        this.view = view;
    }
    @Override
    public boolean canProcess(String command) {
        return command.startsWith("drop|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (!(data.length == 2)){
            throw new IllegalArgumentException(WRONG_DROP_TABLE_PARAM.getMessage() + command);
        }
        String tableName = data[1];
        manager.drop(tableName);
        view.write(String.format(DROP_TABLE.getMessage(),tableName));


    }
}
