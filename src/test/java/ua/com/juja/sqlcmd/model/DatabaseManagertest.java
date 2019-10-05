package ua.com.juja.sqlcmd.model;


import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.command.Connect;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public abstract class DatabaseManagertest {

    private DatabaseManager manager;
    private DatabaseConnect connectmanager;


    @Before
    public void setup() {
        connectmanager.connect("namelist", "postgres", "root");
        manager = getDatabaseManager();


    }

    public abstract DatabaseManager getDatabaseManager();


    @Test
    public void testGetAllTambleNames() {
        //given
        //manager.isConnected();
        manager.getTableDataSet("newlist");

        Set<String> tablesNames = manager.getTables();
        assertEquals("[newlist]", tablesNames.toString());


    }

    @Test
    public void testGetTableData() {

        manager.clear("newlist");

        DataSet input = new DataSetImpl();
        input.put("id", 10);
        input.put("name", "hhh");
        input.put("password", "ggg");
        manager.create("newlist", input);

        List<DataSet> newlists = manager.getTableDataSet("newlist");
        assertEquals(1, newlists.size());

        DataSet newlist = newlists.get(0);
        assertEquals("[id, name, password]", newlist.getName().toString());
        assertEquals("[10, hhh, ggg]", newlist.getValue().toString());

    }

    @Test
    public void testUpdateTableData() {
        //given
        manager.clear("newlist");

        DataSet input = new DataSetImpl();
        input.put("id", 10);
        input.put("name", "hhh");
        input.put("password", "ggg");
        manager.create("newlist", input);

        //when
        DataSet newValue = new DataSetImpl();
        newValue.put("password", "ggghh");
        manager.update("newlist", 10, newValue);

        //then
        List<DataSet> newlists = manager.getTableDataSet("newlist");
        assertEquals(1, newlists.size());

        DataSet newlist = newlists.get(0);
        assertEquals("[id, name, password]", newlist.getName().toString());
        assertEquals("[10, hhh, ggghh]", newlist.getValue().toString());
    }

    @Test
    public void testGetColumnNames() {
        manager.clear("newlist");

        Set<String> columnNames = manager.getTableCloumns("newlist");

        assertEquals("[id, name, password]", columnNames.toString());

    }

    @Test
    public void testIsConnected() {
        assertTrue(manager.isConnected());
    }
}
