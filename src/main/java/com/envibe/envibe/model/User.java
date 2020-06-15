package com.envibe.envibe.model;

import com.envibe.envibe.model.validation.constraints.ValidRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class User implements Serializable {
    //@NotNull
    //@NotEmpty
    private String username;

    //@NotNull
    //@NotEmpty
    private String password;

    //@NotNull
    //@NotEmpty
    //@ValidRole
    private String role;

    //@NotNull
    //@NotEmpty
    //@Email
    private String email;

    public User() {

    }

    public User(String username, String password, String role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    // Just a bunch of getters and setters. Nothing to see here.
    public String getRole()
    {
        return this.role;
    }

    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
