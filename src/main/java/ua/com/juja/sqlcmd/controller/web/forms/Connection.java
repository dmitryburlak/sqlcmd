package ua.com.juja.sqlcmd.controller.web.forms;

public class Connection {
    private String database;
    private String userName;
    private String password;
    private String fromPage;

    public Connection() {
        //do nothing
    }

    public Connection(String fromPage) {
        this.fromPage = fromPage;
    }

    public String getDatabase() {
        return database;
    }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public String getFromPage() {
        return fromPage;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFromPage(String fromPage) {
        this.fromPage = fromPage;
    }
}

