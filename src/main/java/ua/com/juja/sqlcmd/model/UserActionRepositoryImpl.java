package ua.com.juja.sqlcmd.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.juja.sqlcmd.model.entity.DatabaseConnection;
import ua.com.juja.sqlcmd.model.entity.UserAction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;

public class UserActionRepositoryImpl implements UserActionRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseConnectionRepository databaseConnection;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createAction(String userName, String database, String action) {
        DatabaseConnection dc = databaseConnection.findByNameAndDbName(userName, database);
        if(Objects.isNull(dc)){
            dc = databaseConnection.save(new DatabaseConnection(userName, database));
        }
        entityManager.persist(new UserAction(dc, action));
    }
}
