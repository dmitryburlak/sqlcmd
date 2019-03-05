package ua.com.juja.sqlcmd.controller;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.util.Arrays;


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
        while (true){
        view.write("введи команду или help:");
        String command = view.read();
        if(command.equals("list")){
            doList();
        } else if (command.equals("help")){
            doHelp();
        } else if (command.equals("exit")){
            view.write("exit");
            System.exit(0);
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
        view.write(result);
    }

    private void doHelp() {
        view.write("список команд:");
        view.write("\thelp");
        view.write("\t\tсписок команд");

        view.write("\texit");
        view.write("\t\tвыход");

        view.write("\tfind|tableName");
        view.write("\t\tсодержимое таблицы 'tableName'");

        view.write("\tlist");
        view.write("\t\tсписок таблиц");
    }

    private void doList() {
        String[] tableNames = manager.getTables();
        String message = Arrays.toString(tableNames);
        view.write(message);
    }


}
