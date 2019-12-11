package ua.com.juja.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Servise;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private Servise servise;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String slash(){
        return "redirect:/menu";
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(Model model){
        model.addAttribute("items", servise.commandsList());
        return "menu";
    }

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help(){
        return "help";
    }

    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public String tables(Model model, HttpSession session){
        DatabaseManager manager = getManager(session);
        if (manager == null){
            session.setAttribute("fromPage", "/tables");
            return "redirect:/connect";
        }
        model.addAttribute("items", servise.tables(manager));
        return "tables";
    }

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connect(Model model, HttpSession session){
        String fromPage = (String) session.getAttribute("fromPage");
        session.removeAttribute("fromPage");
        model.addAttribute("connection", new Connection(fromPage));
        if (getManager(session) == null) {
            return "connect";
        } else {
            return "redirect:/menu";
        }
    }

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public String connecting(@ModelAttribute("connection") Connection connection,
                             Model model, HttpSession session){
        try{
            DatabaseManager manager = servise.connect(connection.getDatabase(),
                    connection.getUserName(), connection.getPassword());
            session.setAttribute("db_manager", manager);
            return "redirect:" + connection.getFromPage();
        } catch (Exception e){
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String find(Model model, HttpSession session){
        DatabaseManager manager = getManager(session);
        if (manager == null) {
            session.setAttribute("fromPage", "/find");
            return "redirect:/connect";
        }
        model.addAttribute("items", servise.tables(manager));
        return "find";
    }


    private DatabaseManager getManager(HttpSession session) {
        return (DatabaseManager) session.getAttribute("db_manager");
    }
}
