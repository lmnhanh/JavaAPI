package com.demo_api.model;

import com.demo_api.entity.UserEntity;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link UserEntity} entity
 */
public class User implements Serializable {
    private final Long id;
    private final String name;
    private final String password;

    public User(){
        this.id = null;
        this.name = null;
        this.password = null;
    }

    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User entity = (User) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.password, entity.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "password = " + password + ")";
    }
}