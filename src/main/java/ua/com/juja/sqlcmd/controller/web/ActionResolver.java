package ua.com.juja.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.juja.sqlcmd.service.Servise;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class ActionResolver {

    private List<Action> actions;

    public ActionResolver(Servise servise) {
        actions = new LinkedList<>();
        actions.addAll(Arrays.asList(
                new ConnectAction(servise),
                new MenuAction(servise),
                new HelpAction(servise),
                new FindAction(servise),
                new TablesAction(servise),
                new InsertAction(servise),
                new DeleteAction(servise),
                new CreateAction(servise),
                new DropAction(servise),
                new UpdateAction(servise),
                new ErrorAction(servise)));
    }

    public Action getAction(String url) {
        for (Action action : actions){
            if(action.canProcess(url)){
                return action;
            }
        }
        return new NullAction();
    }
}
