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
        view.write("\thelp");
        view.write("\t\tсписок команд");

        view.write("\texit");
        view.write("\t\tвыход");

        view.write("\tfind|tableName");
        view.write("\t\tсодержимое таблицы 'tableName'");

        view.write("\tlist");
        view.write("\t\tсписок таблиц");

    }
}
