package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/*
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
        command.get("clear|newlist");
        //then
        verify(manager).clear("newlist");
        verify(view).write("таблица newlist очищена");
    }

    @Test
    public void testErrorClearFewParameters(){
        //given

        //when
        try {
            command.get("clear");
        } catch (IllegalArgumentException e){
            assertEquals("формат команды 'clear|tableName', а ты ввел:clear",
                    e.getMessage());
        }
    }

    @Test
    public void testErrorClearMoreThanNecessaryParameters(){
        //given

        //when
        try {
            command.get("qwe|newlist|qwe");
        } catch (IllegalArgumentException e){
            assertEquals("формат команды 'clear|tableName', а ты ввел:qwe|newlist|qwe",
                    e.getMessage());
        }
    }
}
*/
