package com.demo_api.model;

public class User{
    private final Long id;
    private final String name;
    private final String password;
    private final int status;

    public User(Long id, String name, String password, int status) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getStatus() {
        return status;
    }
}