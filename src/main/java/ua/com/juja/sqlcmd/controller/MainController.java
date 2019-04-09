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
                new Clear(manager, view),
                new Create(manager, view),
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

            for (Command command : commands) {
                try {
                    if (command.canProcess(input)) {
                        command.process(input);
                        break;
                    }
                } catch (Exception e) {
                    if (e instanceof ExitException) {
                        throw new ExitException();
                    }
                    printError(e);
                    break;
                }
            }
            view.write("введи команду или help:");
        }
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        if (e.getCause() != null) {
            message += " " + e.getCause().getMessage();
        }
        view.write("error!" + message);
        view.write("try again");
    }
}






