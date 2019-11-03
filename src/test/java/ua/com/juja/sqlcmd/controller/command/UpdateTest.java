package ua.com.juja.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DataSetImpl;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UpdateTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Update(manager, view);
    }

    @Test
    public void testUpateData(){
        //given
        String tableName = "newdata";
        int id = 1;
        DataSet input = new DataSetImpl();
        input.put("data1", "value1");

        //when
        command.process("update|newdata|1|data1|value1");

        //then
        verify(manager).update(refEq(tableName), eq(id), refEq(input));
        verify(view).write("в таблице newdata id 1 в колонке data1 запись обновлена");
    }

    @Test
    public void testErrorUpdateDataFewParameters(){
        //given

        //when
        try {
            command.process("update");
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("формат команды update|tableName|id|columnN|newvalue , а ты ввел: update",
                    e.getMessage());
        }
    }

    @Test
    public void testErrorUpdateDataMoreThanNecessaryParameters(){
        //given

        //when
        try {
            command.process("qwe|data1|value1|11");
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("формат команды update|tableName|id|columnN|newvalue , а ты ввел: qwe|data1|value1|11",
                    e.getMessage());
        }
    }
}
