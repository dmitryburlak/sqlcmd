package ua.com.juja.sqlcmd.service;

public class ServiceComponentException extends RuntimeException {
    public ServiceComponentException(String message, Exception e){
    super(message, e);
    }
}
