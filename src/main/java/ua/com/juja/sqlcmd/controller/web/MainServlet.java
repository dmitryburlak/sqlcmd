package ua.com.juja.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.ServiceImpl;
import ua.com.juja.sqlcmd.service.Servise;
import ua.com.juja.sqlcmd.service.ServiseException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.com.juja.sqlcmd.view.MessageList.*;

public class MainServlet extends HttpServlet {
    @Autowired
    private Servise servise;

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        if (action.startsWith("/connect")) {
            jsp("connect.jsp", resp, req);
            return;
        }

        DatabaseManager manager = getManager(req);

        if (manager == null){
            resp.sendRedirect("connect");
            return;
        }

        if(action.startsWith("/menu") || action.equals("/")){
            req.setAttribute("items", servise.commandsList());
            jsp("menu.jsp", resp, req);

        } else if (action.startsWith("/help")){
            jsp("help.jsp", resp, req);

        } else if (action.startsWith("/find")) {
            String tableName = req.getParameter("table");
            try {
                req.setAttribute("table", servise.find(manager, tableName));
            } catch (ServiseException e) {
                errorJSP(req, resp, e);
            }
            jsp("find.jsp", resp, req);

        }else if (action.startsWith("/tables")){
            try {
                req.setAttribute("items", servise.tables(manager));
            } catch (ServiseException e) {
                errorJSP(req, resp, e);
            }
            jsp("tables.jsp", resp, req);

        }else if (action.startsWith("/insert")){
            jsp("insert.jsp", resp, req);

        }else if (action.startsWith("/delete")){
            jsp("delete.jsp", resp, req);

        }else if (action.startsWith("/create")) {
            jsp("create.jsp", resp, req);

        }else if (action.startsWith("/drop")){
            jsp("drop.jsp", resp, req);

        }else if (action.startsWith("/update")){
            jsp("update.jsp", resp, req);

        } else {
            jsp("error.jsp", resp, req);
        }
    }

    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return requestURI.substring(req.getContextPath().length(), requestURI.length());
    }

    private DatabaseManager getManager(HttpServletRequest req) {
        return (DatabaseManager) req.getSession().getAttribute("db_manager");
    }

    private void jsp(String namejsp, HttpServletResponse resp, HttpServletRequest req) throws ServletException, IOException {
        req.getRequestDispatcher(namejsp).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        if (action.startsWith("/connect")) {
            serviseConnect(req, resp);
        }

       DatabaseManager manager = getManager(req);

        if(action.startsWith("/insert")) {
            serviseInsert(manager, req, resp);

        } else if (action.startsWith("/delete")){
            serviseDelete(manager, req, resp);

        } else if (action.startsWith("/create")){
            serviseCreate(manager, req, resp);

        } else if (action.startsWith("/drop")){
            serviseDrop(manager, req, resp);

        }else if (action.startsWith("/update")){
            serviseUpdate(manager, req, resp);

        } else {
           // do nothing
        }
    }

    private void serviseConnect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String database = req.getParameter("database");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        try{
            DatabaseManager manager = servise.connect(database, userName, password);
            req.getSession().setAttribute("db_manager", manager);
            resp.sendRedirect("menu");
        } catch (Exception e){
            errorJSP(req, resp, e);
        }
    }

    private void serviseInsert(DatabaseManager manager, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = req.getParameter("tableName");
        String column = req.getParameter("column");
        String value = req.getParameter("value");
        String columnsecond = req.getParameter("columnsecond");
        String valuesecond = req.getParameter("valuesecond");
        try {
            servise.insert(manager, tableName, column, value, columnsecond, valuesecond);
        } catch (ServiseException e) {
            errorJSP(req, resp, e);
        }
        req.setAttribute("message", String.format(INSERT_TABLE_DATA.getMessage(), tableName));
        jsp("insert.jsp", resp, req);
    }

    private void serviseDelete(DatabaseManager manager, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = req.getParameter("tableName");
        String column = req.getParameter("column");
        String value = req.getParameter("value");
        try {
            servise.delete(manager, tableName, column, value);
        } catch (ServiseException e) {
            errorJSP(req, resp, e);
        }
        req.setAttribute("message", String.format(DELETE_TABLE_DATA.getMessage(), tableName, column, value));
        jsp("delete.jsp", resp, req);
    }

    private void serviseCreate(DatabaseManager manager, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = req.getParameter("tableName");
        String columnPk = req.getParameter("columnPk");
        String columnone = req.getParameter("columnone");
        String columntwo = req.getParameter("columntwo");
        try {
            servise.create(manager, tableName, columnPk, columnone, columntwo);
        } catch (ServiseException e) {
            errorJSP(req, resp, e);
        }
        req.setAttribute("message", String.format(CREATE_TABLE.getMessage(), tableName));
        jsp("create.jsp", resp, req);
    }

    private void serviseDrop(DatabaseManager manager, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = req.getParameter("tableName");
        try {
            servise.drop(manager, tableName);
        } catch (ServiseException e) {
            errorJSP(req, resp, e);
        }
        req.setAttribute("message", String.format(DROP_TABLE.getMessage(), tableName));
        jsp("drop.jsp", resp, req);
    }

    private void serviseUpdate(DatabaseManager manager, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = req.getParameter("tableName");
        int id = Integer.valueOf(req.getParameter("id"));
        String column = req.getParameter("column");
        String value = req.getParameter("value");
        try {
            servise.update(manager, tableName, id, column, value);
        } catch (ServiseException e) {
            errorJSP(req, resp, e);
        }
        req.setAttribute("message", String.format(UPDATE_TABLE_DATA.getMessage(), tableName, id, column));
        jsp("update.jsp", resp, req);
    }

    private void errorJSP(HttpServletRequest req, HttpServletResponse resp, Exception e) throws ServletException, IOException {
        req.setAttribute("message", e.getMessage());
        try {
            e.printStackTrace();
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
