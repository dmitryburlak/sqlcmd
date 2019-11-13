package ua.com.juja.sqlcmd.controller.web;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.ServiceImpl;
import ua.com.juja.sqlcmd.service.Servise;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.com.juja.sqlcmd.view.MessageList.INSERT_TABLE_DATA;

public class MainServlet extends HttpServlet {
    private Servise servise;

    @Override
    public void init() throws ServletException{
        super.init();
        servise = new ServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        if (action.startsWith("/connect")) {
            req.getRequestDispatcher("connect.jsp").forward(req, resp);
            return;
        }

        DatabaseManager manager = (DatabaseManager) req.getSession().getAttribute("db_manager");

        if (manager == null){
            resp.sendRedirect("connect");
            return;
        }

        if(action.startsWith("/menu") || action.equals("/")){
            req.setAttribute("items", servise.commandsList());
            req.getRequestDispatcher("menu.jsp").forward(req, resp);

        } else if (action.startsWith("/help")){
            req.getRequestDispatcher("help.jsp").forward(req, resp);

        } else if (action.startsWith("/find")) {
            String tableName = req.getParameter("table");
            req.setAttribute("table", servise.find(manager, tableName));
            req.getRequestDispatcher("find.jsp").forward(req, resp);

        }else if (action.startsWith("/tables")){
            req.setAttribute("items", servise.tablesList(manager));
            req.getRequestDispatcher("tables.jsp").forward(req, resp);

        }else if (action.startsWith("/insert")){
            req.getRequestDispatcher("insert.jsp").forward(req, resp);

        }
        else {
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }

    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return requestURI.substring(req.getContextPath().length(), requestURI.length());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);
        if (action.startsWith("/connect")) {
            String database = req.getParameter("database");
            String userName = req.getParameter("userName");
            String password = req.getParameter("password");
            try{
                DatabaseManager manager = servise.connect(database, userName, password);
                req.getSession().setAttribute("db_manager", manager);
                resp.sendRedirect("menu");
            } catch (Exception e){
                req.setAttribute("message", e.getMessage());
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            }
        }

       DatabaseManager manager = (DatabaseManager) req.getSession().getAttribute("db_manager");

        if(action.startsWith("/insert")){
            String tableName = req.getParameter("tableName");
            String column = req.getParameter("column");
            String value = req.getParameter("value");
            String columnsecond = req.getParameter("columnsecond");
            String valuesecond = req.getParameter("valuesecond");
            servise.insert(manager, tableName, column, value, columnsecond, valuesecond);
            req.setAttribute("message", String.format(INSERT_TABLE_DATA.getMessage(), tableName));
            req.getRequestDispatcher("insert.jsp").forward(req, resp);
        }else {
           // do nothing
        }
    }
}
