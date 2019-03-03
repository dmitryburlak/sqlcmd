package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.InMemoryDatabaseManager;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;


public class MainController {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new InMemoryDatabaseManager();
        view.write("привет");
        view.write("введи, имя базы, логин, пароль в формате: databaseName|userName|password");
        while (true) {
            String string = view.read();
            String[] data = string.split("[|]");
            String databaseName = data[0];
            String userName = data[1];
            String password = data[2];
            try {
                manager.connect(databaseName, userName, password);
                break;
            } catch (Exception e) {
                String message = e.getMessage();
                if(e.getCause() != null){
                    message += " " + e.getCause().getMessage();
                }
                view.write("error!" + message);
                view.write("try again");
            }
        }
        view.write("ок");

    }


}
