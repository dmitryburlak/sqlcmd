package ua.com.juja.sqlcmd.integration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.Main;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseConnect;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;


import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class IntegrationTest {
    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;
    private DatabaseManager databaseManager;

    @Before
    public void setup(){
        databaseManager = new JDBCDatabaseManager();
        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    private String getData() {
        try {
           return new String(out.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    @Test
    public void testHelp() {
        //given
        //in.add("connect|namelist|postgres|root");
        in.add("help");
        in.add("exit");


        //when
        Main.main(new String[0]);

        //then
        assertEquals("привет\r\n" +
                "введи, имя базы, логин, пароль в формате: connect|databaseName|userName|password\r\n" +
                "список команд:\r\n" +
                "\r\n" +
                "connect|databaseName|userName|password\r\n" +
                "для подключения к базе данных, с которой будем работать\r\n" +
                "\r\n" +
                "help\r\n" +
                "список команд\r\n" +
                "\r\n" +
                "exit\r\n" +
                "выход\r\n" +
                "\r\n" +
                "tables\r\n" +
                "список таблиц\r\n" +
                "\r\n" +
                "find|tableName\r\n" +
                "посмотреть содержимое таблицы 'tableName'\r\n" +
                "\r\n" +
                "create|tableName|column1(PK)|column2|...|columnN\r\n" +
                "создание таблицы 'tableName'\r\n" +
                "\r\n" +
                "drop|tableName\r\n" +
                "удаление таблицы 'tableName'\r\n" +
                "\r\n" +
                "clear|tableName\r\n" +
                "очистка таблицы 'tableName'\r\n" +
                "\r\n" +
                "insert|tableName|column1|value1|column2|value2|...|columnN|valueN\r\n" +
                "создание записи в таблице 'tableName'\r\n" +
                "\r\n" +
                "delete|tableName|column|value\r\n" +
                "создание записи в таблице 'tableName' из колонки 'column' значение 'value'" +
                "\r\n" +
                "update|tableName|id|column|newvalue\r\n" +
                "обновление записи таблице 'tableName' №id в колонке 'column' запись обновлена\r\n" +
                "\r\n" +
                "введи команду или help:\r\n" +
                "до встречи!\r\n", getData());
    }

    @Test
    public void testExit() {
        //given
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("привет\r\n" +
                "введи, имя базы, логин, пароль в формате: connect|databaseName|userName|password\r\n" +
                "до встречи!\r\n", getData());
    }

    @Test
    public void testListWhithoutConnect() {
        //given
        //in.add("connect|namelist|postgres|root");
        in.add("tables");
        in.add("exit");

        //when
        Main.main(new String[0]);


        //then
        assertEquals("привет\r\n" +
                "введи, имя базы, логин, пароль в формате: connect|databaseName|userName|password\r\n" +
                "вы не можете пользоваться командой, tables, пока не подключитесь с помощью команды connect|databaseName|userName|password\r\n" +
                "введи команду или help:\r\n" +
                "до встречи!\r\n", getData());

    }

    @Test
    public void testFindWhithoutConnect() {
        //given
        in.add("find|newlist");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("привет\r\n" +
                "введи, имя базы, логин, пароль в формате: connect|databaseName|userName|password\r\n" +
                "вы не можете пользоваться командой, find|newlist, пока не подключитесь с помощью команды connect|databaseName|userName|password\r\n" +
                "введи команду или help:\r\n" +
                "до встречи!\r\n", getData());

    }

    @Test
    public void testUnsupported() {
        //given
        in.add("unsupported");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("привет\r\n" +
                "введи, имя базы, логин, пароль в формате: connect|databaseName|userName|password\r\n" +
                "несуществующая команда:unsupported\r\n" +
                "введи команду или help:\r\n" +
                "до встречи!\r\n", getData());

    }

    @Test
    public void testUnsupportedAfterConnect() {
        //given
        in.add("connect|namelist|postgres|root");
        in.add("unsupported");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("привет\r\n" +
                "введи, имя базы, логин, пароль в формате: connect|databaseName|userName|password\r\n" +
                "ок\r\n" +
                "введи команду или help:\r\n" +
                "несуществующая команда:unsupported\r\n" +
                "введи команду или help:\r\n" +
                "до встречи!\r\n", getData());

    }

    @Test
    public void testListAfterConnect() {
        //given
        in.add("connect|namelist|postgres|root");
        in.add("tables");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("привет\r\n" +
                "введи, имя базы, логин, пароль в формате: connect|databaseName|userName|password\r\n" +
                "ок\r\n" +
                "введи команду или help:\r\n" +
                //tables
                "[supertable, newlist, newtable]\r\n" +
                "введи команду или help:\r\n" +
                "до встречи!\r\n", getData());
    }

    @Test
    public void testFindAfterConnect() {
        //given
        in.add("connect|namelist|postgres|root");
        in.add("find|newlist");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("привет\r\n" +
                "введи, имя базы, логин, пароль в формате: connect|databaseName|userName|password\r\n" +
                "ок\r\n" +
                "введи команду или help:\r\n" +
                //find|newlist
                "+---+---------------+---------------+\r\n" +
                "| id|           name|       password|\r\n" +
                "+---+---------------+---------------+\r\n" +
                "| 11|            xxx|            ccc|\r\n" +
                "+---+---------------+---------------+\r\n" +
                "| 12|            vvv|            bbb|\r\n" +
                "+---+---------------+---------------+\r\n" +
                "введи команду или help:\r\n" +
                "до встречи!\r\n", getData());
    }

    @Test
    public void testConnectWhithError() {
        //given
        in.add("connect|namelist");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("привет\r\n" +
                "введи, имя базы, логин, пароль в формате: connect|databaseName|userName|password\r\n" +
                "ошибка!ожидается 4 параметра, а есть: 2\r\n" +
                "попробуй еще раз\r\n" +
                "введи команду или help:\r\n" +
                "до встречи!\r\n", getData());
    }

    @Test
    public void testFindAfterConnect_whithData() {
        in.add("connect|namelist|postgres|root");
        in.add("clear|newlist");
        in.add("insert|newlist|id|11|name|xxx|password|ccc");
        in.add("insert|newlist|id|12|name|vvv|password|bbb");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("привет\r\n" +
                "введи, имя базы, логин, пароль в формате: connect|databaseName|userName|password\r\n" +
                "ок\r\n" +
                "введи команду или help:\r\n" +
                "таблица newlist очищена\r\n" +
                "введи команду или help:\r\n" +
                "в таблицу newlist запись добавлена\r\n" +
                "введи команду или help:\r\n" +
                "в таблицу newlist запись добавлена\r\n" +
                "введи команду или help:\r\n" +
                "до встречи!\r\n", getData());
    }

    @Test
    public void testClearWhithError() {
        //given
        in.add("connect|namelist|postgres|root");
        in.add("clear|asd|sdf");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("привет\r\n" +
                "введи, имя базы, логин, пароль в формате: connect|databaseName|userName|password\r\n" +
                "ок\r\n" +
                "введи команду или help:\r\n" +
                "ошибка!формат команды 'clear|tableName', а ты ввел:clear|asd|sdf\r\n" +
                "попробуй еще раз\r\n" +
                "введи команду или help:\r\n" +
                "до встречи!\r\n", getData());
    }

    @Test
    public void testCreateWhithError() {
        //given
        in.add("connect|namelist|postgres|root");
        in.add("insert|newlist|error");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("привет\r\n" +
                "введи, имя базы, логин, пароль в формате: connect|databaseName|userName|password\r\n" +
                "ок\r\n" +
                "введи команду или help:\r\n" +
                "ошибка!должно быть четное колличесво параметров, в формате 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN', а есть:insert|newlist|error\r\n" +
                "попробуй еще раз\r\n" +
                "введи команду или help:\r\n" +
                "до встречи!\r\n", getData());
    }
}
