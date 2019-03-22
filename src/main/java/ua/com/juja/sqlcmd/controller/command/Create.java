package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

public class Create implements Command {

    private DatabaseManager manager;
    private View view;

    public Create(DatabaseManager manager, View view) {

        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");

        if (data.length % 2 != 0){
            throw new IllegalArgumentException("должно быть четное колличесво параметров, " +
                    "в формате 'create|tableName|column1|value1|column2|value2|...|columnN|valueN'," +
                    " а есть:" + command);

        }
        String tableName = data[1];
        DataSet dataSet = new DataSet();

        for (int index = 1; index < (data.length / 2); index++) {
            String columnName = data[index*2];
            String columnValue = data[index*2 + 1];

            dataSet.put(columnName, columnValue);
        }
        manager.create(tableName, dataSet);
        view.write(String.format("в таблицу %s запись добавлена", dataSet, tableName));


    }
}
