package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import static ua.com.juja.sqlcmd.view.MessageList.*;


import ua.com.juja.sqlcmd.view.*;


import java.util.List;
import java.util.Set;

public class Find implements Command {

    private DatabaseManager manager;
    private View view;

    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (!(data.length == 2)) {
            throw new IllegalArgumentException(WRONG_FIND.getMessage() + command);
        }
        String tableName = data[1];
        Set<String> tableColumns = manager.getTableCloumns(tableName);
        PrintTable.printHeader(tableColumns);
        List<DataSet> tableData = manager.getTableDataSet(tableName);
        PrintTable.printTableData(tableData);
    }

}

