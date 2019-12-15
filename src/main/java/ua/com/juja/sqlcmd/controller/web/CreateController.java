package ua.com.juja.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.com.juja.sqlcmd.controller.web.forms.Connection;
import ua.com.juja.sqlcmd.controller.web.forms.Create;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Servise;
import ua.com.juja.sqlcmd.service.ServiseConnect;
import ua.com.juja.sqlcmd.service.ServiseCreate;
import ua.com.juja.sqlcmd.service.ServiseException;

import javax.servlet.http.HttpSession;

import static ua.com.juja.sqlcmd.message.MessageList.*;

@Controller
public class CreateController {

    @Autowired
    private ServiseCreate serviseCreate;

    @Autowired
    private Servise servise;

    @Autowired
    private ServiseConnect serviseConnect;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String slash(){
        return "redirect:/menu";
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(Model model){
        model.addAttribute("items", servise.commandsList());
        return "menu";
    }

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connectGet(Model model, HttpSession session){
        String fromPage = (String) session.getAttribute("fromPage");
        session.removeAttribute("fromPage");
        model.addAttribute("connection", new Connection(fromPage));
        if (!isConnected(session)) {
            return "connect";
        } else {
            return "redirect:/menu";
        }
    }

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public String connectPost(@ModelAttribute("connection") Connection connection,
                              Model model, HttpSession session) {
        try{
            serviseConnect.connect(connection.getDatabase(),
                    connection.getUserName(), connection.getPassword());
            session.setAttribute("db_manager", serviseConnect.getManager());
            return "redirect:" + connection.getFromPage();
        } catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createGet(Model model, HttpSession session){
        if (!isConnected(session)) {
            session.setAttribute("fromPage", "/create");
            return "redirect:/connect";
        } else {
            model.addAttribute("create", new Create());
            return "create";
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createPost(@ModelAttribute("create") Create create, Model model){
        try {
            serviseCreate.create(create.getTableName(), create.getColumnPk(),
                    create.getColumnone(), create.getColumntwo());
        } catch (ServiseException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
        model.addAttribute("message", String.format(CREATE_TABLE.getMessage(), create.getTableName()));
        return "create";
    }

    private DatabaseManager getManager(HttpSession session) {
        return (DatabaseManager) session.getAttribute("db_manager");
    }

    private boolean isConnected(HttpSession session){
        if (getManager(session) == null) {
            return false;
        } else {
            return true;
        }
    }
}
