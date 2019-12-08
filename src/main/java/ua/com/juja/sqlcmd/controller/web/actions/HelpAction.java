package ua.com.juja.sqlcmd.controller.web.actions;

import ua.com.juja.sqlcmd.controller.web.AbstractAction;
import ua.com.juja.sqlcmd.service.Servise;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelpAction extends AbstractAction {

    public HelpAction(Servise servise) {
        super(servise);
    }

    @Override
    public boolean canProcess(String url) {
        return url.startsWith("/help");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jsp("help.jsp", req, resp);
    }
}
