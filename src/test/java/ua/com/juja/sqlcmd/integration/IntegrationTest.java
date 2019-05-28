package ua.com.juja.sqlcmd.integration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.Main;
import ua.com.juja.sqlcmd.model.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;


import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;


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
                "\tconnect|databaseName|userName|password\r\n" +
                "\t\tдля подключения к базе данных, с которой будем работать\r\n" +
                "\thelp\r\n" +
                "\t\tсписок команд\r\n" +
                "\texit\r\n" +
                "\t\tвыход\r\n" +
                "\tfind|tableName\r\n" +
                "\t\tсодержимое таблицы 'tableName'\r\n" +
                "\ttables\r\n" +
                "\t\tсписок таблиц\r\n" +
                "\tclear|tableName\r\n" +
                "\t\tочистка таблицы 'tableName'\r\n" +
                "\tcreate|tableName|column1|value1|column2|value2|...|columnN|valueN\r\n" +
                "\t\tсоздание записи в таблице 'tableName'\r\n" +
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
       // in.add("connect|namelist|postgres|root");
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
                "вы не можете пользоваться командой, unsupported, пока не подключитесь с помощью команды connect|databaseName|userName|password\r\n" +
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
                "[newlist]\r\n" +
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
                "-----------------\r\n" +
                "|id|name|lastname|\r\n" +
                "-----------------\r\n" +
                "|11|xxx|ccc|\r\n" +
                "|12|vvv|bbb|\r\n" +
                "-----------------\r\n" +
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
                "error!ожидается 4 параметра, а есть: 2\r\n" +
                "try again\r\n" +
                "введи команду или help:\r\n" +
                "до встречи!\r\n", getData());

    }

    @Test
    public void testFindAfterConnect_whithData() {

        //given
       /* databaseManager.connect("namelist", "postgres", "root");

        databaseManager.clear("newlist");

        DataSet name1 = new DataSet();
        name1.put("id", 10);
        name1.put("name", "xxx");
        name1.put("lastname", "ccc");
        databaseManager.create("newlist", name1);

        DataSet name2 = new DataSet();
        name2.put("id", 11);
        name2.put("name", "vvv");
        name2.put("lastname", "bbb");
        databaseManager.create("newlist", name2);
*/
        in.add("connect|namelist|postgres|root");
        in.add("clear|newlist");
        in.add("create|newlist|id|11|name|xxx|lastname|ccc");
        in.add("create|newlist|id|12|name|vvv|lastname|bbb");
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
                "в таблицу {names:[id, name, lastname], values:[11, xxx, ccc]} запись добавлена\r\n" +
                "введи команду или help:\r\n" +
                "в таблицу {names:[id, name, lastname], values:[12, vvv, bbb]} запись добавлена\r\n" +
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
                "error!формат команды 'clear|tableName', а ты ввел:clear|asd|sdf\r\n" +
                "try again\r\n" +
                "введи команду или help:\r\n" +
                "до встречи!\r\n", getData());

    }

    @Test
    public void testCreateWhithError() {

        //given
        in.add("connect|namelist|postgres|root");
        in.add("create|newlist|error");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("привет\r\n" +
                "введи, имя базы, логин, пароль в формате: connect|databaseName|userName|password\r\n" +
                "ок\r\n" +
                "введи команду или help:\r\n" +
                "error!должно быть четное колличесво параметров, в формате 'create|tableName|column1|value1|column2|value2|...|columnN|valueN', а есть:create|newlist|error\r\n" +
                "try again\r\n" +
                "введи команду или help:\r\n" +
                "до встречи!\r\n", getData());

    }



}
