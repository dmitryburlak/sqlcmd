package ua.com.juja.sqlcmd.controller.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ua.com.juja.sqlcmd.controller.web.forms.Connection;
import ua.com.juja.sqlcmd.controller.web.forms.Create;
import ua.com.juja.sqlcmd.controller.web.forms.Insert;
import ua.com.juja.sqlcmd.controller.web.forms.Update;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.service.Servise;


import java.util.Arrays;


import static java.lang.String.valueOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class MainControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private MainController mainController;

    @Mock
    private Servise service;

    @BeforeEach
    public void setupMock() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/webapp/views/");
        viewResolver.setSuffix(".jsp");

        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(mainController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void slash_get_Test() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/menu"));
    }

    @Test
    public void menu_get_Test() throws Exception {
        when(service.commandsList())
                .thenReturn(Arrays.asList("help", "tables", "find", "create", "drop", "clear", "insert", "delete", "update"));

        mockMvc.perform(get("/menu"))
                .andDo(print())
                .andExpect(model().attribute("items", service.commandsList()))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/webapp/views/menu.jsp"));
    }

    @Test
    public void help_get_Test() throws Exception {
        mockMvc.perform(get("/help"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/webapp/views/help.jsp"));
    }

    @Test
    public void tables_notConnected_get_Test() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/tables")
                .sessionAttr("fromPage", "/tables");

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/connect"));
    }

    @Test
    public void tables_isConnected_get_Test() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/tables")
                .sessionAttr("db_manager", getManager());

        mockMvc.perform(builder)
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("items", service.tables()))
                    .andExpect(forwardedUrl("/webapp/views/tables.jsp"));

        when(service.tables()).thenReturn("newlist, supertable");
    }

    @Test
    public void connect_notConnected_get_Test() throws Exception {
        String fromPage = "/menu";
        Connection connection = new Connection(fromPage);

        mockMvc.perform(get("/connect"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("connection", instanceOf(connection.getClass())))
                .andExpect(forwardedUrl("/webapp/views/connect.jsp"));
    }

    @Test
    public void connect_notConnected_post_Test() throws Exception {
        Connection connection = new Connection();
        connection.setFromPage("/menu");
        connection.setDatabase("namelist");
        connection.setUserName("postgres");
        connection.setPassword("root");

        doNothing().when(service)
               .connect(connection.getDatabase(), connection.getUserName(), connection.getPassword());

        when(service.getManager()).thenReturn(getManager());
        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("db_manager", getManager());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/connect", connection)
                        .sessionAttr("fromPage", connection.getFromPage())
                        .param("database", connection.getDatabase())
                        .param("userName", connection.getUserName())
                        .param("Password", connection.getPassword())
                        .param("fromPage", connection.getFromPage());

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attribute("connection", instanceOf(connection.getClass())))
                .andExpect(redirectedUrl(connection.getFromPage()));

        verify(service, times(1))
                .connect(connection.getDatabase(), connection.getUserName(), connection.getPassword());

        verify(service, times(1)).getManager();
    }

    @Test
    public void find_notConnected_get_Test() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/find")
                .sessionAttr("fromPage", "/find");

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/connect"));
    }

    @Test
    public void find_isConnected_get_Test() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/find")
                .sessionAttr("db_manager", getManager());

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("items", service.tables()))
                .andExpect(forwardedUrl("/webapp/views/find.jsp"));

        when(service.tables()).thenReturn("newlist, supertable");
    }

    @Test
    public void find_post_Test() throws Exception {
        String tableName = "newlist";
        when(service.find(tableName)).thenReturn(Arrays.asList(Arrays.asList("tables data")));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/find")
                .sessionAttr("db_manager", getManager())
                .param("tableName", tableName);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("tableName", service.find(tableName)))
                .andExpect(model().attribute("message", tableName))
                .andExpect(forwardedUrl("/webapp/views/openTable.jsp"));
    }

    @Test
    public void create_notConnected_get_Test() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/create")
                .sessionAttr("fromPage", "/create");

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/connect"));
    }

    @Test
    public void create_isConnected_get_Test() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/create")
                .sessionAttr("db_manager", getManager());

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("create", instanceOf(Create.class)))
                .andExpect(forwardedUrl("/webapp/views/create.jsp"));
    }

    @Test
    public void create_post_Test() throws Exception {
        Create create = new Create();
        create.setTableName("someName");
        create.setColumnPk("id");
        create.setColumnone("someColumnOne");
        create.setColumntwo("someColumnTwo");

        doNothing().when(service)
                .create(create.getTableName(), create.getColumnPk(), create.getColumnone(), create.getColumntwo());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/create", create)
                .sessionAttr("db_manager", getManager())
                .param("tableName", create.getTableName())
                .param("columnPk", create.getColumnPk())
                .param("columnone", create.getColumnone())
                .param("columntwo", create.getColumntwo());

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("create", instanceOf(create.getClass())))
                .andExpect(model().attribute("message", "таблица " + create.getTableName() + " создана"))
                .andExpect(forwardedUrl("/webapp/views/create.jsp"));

        verify(service, times(1))
                .create(create.getTableName(), create.getColumnPk(), create.getColumnone(), create.getColumntwo());
    }

    @Test
    public void drop_notConnected_get_Test() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/drop")
                .sessionAttr("fromPage", "/drop");

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/connect"));
    }

    @Test
    public void drop_isConnected_get_Test() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/drop")
                .sessionAttr("db_manager", getManager());

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/webapp/views/drop.jsp"));
    }

    @Test
    public void drop_post_Test() throws Exception {
        String tableName = "somethingName";
        doNothing().when(service).drop(tableName);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/drop")
                .sessionAttr("db_manager", getManager())
                .param("tableName", tableName);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", "таблица " + tableName + " удалена"))
                .andExpect(forwardedUrl("/webapp/views/drop.jsp"));

        verify(service, times(1)).drop(tableName);
    }

    @Test
    public void clear_notConnected_get_Test() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/clear")
                .sessionAttr("fromPage", "/clear");

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/connect"));
    }

    @Test
    public void clear_isConnected_get_Test() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/clear")
                .sessionAttr("db_manager", getManager());

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/webapp/views/clear.jsp"));
    }

    @Test
    public void clear_post_Test() throws Exception {
        String tableName = "someName";
        doNothing().when(service).clear(tableName);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/clear")
                .sessionAttr("db_manager", getManager())
                .param("tableName", tableName);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", "таблица " + tableName + " очищена"))
                .andExpect(forwardedUrl("/webapp/views/clear.jsp"));

        verify(service, times(1)).clear(tableName);
    }

    @Test
    public void insert_notConnected_get_Test() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/insert")
                .sessionAttr("fromPage", "/insert");

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/connect"));
    }

    @Test
    public void insert_isConnected_get_Test() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/insert")
                .sessionAttr("db_manager", getManager());

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("insert", instanceOf(Insert.class)))
                .andExpect(forwardedUrl("/webapp/views/insert.jsp"));
    }

    @Test
    public void insert_post_Test() throws Exception {
        Insert insert = new Insert();
        insert.setTableName("someName");
        insert.setColumn("someColumnOne");
        insert.setValue("one");
        insert.setColumnsecond("someColumnTwo");
        insert.setColumnsecond("two");

        doNothing().when(service).insert(insert.getTableName(),
                insert.getColumn(), insert.getValue(), insert.getColumnsecond(), insert.getValuesecond());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/insert", insert)
                .sessionAttr("db_manager", getManager())
                .param("tableName", insert.getTableName())
                .param("column", insert.getColumn())
                .param("value", insert.getValue())
                .param("columnsecond", insert.getColumnsecond())
                .param("valuesecond", insert.getValuesecond());

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("insert", instanceOf(insert.getClass())))
                .andExpect(model().attribute("message", "в таблицу "+ insert.getTableName() +" запись добавлена"))
                .andExpect(forwardedUrl("/webapp/views/insert.jsp"));

        verify(service, times(1)).insert(insert.getTableName(),
                        insert.getColumn(), insert.getValue(), insert.getColumnsecond(), insert.getValuesecond());
        service.drop("someName");
    }

    @Test
    public void delete_notConnected_get_Test() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/delete")
                .sessionAttr("fromPage", "/delete");

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/connect"));
    }

    @Test
    public void delete_isConnected_get_Test() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/delete")
                .sessionAttr("db_manager", getManager());

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/webapp/views/delete.jsp"));
    }

    @Test
    public void delete_post_Test() throws Exception {
        String tableName = "someName";
        String column = "column";
        String value = "value";
        doNothing().when(service).delete(tableName, column, value);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/delete")
                .sessionAttr("db_manager", getManager())
                .param("tableName", tableName)
                .param("column", column)
                .param("value", value);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", "в таблицe " + tableName + " из колонки " + column + " запись " + value + " удалена"))
                .andExpect(forwardedUrl("/webapp/views/delete.jsp"));

        verify(service, times(1)).delete(tableName, column, value);
    }

    @Test
    public void update_notConnected_get_Test() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/update")
                .sessionAttr("fromPage", "/update");

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/connect"));
    }

    @Test
    public void update_isConnected_get_Test() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/update")
                .sessionAttr("db_manager", getManager());

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("update", instanceOf(Update.class)))
                .andExpect(forwardedUrl("/webapp/views/update.jsp"));
    }

    @Test
    public void update_post_Test() throws Exception {
        Update update = new Update();
        update.setTableName("someName");
        update.setId(1);
        update.setColumn("column");
        update.setValue("value");

        doNothing().when(service).update(update.getTableName(), update.getId(), update.getColumn(), update.getValue());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/update", update)
                .sessionAttr("db_manager", getManager())
                .param("tableName", update.getTableName())
                .param("id", valueOf(update.getId()))
                .param("column", update.getColumn())
                .param("value", update.getValue());

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("update", instanceOf(update.getClass())))
                .andExpect(model().attribute("message", "в таблице someName id " + valueOf(update.getId()) + " в колонке "+ update.getColumn() + " запись обновлена"))
                .andExpect(forwardedUrl("/webapp/views/update.jsp"));

        verify(service, times(1))
                .update(update.getTableName(), update.getId(), update.getColumn(), update.getValue());
    }

    private DatabaseManager getManager(){
        return new JDBCDatabaseManager();
    }
}
