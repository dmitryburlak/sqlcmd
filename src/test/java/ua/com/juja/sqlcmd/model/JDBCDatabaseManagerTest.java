package ua.com.juja.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JDBCDatabaseManagerTest {

    private DatabaseManager manager;

    @Before
    public void setup(){
        DatabaseConnect databaseConnect = new DatabaseConnect();
        databaseConnect.connect("namelist","postgres", "root");
        manager = new JDBCDatabaseManager();
    }

    @Test
    public void testGetAllTableNames() {
        Set<String> tablesNames = manager.getTables();
        assertEquals("[newtable, supertable, newlist]", tablesNames.toString());
    }

    @Test
    public void testGetTableData_find() {
        //given
        manager.clear("newlist");
        //when
        Map<String, Object> input = new LinkedHashMap<>();
        input.put("id", 10);
        input.put("name", "hhh");
        input.put("password", "ggg");
        manager.insert("newlist", input);

        //then
        List<Map<String, Object>> newlists = manager.getTableDataSet("newlist");

        for (Map<String, Object> lists : newlists ){
            String resultkeys = "";
            String resultvalues = "";
            for (Map.Entry maps : lists.entrySet()){
                resultkeys = resultkeys.concat(String.valueOf(maps.getKey())).concat(", ");
                resultvalues = resultvalues.concat(String.valueOf(maps.getValue()).concat(", "));
            }
            assertEquals("id, name, password, ", resultkeys);
            assertEquals("10, hhh, ggg, ", resultvalues);
        }
    }

    @Test
    public void testUpdateTableData() {
        //given
        manager.clear("newlist");

        //when
        Map<String, Object> input = new LinkedHashMap<>();
        input.put("id", 10);
        input.put("name", "hhh");
        input.put("password", "ggg");
        manager.insert("newlist", input);

        Map<String, Object> newValue = new LinkedHashMap<>();
        newValue.put("password", "ggghh");
        manager.update("newlist", 10, newValue);

        //then
        List<Map<String, Object>> newlists = manager.getTableDataSet("newlist");

        for (Map<String, Object> lists : newlists ) {
            String resultkeys = "";
            String resultvalues = "";
            for (Map.Entry maps : lists.entrySet()) {
                resultkeys = resultkeys.concat(String.valueOf(maps.getKey())).concat(", ");
                resultvalues = resultvalues.concat(String.valueOf(maps.getValue()).concat(", "));
            }
            assertEquals("id, name, password, ", resultkeys);
            assertEquals("10, hhh, ggghh, ", resultvalues);
        }
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
        String keyName = "id";

        Set<String> input = new LinkedHashSet();
        input.add("one");
        input.add("two");

        //then
        manager.create(tableName, keyName, input);
        Set<String> columnNames = manager.getTableCloumns("newtable");
        assertEquals("[id, one, two]", columnNames.toString());
    }
    @Test
    public void testDeleteTableData() {
        //given
        manager.clear("newlist");

        Map<String, Object> input = new LinkedHashMap<>();
        input.put("id", 24);
        input.put("name", "mmm");
        input.put("password", "nnn");
        manager.insert("newlist", input);

        Map<String, Object> inputsecond = new LinkedHashMap<>();
        inputsecond.put("id", 25);
        inputsecond.put("name", "kkk");
        inputsecond.put("password", "jjj");
        manager.insert("newlist", inputsecond);

        //when
        Map<String, Object> inputToDel = new LinkedHashMap<>();
        inputToDel.put("name", "kkk");
        manager.delete("newlist", inputToDel);

        //then
        List<Map<String, Object>> newlists = manager.getTableDataSet("newlist");

        for (Map<String, Object> lists : newlists ){
            String resultvalues = "";
            for (Object values  : lists.values()){
                resultvalues = resultvalues.concat(String.valueOf(values)).concat(", ");
            }
            assertEquals("24, mmm, nnn, ", resultvalues);
        }
    }

}
