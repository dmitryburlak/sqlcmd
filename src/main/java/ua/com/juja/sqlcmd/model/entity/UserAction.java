package ua.com.juja.sqlcmd.model.entity;

import jdk.nashorn.internal.objects.annotations.Property;

import javax.persistence.*;

@Entity
@Table(name = "user_actions", schema = "public")
public class UserAction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "action")
    private String action;

    @JoinColumn(name = "database_connection_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DatabaseConnection connection;

    public UserAction() {
        //do nothing
    }

    public UserAction(DatabaseConnection connection, String action) {
        this.connection = connection;
        this.action = action;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setConnection(DatabaseConnection connection) {
        this.connection = connection;
    }

    public int getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public DatabaseConnection getConnection() {
        return connection;
    }
}
