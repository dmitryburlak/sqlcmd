package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;

import ua.com.juja.sqlcmd.model.DatabaseManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/*
public class TablesTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Tables(manager, view);
    }

    @Test
    public void testTableList(){
        //given

        //when
        command.process("tables");

        //then
        verify(manager).getTables();
        verify(view).write("[]");
    }
}
*/
