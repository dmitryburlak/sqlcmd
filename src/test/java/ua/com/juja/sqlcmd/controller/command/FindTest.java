package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;


import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.PrintTable;
import ua.com.juja.sqlcmd.view.View;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class FindTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Find(manager, view);
    }

    @Test
    public void testPrintTableData() {
        //given
        String tableName = "newlist";
        setupTableColumns("newlist", "id", "name", "password");

        DataSet user1 = new DataSetImpl();
        user1.put("id", 11);
        user1.put("name", "xxx");
        user1.put("password", "ccc");

        DataSet user2 = new DataSetImpl();
        user2.put("id", 12);
        user2.put("name", "vvv");
        user2.put("password", "bbb");

        when(manager.getTableDataSet("newlist"))
                .thenReturn(Arrays.asList(user1, user2));

        //when
        command.process("find|newlist");

        //then

    }

    @Test
    public void testCanProcessFindWithParametersString(){
        //given

        //when
        boolean canProcess = command.canProcess("find|");

        //then
        assertTrue(canProcess);
    }

    @Test
    public void testNotCanProcessFindWithoutParametersString(){
        //given

        //when
        boolean canProcess = command.canProcess("qwe|");

        //then
        assertFalse(canProcess);
    }

    @Test
    public void testPrintEmptyTableData() {
        //given
        setupTableColumns("newlist", "id", "name", "password");
        when(manager.getTableDataSet("newlist"))
                .thenReturn(new ArrayList<DataSet>());

        //when
        command.process("find|newlist");

        //then

    }

    private void setupTableColumns(String tableName, String... columns) {
        when(manager.getTableCloumns(tableName))
                .thenReturn(new LinkedHashSet<String>(Arrays.asList(columns)));
    }

    @Test
    public void testErrorWhenBadCommand(){
        //given
        setupTableColumns("newlist", "id", "name", "password");

        when(manager.getTableDataSet("newlist"))
                .thenReturn(new ArrayList<DataSet>());

        //when
        try {
            command.process("find|newlist|yyy");
            fail("expected error");
        } catch (IllegalArgumentException e){
            assertEquals("формат команды 'find|tableName', а ты ввел:find|newlist|yyy",
                    e.getMessage());
        }
    }
}
