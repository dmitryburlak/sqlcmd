package ua.com.juja.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.juja.sqlcmd.controller.web.forms.Connection;
import ua.com.juja.sqlcmd.controller.web.forms.Create;
import ua.com.juja.sqlcmd.controller.web.forms.Insert;
import ua.com.juja.sqlcmd.controller.web.forms.Update;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Servise;
import ua.com.juja.sqlcmd.service.ServiseException;

import javax.servlet.http.HttpSession;

import java.util.Objects;

import static ua.com.juja.sqlcmd.message.MessageList.*;

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
        if (!isConnected(session)){
            session.setAttribute("fromPage", "/tables");
            return "redirect:/connect";
        }
        model.addAttribute("items", servise.tables());
        return "tables";
    }

    @RequestMapping(value = "/actions/{userName}", method = RequestMethod.GET)
    public String actions(Model model,
                          @PathVariable(value = "userName") String userName){
        model.addAttribute("userName", servise.getAllFor(userName));
        return "actions";
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
            servise.connect(connection.getDatabase(),
                    connection.getUserName(), connection.getPassword());
            session.setAttribute("db_manager", servise.getManager());
            return "redirect:" + connection.getFromPage();
        } catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String findGet(Model model, HttpSession session){
        if (!isConnected(session)) {
            session.setAttribute("fromPage", "/find");
            return "redirect:/connect";
        }
        model.addAttribute("items", servise.tables());
        return "find";
    }

    @RequestMapping(value = "/find", params = {"tableName"}, method = RequestMethod.POST)
    public String findPost(@RequestParam(value = "tableName") String tableName,
                           Model model){
        try{
            model.addAttribute("tableName", servise.find(tableName));
            model.addAttribute("message", tableName);
            return "openTable";
        }catch(ServiseException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

   @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createGet(Model model, HttpSession session){
        if (!isConnected(session)) {
            session.setAttribute("fromPage", "/create");
            return "redirect:/connect";
        }
        model.addAttribute("create", new Create());
        return "create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createPost(@ModelAttribute("create") Create create,
                             Model model){
        try {
            servise.create(create.getTableName(), create.getColumnPk(),
                    create.getColumnone(), create.getColumntwo());
        } catch (ServiseException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
        model.addAttribute("message", String.format(CREATE_TABLE.getMessage(), create.getTableName()));
        return "create";
    }

    @RequestMapping(value = "/drop", method = RequestMethod.GET)
    public String dropGet(HttpSession session){
        if (!isConnected(session)) {
            session.setAttribute("fromPage", "/drop");
            return "redirect:/connect";
        }
        return "drop";
    }

    @RequestMapping(value = "/drop", params = {"tableName"}, method = RequestMethod.POST)
    public String dropPost(@RequestParam(value = "tableName") String tableName,
                           Model model) {
        try {
            servise.drop(tableName);
        } catch (ServiseException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
        model.addAttribute("message", String.format(DROP_TABLE.getMessage(), tableName));
        return "drop";
    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public String clearGet(HttpSession session){
        if (!isConnected(session)) {
            session.setAttribute("fromPage", "/clear");
            return "redirect:/connect";
        }
        return "clear";
    }

    @RequestMapping(value = "/clear", params = {"tableName"}, method = RequestMethod.POST)
    public String clearPost(@RequestParam(value = "tableName") String tableName,
                            Model model) {
        try {
            servise.clear(tableName);
        } catch (ServiseException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
        model.addAttribute("message", String.format(CLEAR_TABLE.getMessage(), tableName));
        return "clear";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public String insertGet(Model model, HttpSession session){
        if (!isConnected(session)) {
            session.setAttribute("fromPage", "/insert");
            return "redirect:/connect";
        }
        model.addAttribute("insert", new Insert());
        return "insert";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insertPost(@ModelAttribute(value = "insert") Insert insert,
                             Model model) {
        try {
            servise.insert(insert.getTableName(), insert.getColumn(), insert.getValue(),
                    insert.getColumnsecond(), insert.getValuesecond());
        } catch (ServiseException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
        model.addAttribute("message", String.format(INSERT_TABLE_DATA.getMessage(), insert.getTableName()));
        return "insert";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteGet(HttpSession session){
        if (!isConnected(session)) {
            session.setAttribute("fromPage", "/delete");
            return "redirect:/connect";
        }
        return "delete";
    }

    @RequestMapping(value = "/delete", params = {"tableName", "column", "value"}, method = RequestMethod.POST)
    public String deletePost(@RequestParam(value = "tableName") String tableName,
                             @RequestParam(value = "column") String column,
                             @RequestParam(value = "value") String value,
                             Model model) {
        try {
            servise.delete(tableName, column, value);
        } catch (ServiseException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
        model.addAttribute("message", String.format(DELETE_TABLE_DATA.getMessage(), tableName, column, value));
        return "delete";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateGet(Model model, HttpSession session){
        if (!isConnected(session)) {
            session.setAttribute("fromPage", "/update");
            return "redirect:/connect";
        }
        model.addAttribute("update", new Update());
        return "update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updatePost(@ModelAttribute(value = "update") Update update,
                            Model model) {
        try {
            servise.update(update.getTableName(), update.getId(), update.getColumn(),
                    update.getValue());
        } catch (ServiseException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
        model.addAttribute("message", String.format(UPDATE_TABLE_DATA.getMessage(), update.getTableName(), update.getId(), update.getColumn()));
        return "update";
    }

    private boolean isConnected(HttpSession session){
        return Objects.nonNull(session.getAttribute("db_manager")) ? true : false;
    }
}
