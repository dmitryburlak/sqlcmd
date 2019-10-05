package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseConnect;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

public class Connect implements Command {

    private static String COMMAND_SAMPLE = "connect|namelist|postgres|root";
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
            throw new IllegalArgumentException(String.format("ожидается %s параметра, а есть: %s",
                    count(), data.length));
        }
        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];
        connectmanager.connect(databaseName, userName, password);
        view.write("ок");
    }

    private int count() {
        return COMMAND_SAMPLE.split("\\|").length;
    }
}
