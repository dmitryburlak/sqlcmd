package ua.com.juja.sqlcmd.model;


import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;

import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public abstract class DatabaseManagertest {

    private DatabaseManager manager;

    @Before
    public void setup() {
        manager = getDatabaseManager();
        manager.connect("namelist", "postgres", "root");
    }

    public abstract DatabaseManager getDatabaseManager();


    @Test
    public void testDetAllTambleNames() {
        Set<String> tablesNames = manager.getTables();
        assertEquals("[newlist]", tablesNames.toString());


    }

    @Test
    public void testGetTableData() {

        manager.clear("newlist");

        DataSet input = new DataSet();
        input.put("id", 10);
        input.put("name", "hhh");
        input.put("lastname", "ggg");
        manager.create("newlist", input);

        DataSet[] newlists = manager.getTableDataSet("newlist");
        assertEquals(1, newlists.length);

        DataSet newlist = newlists[0];
        assertEquals("[id, name, lastname]", Arrays.toString(newlist.getName()));
        assertEquals("[10, hhh, ggg]", Arrays.toString(newlist.getValue()));

    }

    @Test
    public void testUpdateTableData() {
        //given
        manager.clear("newlist");

        DataSet input = new DataSet();
        input.put("id", 10);
        input.put("name", "hhh");
        input.put("lastname", "ggg");
        manager.create("newlist", input);

        //when
        DataSet newValue = new DataSet();
        newValue.put("lastname", "ggghh");
        manager.update("newlist", 10, newValue);

        //then
        DataSet[] newlists = manager.getTableDataSet("newlist");
        assertEquals(1, newlists.length);

        DataSet newlist = newlists[0];
        assertEquals("[id, name, lastname]", Arrays.toString(newlist.getName()));
        assertEquals("[10, hhh, ggghh]", Arrays.toString(newlist.getValue()));
    }

    @Test
    public void testGetColumnNames() {
        manager.clear("newlist");

        Set<String> columnNames = manager.getTableCloumns("newlist");

        assertEquals("[id, name, lastname]", columnNames.toString());

    }

    @Test
    public void testIsConnected() {
        assertTrue(manager.isConnected());
    }
}
