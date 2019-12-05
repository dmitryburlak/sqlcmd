package ua.com.juja.sqlcmd.controller.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/*
public class DeleteTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Delete(manager, view);
    }

    @Test
    public void testDeleteData(){
        //given
        String tableName = "newdata";
        DataSet input = new DataSetImpl();
        input.put("data1", "value1");

        //when
        command.get("delete|newdata|data1|value1");

        //then
        verify(manager).delete(refEq(tableName), refEq(input));
        verify(view).write("в таблицe newdata из колонки data1 запись value1 удалена");
    }

    @Test
    public void testErrorDeleteDataFewParameters(){
        //given

        //when
        try {
            command.get("delete");
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("формат команды delete|tableName|column|value, а ты ввел: delete",
                    e.getMessage());
        }
    }

    @Test
    public void testErrorDeleteDataMoreThanNecessaryParameters(){
        //given

        //when
        try {
            command.get("delete|newdata|data1|value1|11");
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("формат команды delete|tableName|column|value, а ты ввел: delete|newdata|data1|value1|11",
                    e.getMessage());
        }
    }
}
*/
