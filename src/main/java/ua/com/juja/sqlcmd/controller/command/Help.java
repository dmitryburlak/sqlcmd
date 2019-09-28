package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.view.View;

public class Help implements Command {

    private View view;

    public Help(View view){
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
        view.write("список команд:");

        view.write("\tconnect|databaseName|userName|password");
        view.write("\t\tдля подключения к базе данных, с которой будем работать");

        view.write("\thelp");
        view.write("\t\tсписок команд");

        view.write("\texit");
        view.write("\t\tвыход");

        view.write("\tfind|tableName");
        view.write("\t\tсодержимое таблицы 'tableName'");

        view.write("\ttables");
        view.write("\t\tсписок таблиц");

        view.write("\tclear|tableName");
        view.write("\t\tочистка таблицы 'tableName'");

        view.write("\tcreate|tableName|column1|value1|column2|value2|...|columnN|valueN");
        view.write("\t\tсоздание записи в таблице 'tableName'");
    }
}
