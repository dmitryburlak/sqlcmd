package ua.com.juja.sqlcmd.controller.command;

import static ua.com.juja.sqlcmd.view.MessageList.*;
import ua.com.juja.sqlcmd.view.View;

public class Unsupported implements Command {

    private View view;

    public Unsupported(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        view.write(UNSUPPORTED_COMMAND.getMessage() +command);
    }
}
