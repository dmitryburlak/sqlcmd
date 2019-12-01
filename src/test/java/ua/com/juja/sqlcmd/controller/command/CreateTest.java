package ua.com.juja.sqlcmd.controller.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/*public class CreateTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup(){
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Create(manager, view);
    }

    @Test
    public void testCreateTable(){
        //given
        String tableName = "newdata";
        DataSet keyId = new DataSetImpl();
        keyId.put("id", "");
        DataSet input = new DataSetImpl();
        input.put("data1", "");
        input.put("data2", "");

        //when
        command.process("create|newdata|id|data1|data2");

        //then
        verify(manager).create(refEq(tableName), refEq(keyId), refEq(input));
        verify(view).write("таблица newdata создана");
    }

    @Test
    public void testErrorCreateFewParameters(){
        //given

        //when
        try {
            command.process("create");
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("формат команды create|tableName|column1(PK)|column2|...|columnN , а ты ввел: create",
                    e.getMessage());
        }
    }

    @Test
    public void testErrorCreateMoreThanNecessaryParameters(){
        //given

        //when
        try {
            command.process("qwe|newdata");
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("формат команды create|tableName|column1(PK)|column2|...|columnN , а ты ввел: qwe|newdata",
                    e.getMessage());
        }
    }
}*/
