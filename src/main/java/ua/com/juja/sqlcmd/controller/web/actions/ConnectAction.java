package ua.com.juja.sqlcmd.controller.web.actions;

import ua.com.juja.sqlcmd.controller.web.AbstractAction;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Servise;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ConnectAction extends AbstractAction {

    public ConnectAction(Servise servise) {
        super(servise);
    }

    @Override
    public boolean canProcess(String url) {
        return url.equalsIgnoreCase("/connect");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jsp("connect.jsp", req, resp);

    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String database = req.getParameter("database");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        try{
            DatabaseManager manager = (DatabaseManager) servise.connect(database, userName, password);
            req.getSession().setAttribute("db_manager", manager);
            resp.sendRedirect("menu");
        } catch (Exception e){
            e.printStackTrace();
            errorJSP(req, resp, e);
        }
    }

}
