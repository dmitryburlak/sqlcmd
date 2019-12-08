package ua.com.juja.sqlcmd.controller.web.actions;

import ua.com.juja.sqlcmd.controller.web.AbstractAction;
import ua.com.juja.sqlcmd.service.Servise;
import ua.com.juja.sqlcmd.service.ServiseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.com.juja.sqlcmd.view.MessageList.*;

public class DeleteAction extends AbstractAction {

    public DeleteAction(Servise servise) {
        super(servise);
    }

    @Override
    public boolean canProcess(String url) {
        return url.startsWith("/delete");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jsp("delete.jsp", req, resp);
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = req.getParameter("tableName");
        String column = req.getParameter("column");
        String value = req.getParameter("value");
        try {
            servise.delete(getManagerAfterConnect(req), tableName, column, value);
        } catch (ServiseException e) {
            errorJSP(req, resp, e);
        }
        req.setAttribute("message", String.format(DELETE_TABLE_DATA.getMessage(), tableName, column, value));
        jsp("delete.jsp", req, resp);
    }
}
