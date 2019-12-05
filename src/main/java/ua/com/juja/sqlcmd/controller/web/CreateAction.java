package ua.com.juja.sqlcmd.controller.web;

import ua.com.juja.sqlcmd.service.Servise;
import ua.com.juja.sqlcmd.service.ServiseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.com.juja.sqlcmd.view.MessageList.*;

public class CreateAction extends AbstractAction {

    public CreateAction(Servise servise) {
        super(servise);
    }

    @Override
    public boolean canProcess(String url) {
        return url.startsWith("/create");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jsp("create.jsp", req, resp);
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = req.getParameter("tableName");
        String columnPk = req.getParameter("columnPk");
        String columnone = req.getParameter("columnone");
        String columntwo = req.getParameter("columntwo");
        try {
            servise.create(getManagerAfterConnect(req), tableName, columnPk, columnone, columntwo);
        } catch (ServiseException e) {
            errorJSP(req, resp, e);
        }
        req.setAttribute("message", String.format(CREATE_TABLE.getMessage(), tableName));
        jsp("create.jsp", req, resp);
    }
}

