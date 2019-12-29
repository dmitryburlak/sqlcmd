package ua.com.juja.sqlcmd.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserActionsDaoImpl implements UserActionsDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier(value = "logDataSource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void log(String userName, String dbName, String action) {
        String sqlquery = String.format("INSERT INTO public.user_actions (user_name, db_name, action)" +
                "VALUES ('%s', '%s', '%s')", userName, dbName, action);
        jdbcTemplate.update(sqlquery);
    }

    @Override
    public List<UserAction> getAllFor(String userName) {
        return (jdbcTemplate.query("SELECT * FROM public.user_actions WHERE user_name = ?",
                new Object[] { userName }, (resultSet, rowNum) -> {
                    UserAction userAction = new UserAction();
                    userAction.setId(resultSet.getInt("id"));
                    userAction.setName(resultSet.getString("user_name"));
                    userAction.setDbName(resultSet.getString("db_name"));
                    userAction.setAction(resultSet.getString("action"));

                    return userAction;
                }));
    }
}
