package ua.com.juja.sqlcmd.servise;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ua.com.juja.sqlcmd.model.DatabaseConnect;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;

import ua.com.juja.sqlcmd.service.Servise;
import ua.com.juja.sqlcmd.service.ServiseException;

import java.sql.Connection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/application-context.xml")
public class ServiseConnectTest {

    @Autowired
    private Servise servise;

    public DatabaseManager getManager() {
        return mock(JDBCDatabaseManager.class);
    }

    @Test
    public void serviseConnectedTest() {
        //given
        DatabaseConnect connectManager = mock(DatabaseConnect.class);
        Connection getConnection = connectManager.getConnection();
        final String database = "namelist";
        final String userName = "postgres";
        final String password = "root";

        //when
        servise.connect(database, userName, password);

        //then
        when(connectManager.connect(database, userName, password))
                .thenReturn(getConnection);
        DatabaseManager manager = getManager();
        assertNotNull(manager);
    }

    @Test(expected = ServiseException.class)
    public void serviseNotConnectedTest() {
        //given
        DatabaseConnect connectManager = mock(DatabaseConnect.class);
        Connection NullConnection = connectManager.getConnection();
        final String database = "xxx";
        final String userName = "postgres";
        final String password = "root";

        //when
        servise.connect(database, userName, password);

        //then
        when(connectManager.connect(database, userName, password))
                .thenReturn(NullConnection).thenThrow(new RuntimeException());
        DatabaseManager manager = null;
        assertNull(manager);
    }
}
