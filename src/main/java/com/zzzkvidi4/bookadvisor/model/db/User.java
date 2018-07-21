package com.zzzkvidi4.bookadvisor.model.db;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonProperty("books")
    private Set<BookUser> bookUser = new HashSet<>();

    public User(){}

    public User(int id, String username, String password){
        this.userId = id;
        this.username = username;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<BookUser> getBookUser() {
        return bookUser;
    }

    @Override
    public String toString() {
        return "db.User[id: " + userId + ", username: " + username + "]";
    }
}
