package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.controller.command.Command;
import ua.com.juja.sqlcmd.controller.command.Exit;
import ua.com.juja.sqlcmd.controller.command.Help;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.util.Arrays;


public class MainController {

    private Command commands[];
    private View view;
    private DatabaseManager manager;

    public MainController(View view, DatabaseManager manager ){
        this.view = view;
        this.manager = manager;
        this.commands = new Command[]{new Exit(view), new Help(view)};

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
        while (true){
        view.write("введи команду или help:");
        String command = view.read();
        if(command.equals("list")){
            doList();
        } else if (commands[1].canProcess(command)){
            commands[1].process(command);
        } else if (commands[0].canProcess(command)){
            commands[0].process(command);
        } else if (command.startsWith("find|")){
            doFind(command);
        } else {
            view.write("несуществующая команда:" +command);
        }
        }
    }

    private void doFind(String command) {
        String[] data = command.split("\\|");
        String tableName = data[1];

        DataSet[] tableData = manager.getTableDataSet(tableName);
        String[] tableColumns = manager.getTableCloumns(tableName);

        printHeader(tableColumns);
        printTable(tableData);

    }

    private void printTable(DataSet[] tableData) {

        for(DataSet row : tableData){
           printRow(row);
        }
    }

    private void printRow(DataSet row) {
        Object[] values = row.getValue();
        String result = "|";
        for (Object value : values){
            result += value + "|";
        }
        view.write(result);
    }

    private void printHeader(String[] tableColumns) {
        String result = "|";
        for (String name : tableColumns){
           result += name + "|";
        }
        view.write("-----------------");
        view.write(result);
        view.write("-----------------");
    }



    private void doList() {
        String[] tableNames = manager.getTables();
        String message = Arrays.toString(tableNames);
        view.write(message);
    }


}
