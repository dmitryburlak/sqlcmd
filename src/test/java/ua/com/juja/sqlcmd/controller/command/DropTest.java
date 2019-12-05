package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/*
public class DropTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Drop(manager, view);
    }

    @Test
    public void testDropTable(){
        //given

        //when
        command.get("drop|newdata");

        //then
        verify(manager).drop("newdata");
        verify(view).write("таблица newdata удалена");
    }

    @Test
    public void testErrorDropFewParameters(){
        //given

        //when
        try {
            command.get("drop");
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("формат команды drop|tableName, а ты ввел: drop",
                    e.getMessage());
        }
    }

    @Test
    public void testErrorDropMoreThanNecessaryParameters(){
        //given

        //when
        try {
            command.get("qwe|newdata|qwe");
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("формат команды drop|tableName, а ты ввел: qwe|newdata|qwe",
                    e.getMessage());
        }
    }
}
*/
