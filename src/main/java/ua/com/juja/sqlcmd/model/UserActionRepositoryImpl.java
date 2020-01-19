package ua.com.juja.sqlcmd.model;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.juja.sqlcmd.model.entity.DatabaseConnection;
import ua.com.juja.sqlcmd.model.entity.UserAction;
import java.util.Objects;

public class UserActionRepositoryImpl implements UserActionRepositoryCustom {

    @Autowired
    private UserActionRepository userActions;

    @Autowired
    private DatabaseConnectionRepository databaseConnection;

    @Override
    public void createAction(String userName, String database, String action) {
        DatabaseConnection dc = databaseConnection.findByNameAndDbName(userName, database);
        if(Objects.isNull(dc)){
            dc = databaseConnection.save(new DatabaseConnection(userName, database));
        }
        userActions.save(new UserAction(dc, action));
    }
}
