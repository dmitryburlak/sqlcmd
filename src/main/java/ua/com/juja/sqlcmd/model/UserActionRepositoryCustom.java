package ua.com.juja.sqlcmd.model;

public interface UserActionRepositoryCustom {

    void createAction(String userName, String database, String action);
}
