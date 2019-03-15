package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

public class IsConnected implements Command {

    private DatabaseManager manager;
    private View view;

    public IsConnected(DatabaseManager manager, View view) {

        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return !manager.isConnected();
    }

    @Override
    public void process(String command) {
        view.write(String.format("вы не можете пользоваться командой, %s, " +
                "пока не подключитесь с помощью " +
                "команды connect|databaseName|userName|password", command));

    }
}
