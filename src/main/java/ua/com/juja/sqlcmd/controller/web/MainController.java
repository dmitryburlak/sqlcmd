package ua.com.juja.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.com.juja.sqlcmd.service.Servise;

@Controller
public class MainController {

    @Autowired
    private Servise servise;

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help(){
        return "help";
    }
}
