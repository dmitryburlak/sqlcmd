package ua.com.juja.sqlcmd.controller.command;


import static ua.com.juja.sqlcmd.view.MessageList.*;
import ua.com.juja.sqlcmd.view.View;

public class Exit implements Command {

    private View view;

    public Exit(View view){
        this.view = view;
    }
    @Override
    public boolean canProcess(String command) {
        return command.equals("exit");
}

    @Override
    public void process(String command) {
        view.write(EXIT_PROGRAM.getMessage());
        throw new ExitException();
    }
}
