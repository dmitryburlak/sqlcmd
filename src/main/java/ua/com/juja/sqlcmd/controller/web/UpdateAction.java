package ua.com.juja.sqlcmd.controller.web;

import ua.com.juja.sqlcmd.service.Servise;
import ua.com.juja.sqlcmd.service.ServiseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.com.juja.sqlcmd.view.MessageList.UPDATE_TABLE_DATA;

public class UpdateAction extends AbstractAction {

    public UpdateAction(Servise servise) {
        super(servise);
    }

    @Override
    public boolean canProcess(String url) {
        return url.startsWith("/update");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jsp("update.jsp", req, resp);
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = req.getParameter("tableName");
        int id = Integer.valueOf(req.getParameter("id"));
        String column = req.getParameter("column");
        String value = req.getParameter("value");
        try {
            servise.update(getManagerAfterConnect(req), tableName, id, column, value);
        } catch (ServiseException e) {
            errorJSP(req, resp, e);
        }
        req.setAttribute("message", String.format(UPDATE_TABLE_DATA.getMessage(), tableName, id, column));
        jsp("update.jsp", req, resp);
    }
}
