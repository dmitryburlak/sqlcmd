package ua.com.juja.sqlcmd.controller.web;

import ua.com.juja.sqlcmd.service.Servise;
import ua.com.juja.sqlcmd.service.ServiseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.com.juja.sqlcmd.view.MessageList.*;

public class DropAction extends AbstractAction {

    public DropAction(Servise servise) {
        super(servise);
    }

    @Override
    public boolean canProcess(String url) {
        return url.startsWith("/drop");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jsp("drop.jsp", req, resp);
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = req.getParameter("tableName");
        try {
            servise.drop(getManagerAfterConnect(req), tableName);
        } catch (ServiseException e) {
            errorJSP(req, resp, e);
        }
        req.setAttribute("message", String.format(DROP_TABLE.getMessage(), tableName));
        jsp("drop.jsp", req, resp);
    }
}
