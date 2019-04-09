package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;


import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import javax.lang.model.util.Types;

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
    public void testPrintTableData(){

        //given
        when(manager.getTableCloumns("newlist"))
                  .thenReturn(new String[]{"id", "name", "lastname"});

            DataSet user1 = new DataSet();
            user1.put("id", 11);
            user1.put("name", "xxx");
            user1.put("lastname", "ccc");

            DataSet user2 = new DataSet();
            user2.put("id", 12);
            user2.put("name", "vvv");
            user2.put("lastname", "bbb");

            DataSet[] data = new DataSet[]{user1, user2};
            when(manager.getTableDataSet("newlist"))
                    .thenReturn(data);

            //when
            command.process("find|newlist");
            //then
        shouldPrint("[-----------------," +
                            " |id|name|lastname|," +
                            " -----------------, " +
                            "|11|xxx|ccc|," +
                            " |12|vvv|bbb|," +
                            " -----------------]");

    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected,captor.getAllValues().toString());
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
    public void testPrintEmptyTableData(){

        //given

        when(manager.getTableCloumns("newlist"))
                .thenReturn(new String[]{"id", "name", "lastname"});


        DataSet[] data = new DataSet[0];
        when(manager.getTableDataSet("newlist"))
                .thenReturn(data);

        //when
        command.process("find|newlist");
        //then
        shouldPrint("[-----------------," +
                " |id|name|lastname|," +
                " -----------------," +
                " -----------------]");

    }
}