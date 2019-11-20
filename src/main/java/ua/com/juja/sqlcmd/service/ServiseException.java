package ua.com.juja.sqlcmd.service;

public class ServiseException extends RuntimeException {
    public ServiseException(String message, Exception e){
    super(message, e);
    }
}
