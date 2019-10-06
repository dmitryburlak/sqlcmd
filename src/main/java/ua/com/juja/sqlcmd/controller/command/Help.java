package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.view.MessageList;
import ua.com.juja.sqlcmd.view.View;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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
        List<String> readHelp = new ArrayList<String>();
        try(BufferedReader fileRead = new BufferedReader(new FileReader(helpfile))){
            String currenLine;
            while(fileRead.ready() && (currenLine = fileRead.readLine()) != null){
                    readHelp.add(currenLine);
            }

            Iterator iterator = readHelp.iterator();
            while(iterator.hasNext()){
              view.write(iterator.next().toString());
            }

        }catch(IOException e){
            throw new RuntimeException(MessageList.HELPTXT_NOT_FOUND.getMessage());

        }

    }
}
