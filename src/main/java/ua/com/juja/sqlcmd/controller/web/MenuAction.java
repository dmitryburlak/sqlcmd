package ua.com.juja.sqlcmd.controller.web;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Servise;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MenuAction extends AbstractAction {

    public MenuAction(Servise servise) {
        super(servise);
    }

    @Override
    public boolean canProcess(String url) {
        return url.startsWith("/menu") || url.equals("/");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       DatabaseManager manager = getManagerAfterConnect(req);
       if (manager != null) {
            req.setAttribute("items", servise.commandsList());
            jsp("menu.jsp", req, resp);
       } else {
           resp.sendRedirect("connect");
       }
    }
}
