package ua.com.juja.sqlcmd.controller.web;


import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Servise;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractAction implements Action {

    protected Servise servise;

    public AbstractAction(Servise servise) {
        this.servise = servise;
    }

    protected void jsp(String namejsp, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(namejsp).forward(req, resp);
    }

    protected void errorJSP(HttpServletRequest req, HttpServletResponse resp, Exception e) throws ServletException, IOException {
        req.setAttribute("message", e.getMessage());
        try {
            e.printStackTrace();
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    protected DatabaseManager getManagerAfterConnect(HttpServletRequest req) {
        DatabaseManager manager = (DatabaseManager) req.getSession().getAttribute("db_manager");
        return manager;
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //do nothing
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //do nothing
    }
}
