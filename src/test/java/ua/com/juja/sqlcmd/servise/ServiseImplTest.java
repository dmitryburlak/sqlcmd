package ua.com.juja.sqlcmd.servise;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.service.Servise;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/application-context.xml")
public class ServiseImplTest {

    @Autowired
    private Servise servise;

    private DatabaseManager manager = mock(JDBCDatabaseManager.class);

    @Test
    public void serviseCommandListTest(){
        //given

        //when
        servise.commandsList();

        //then
        assertEquals("[help, tables, find, create, drop, clear, insert, delete, update]",
                servise.commandsList().toString());
    }

    @Test
    public void serviseFindTest(){
        //given
        final String tableName = "newlist";

        //when
        servise.find(manager, tableName);

        //then
        verify(manager, times(1)).getTableCloumns(tableName);
        verify(manager, times(1)).getTableDataSet(tableName);
    }

    @Test
    public void serviseTablesTest(){
        //given

        //when
        servise.tables(manager);

        //then
        verify(manager, times(1)).getTables();
    }

    @Test
    public void serviseInsertTest(){
        //given
        final String tableName = "newtablename";
        final String column = "column";
        final String value = "value";
        final String columnsecond = "columnsecond";
        final String valuesecond = "valuesecond";

        //when
        servise.insert(manager, tableName, column, value, columnsecond, valuesecond);

        Map<String, Object> input = new LinkedHashMap<>();
        input.put(column, value);
        input.put(columnsecond, valuesecond);

        //then
        verify(manager, times(1)).insert(tableName, input);
    }

    @Test
    public void serviseDeleteTest(){
        //given
        final String tableName = "newtablename";
        final String column = "column";
        final String value = "value";

        //when
        servise.delete(manager, tableName, column, value);

        Map<String, Object> input = new LinkedHashMap<>();
        input.put(column, value);

        //then
        verify(manager, times(1)).delete(tableName, input);
    }

    @Test
    public void serviseCreateTest(){
        //given
        final String tableName = "newtablename";
        final String columnPk = "id";
        final String columnone = "columnone";
        final String columntwo = "columntwo";

        //when
        servise.create(manager, tableName, columnPk, columnone, columntwo);

        Set<String> input = new LinkedHashSet<>();
        input.add(columnone);
        input.add(columntwo);

        //then
        verify(manager, times(1)).create(tableName, columnPk, input);
    }

    @Test
    public void serviseDropTest(){
        //given
        final String tableName = "newtablename";

        //when
        servise.drop(manager, tableName);

        //then
        verify(manager, times(1)).drop(tableName);
    }

    @Test
    public void serviseUpdateTest(){
        //given
        final String tableName = "newtablename";
        int id = 1;
        final String column = "column";
        final String value = "value";

        //when
        servise.update(manager, tableName, id, column, value);

        Map<String, Object> input = new LinkedHashMap<>();
        input.put(column, value);

        //then
        verify(manager, times(1)).update(tableName, id, input);
    }

}
