package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseConnect;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import static ua.com.juja.sqlcmd.view.MessageList.*;
import ua.com.juja.sqlcmd.view.View;

public class Connect implements Command {

    private DatabaseConnect connectmanager;
    private View view;

    public Connect(DatabaseConnect connectmanager, View view) {
        this.connectmanager = connectmanager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length < count()) {
            throw new IllegalArgumentException(String.format(WRONG_PARAM_CONNECT.getMessage(),
                    count(), data.length));
        }
        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];
        connectmanager.connect(databaseName, userName, password);
        view.write(OK_PARAM_CONNECT.getMessage());
    }

    private int count() {
        return COMMAND_SAMPLE.getMessage().split("\\|").length;
    }
}
