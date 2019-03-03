package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InMemoryDatabaseManager;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;


public class MainController {

    private View view;
    private DatabaseManager manager;

    public MainController(View view, DatabaseManager manager ){
        this.view = view;
        this.manager = manager;

    }
    private void connectToDb() {
        view.write("привет");
        view.write("введи, имя базы, логин, пароль в формате: databaseName|userName|password");
        while (true) {
            try {
                String string = view.read();
                String[] data = string.split("[|]");
                String databaseName = data[0];
                String userName = data[1];
                String password = data[2];
                if(data.length < 3){
                    throw new IllegalArgumentException("ожидается 3 параметра, а есть:" + data.length);
                }
                manager.connect(databaseName, userName, password);
                break;
            } catch (Exception e) {
                printError(e);
            }
        }
        view.write("ок");
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        if(e.getCause() != null){
            message += " " + e.getCause().getMessage();
        }
        view.write("error!" + message);
        view.write("try again");
    }

    public void run(){
        connectToDb();
    }


}
