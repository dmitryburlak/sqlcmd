package ua.com.juja.sqlcmd.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "database_connection", schema = "public")
public class DatabaseConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "db_name")
    private String dbName;

    public DatabaseConnection() {
        //do nothing
    }

    public DatabaseConnection(String name, String dbName) {
        this.name = name;
        this.dbName = dbName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDbName() {
        return dbName;
    }
}
