package ua.com.juja.sqlcmd.integration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.Main;


import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;


public class IntegrationTest {

    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;

    @Before
    public void setup(){
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
                "\tlist\r\n" +
                "\t\tсписок таблиц\r\n" +
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
        in.add("list");
        in.add("exit");

        //when
        Main.main(new String[0]);


        //then
        assertEquals("привет\r\n" +
                "введи, имя базы, логин, пароль в формате: connect|databaseName|userName|password\r\n" +
                "вы не можете пользоваться командой, list, пока не подключитесь с помощью команды connect|databaseName|userName|password\r\n" +
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
        in.add("list");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("привет\r\n" +
                "введи, имя базы, логин, пароль в формате: connect|databaseName|userName|password\r\n" +
                "ок\r\n" +
                "введи команду или help:\r\n" +
                //list
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
                "введи команду или help:\r\n" +
                "до встречи!\r\n", getData());

    }


}
