package ua.com.juja.sqlcmd.controller.command;


import ua.com.juja.sqlcmd.view.View;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static ua.com.juja.sqlcmd.view.MessageList.*;


public class Help implements Command {
    private View view;
    private String helpfile = "src/main/resources/help.txt";

    public Help(View view){
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
        try (Stream<String> stream = Files.lines(Paths.get(helpfile))) {
            List<String> readHelp = stream.collect(Collectors.toList());
            readHelp.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(HELPTXT_NOT_FOUND.getMessage());
        }
    }
}
