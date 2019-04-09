package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class ClearTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Clear(manager, view);
    }
    @Test
    public void testClearTable(){

        //given

        //when
        command.process("clear|newlist");
        //then
        verify(manager).clear("newlist");
        verify(view).write("таблица newlist очищена");

    }



    @Test
    public void testCanProcessClearWithParametersString(){
        //given


        //when
        boolean canProcess = command.canProcess("clear|");

        //then
        assertTrue(canProcess);
    }

    @Test
    public void testNotCanProcessClearWithoutParametersString(){
        //given


        //when
        boolean canProcess = command.canProcess("qwe|");

        //then
        assertFalse(canProcess);
    }

    @Test
    public void testErrorClearCountParameters1(){
        //given


        //when
        try {
            command.process("clear");
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("формат команды 'clear|tableName', а ты ввел:clear",
                    e.getMessage());
        }

    }

    @Test
    public void testErrorClearCountParameters3(){
        //given


        //when
        try {
            command.process("qwe|newlist|qwe");
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("формат команды 'clear|tableName', а ты ввел:qwe|newlist|qwe",
                    e.getMessage());
        }

    }

}
