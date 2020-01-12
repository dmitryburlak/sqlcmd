package ua.com.juja.sqlcmd.model.entity;

import jdk.nashorn.internal.objects.annotations.Property;

import javax.persistence.*;

@Entity
@Table(name = "user_actions", schema = "public")
public class UserAction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "db_name")
    private String dbName;

    @Column(name = "action")
    private String action;

    public UserAction() {
        //do nothing
    }

    public UserAction(String name, String dbName, String action) {
        this.name = name;
        this.dbName = dbName;
        this.action = action;
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

    public void setAction(String action) {
        this.action = action;
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

    public String getAction() {
        return action;
    }
}
