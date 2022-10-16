package com.demo_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;

    @ManyToOne()
    @JoinColumn(name = "role_id")
//    @JsonIgnore
    @JsonIgnoreProperties(value = "users")
    private RoleEntity role;

    public UserEntity(){
        this.id = null;
        this.name = null;
        this.password = null;
    }

    public UserEntity(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public UserEntity(String name, String password, RoleEntity role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
        this.role = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }
}
