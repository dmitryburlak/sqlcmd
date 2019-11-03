package ua.com.juja.sqlcmd.model;


import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class DatabaseManagertest {
    private DatabaseManager manager;
    private DatabaseConnect connectmanager;

    @Before
    public void setup() {
        connectmanager.connect("namelist", "postgres", "root");
        manager = new JDBCDatabaseManager();
    }

    @Test
    public void testGetAllTableNames() {
        //given

        Set<String> tablesNames = manager.getTables();
        assertEquals("[supertable, newlist, newtable]", tablesNames.toString());
    }

    @Test
    public void testGetTableData() {

        manager.clear("newlist");

        DataSet input = new DataSetImpl();
        input.put("id", 10);
        input.put("name", "hhh");
        input.put("password", "ggg");
        manager.insert("newlist", input);

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
        manager.insert("newlist", input);

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

    @Test
    public void testDropCreateTable() {
        //given
        manager.drop("newtable");

        //when
        String tableName = "newtable";

        DataSet keyName = new DataSetImpl();
        keyName.put("id", "");

        DataSet input = new DataSetImpl();
        input.put("one", "");
        input.put("two", "");

        //then
        manager.create(tableName, keyName, input);
        Set<String> columnNames = manager.getTableCloumns("newtable");
        assertEquals("[id, one, two]", columnNames.toString());
    }
    @Test
    public void testDeleteTableData() {
        //given
        manager.clear("newlist");

        DataSet input = new DataSetImpl();
        input.put("id", 24);
        input.put("name", "mmm");
        input.put("password", "nnn");
        manager.insert("newlist", input);

        DataSet inputsecond = new DataSetImpl();
        inputsecond.put("id", 25);
        inputsecond.put("name", "kkk");
        inputsecond.put("password", "jjj");
        manager.insert("newlist", inputsecond);

        //when
        DataSet inputToDel = new DataSetImpl();
        inputToDel.put("name", "kkk");
        manager.delete("newlist", inputToDel);

        //then
        List<DataSet> newlists = manager.getTableDataSet("newlist");
        assertEquals(1, newlists.size());

        DataSet newlist = newlists.get(0);
        assertEquals("[id, name, password]", newlist.getName().toString());
        assertEquals("[24, mmm, nnn]", newlist.getValue().toString());
    }
}
