package ua.com.juja.sqlcmd.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConnectProperties {
    private String driver;
    private String host;

    public String getDriver(){
       return driver;
    }

    public String getHost(){
        return host;
    }

    public ConnectProperties readProperties(){

        Properties properties = new Properties();

        try{
            FileInputStream fileproperties = new FileInputStream("src/main/resources/connect.properties");
            properties.load(fileproperties);
        }catch (IOException e){
            e.printStackTrace();
        }

        driver = properties.getProperty("db.driver");
        host = properties.getProperty("db.host");
        return this;

    }
}
