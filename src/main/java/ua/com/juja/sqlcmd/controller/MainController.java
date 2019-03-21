package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.controller.command.*;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.util.Arrays;


public class MainController {

    private Command commands[];
    private View view;


    public MainController(View view, DatabaseManager manager ){
        this.view = view;

        this.commands = new Command[]{
                new Connect(manager, view),
                new Help(view),
                new Exit(view),
                new IsConnected(manager, view),
                new List(manager, view),
                new Find(manager, view),
                new Unsupported(view)
        };
    }

    public void run(){
        try {
            doWork();
            //return;
        }catch (ExitException e){
            //do nothing
        }

    }

    private void doWork() {
        view.write("привет");
        view.write("введи, имя базы, логин, пароль в формате: connect|databaseName|userName|password");

        while (true) {
            String input = view.read();
            if (input == null) {//TODO
                new Exit(view).process(input);
            }

            for (Command command : commands) {
                if (command.canProcess(input)) {
                    command.process(input);
                    break;
                }
            }
            view.write("введи команду или help:");
        }
    }


}
