package ua.com.juja.sqlcmd.controller.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/*
public class InsertTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Insert(manager, view);
    }

    @Test
    public void testInsertData(){
        //given
        String tableName = "newdata";
        DataSet input = new DataSetImpl();
        input.put("data1", "value1");

        //when
        command.process("insert|newdata|data1|value1");

        //then
        verify(manager).insert(refEq(tableName), refEq(input));
        verify(view).write("в таблицу newdata запись добавлена");
    }

    @Test
    public void testErrorInsertDataFewParameters(){
        //given

        //when
        try {
            command.process("insert");
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("должно быть четное колличесво параметров, " +
                            "в формате 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN', " +
                            "а есть:insert",
                    e.getMessage());
        }
    }

    @Test
    public void testErrorInsertDataMoreThanNecessaryParameters(){
        //given

        //when
        try {
            command.process("qwe|newdata|data1|value1|11");
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("должно быть четное колличесво параметров, " +
                            "в формате 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN', " +
                            "а есть:qwe|newdata|data1|value1|11",
                    e.getMessage());
        }
    }
}
*/
