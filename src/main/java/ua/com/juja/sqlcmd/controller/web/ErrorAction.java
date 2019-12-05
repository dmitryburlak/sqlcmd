package ua.com.juja.sqlcmd.controller.web;

import ua.com.juja.sqlcmd.service.Servise;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorAction extends AbstractAction {

    public ErrorAction(Servise servise) {
        super(servise);
    }

    @Override
    public boolean canProcess(String url) {
        return true;
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jsp("error.jsp", req, resp);
    }
}
