package ua.com.juja.sqlcmd.servise;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.juja.sqlcmd.model.DatabaseConnect;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.model.UserActionRepository;
import ua.com.juja.sqlcmd.model.entity.UserAction;
import ua.com.juja.sqlcmd.service.ServiceComponent;
import ua.com.juja.sqlcmd.service.ServiceComponentImpl;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ServiceComponentTest {
    private ServiceComponent serviceComponent;

    private DatabaseConnect connectManager;
    private DatabaseManager manager;
    private UserActionRepository userActions;

    @Before
    public void setupMock() {
        this.connectManager = mock(DatabaseConnect.class);
        this.manager = mock(JDBCDatabaseManager.class);
        this.userActions = mock(UserActionRepository.class);
        this.serviceComponent = new ServiceComponentImpl(connectManager, manager, userActions);
    }

    @Test
    public void connect_isConnected_test() {
        //given
        final String database = "namelist"; //valid
        final String userName = "postgres";
        final String password = "root";

        //when
        serviceComponent.connect(database, userName, password);

        //then
        when(connectManager.connect(database, userName, password))
                .thenReturn(connectManager.getConnection());
        verify(connectManager, times(1))
                .connect(database, userName, password);
        verify(userActions, times(1))
                .createAction(manager.getUserName(), manager.getDbName(), "CONNECT");
    }

    @Test
    public void connect_notConnected_test() {
        //given
        final String database = "xxx"; //not valid
        final String userName = "postgres";
        final String password = "root";

        //when
        serviceComponent.connect(database, userName, password);

        //then
        when(connectManager.connect(database, userName, password))
                .thenThrow(new RuntimeException());
        verify(connectManager, times(1))
                .connect(database, userName, password);
        verify(userActions, never())
                .createAction(null, null, null);
    }

    @Test
    public void commandsList_test() {
        //given
        //when
        List<String> expectedResult = serviceComponent.commandsList();

        //then
        assertEquals("[help, tables, find, create, drop, clear, insert, delete, update]",
                expectedResult.toString());
    }

    @Test
    public void find_test() {
        //given
        String tableName = "someName";
        Set<String> columns = null;
        List<Map<String, Object>> tableData = null;

        //when
        serviceComponent.find(tableName);

        //then
        when(manager.getTableCloumns(tableName)).thenReturn(columns);
        when(manager.getTableDataSet(tableName)).thenReturn(tableData);
        verify(manager, times(1)).getTableCloumns(tableName);
        verify(manager, times(1)).getTableDataSet(tableName);
        verify(userActions, times(1))
                .createAction(manager.getUserName(), manager.getDbName(), "FIND (" + tableName + ")");

    }

    @Test
    public void tables_test() {
        //given
        Set<String> tables = null;

        //when
        serviceComponent.tables();

        //then
        when(manager.getTables()).thenReturn(tables);
        verify(manager, times(1)).getTables();
        verify(userActions, times(1))
                .createAction(manager.getUserName(), manager.getDbName(), "LIST TABLES");
    }

    @Test
    public void insert_test() {
        //given
        String tableName = "someName";
        String column = "column";
        String value = "value";
        String columnSecond = "columnSecond";
        String valueSecond = "valueSecond";

        Map<String, Object> input = new LinkedHashMap<>();
        input.put(column, value);
        input.put(columnSecond, valueSecond);

        //when
        serviceComponent.insert(tableName, column, value, columnSecond, valueSecond);

        //then
        verify(manager, times(1)).insert(tableName, input);
        verify(userActions, times(1))
                .createAction(manager.getUserName(), manager.getDbName(), "INSERT DATA");
    }

    @Test
    public void delete_test() {
        //given
        String tableName = "someName";
        String column = "column";
        String value = "value";

        Map<String, Object> input = new LinkedHashMap<>();
        input.put(column, value);

        //when
        serviceComponent.delete(tableName, column, value);

        //then
        verify(manager, times(1)).delete(tableName, input);
        verify(userActions, times(1))
                .createAction(manager.getUserName(), manager.getDbName(), "DELETE DATA");
    }

    @Test
    public void create_test() {
        //given
        String tableName = "someName";
        String columnPk = "ID";
        String columnOne = "columnOneName";
        String columnTwo = "columnTwoName";

        Set<String> input = new LinkedHashSet<>();
        input.add(columnOne);
        input.add(columnTwo);

        //when
        serviceComponent.create(tableName, columnPk, columnOne, columnTwo);

        //then
        verify(manager, times(1)).create(tableName, columnPk, input);
        verify(userActions, times(1))
                .createAction(manager.getUserName(), manager.getDbName(), "CREATE TABLE");
    }

    @Test
    public void drop_test() {
        //given
        String tableName = "someName";

        //when
        serviceComponent.drop(tableName);

        //then
        verify(manager, times(1)).drop(tableName);
        verify(userActions, times(1))
                .createAction(manager.getUserName(), manager.getDbName(), "DROP TABLE");
    }

    @Test
    public void update_test() {
        //given
        String tableName = "someName";
        int id = 1;
        String column = "someColumn";
        String value = "newValue";

        Map<String, Object> input = new LinkedHashMap<>();
        input.put(column, value);

        //when
        serviceComponent.update(tableName, id, column, value);

        //then
        verify(manager, times(1)).update(tableName, id, input);
        verify(userActions, times(1))
                .createAction(manager.getUserName(), manager.getDbName(), "UPDATE DATA");
    }

    @Test
    public void clear_test() {
        //given
        String tableName = "someName";

        //when
        serviceComponent.clear(tableName);

        //then
        verify(manager, times(1)).clear(tableName);
        verify(userActions, times(1))
                .createAction(manager.getUserName(), manager.getDbName(), "CLEAR TABLE");
    }

    @Test
    public void getAllFor_test() {
        //given
        String tableName = "someName";
        List<UserAction> userAction = null;

        //when
        serviceComponent.getAllFor(tableName);

        //then
        when(userActions.findByName(tableName)).thenReturn(userAction);
        verify(userActions, times(1)).findByName(tableName);
    }






}
