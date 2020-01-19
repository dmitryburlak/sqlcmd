package ua.com.juja.sqlcmd.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.com.juja.sqlcmd.model.entity.DatabaseConnection;
import ua.com.juja.sqlcmd.model.entity.UserAction;

import java.util.List;

public interface DatabaseConnectionRepository extends CrudRepository<DatabaseConnection, Integer> {

    DatabaseConnection findByNameAndDbName(String name, String dbName);
}
