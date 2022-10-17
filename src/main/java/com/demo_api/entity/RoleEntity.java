package com.demo_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(nullable = false)
    private  String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE)
    private List<UserEntity> users = new ArrayList<>();

    @ManyToMany()
    private List<PrivilegeEntity> privileges = new ArrayList<>();

    public RoleEntity() {
    }

    public RoleEntity(String name, List<PrivilegeEntity> privileges) {
        this.name = name;
        this.privileges = privileges;
    }

    public RoleEntity(String name) {
        this.id = id;
    }

    public RoleEntity(Long id, String name, List<PrivilegeEntity> privileges) {
        this.id = id;
        this.name = name;
        this.privileges = privileges;
    }

    public RoleEntity(Long id, String name, List<UserEntity> userEntities, List<PrivilegeEntity> privileges) {
        this.id = id;
        this.name = name;
        this.users = userEntities;
        this.privileges = privileges;
    }

    public void addUser(UserEntity userEntity){
        this.users.add(userEntity);
        userEntity.setRole(this);
    }

    public  void addPrivilege(PrivilegeEntity privilege){
        this.privileges.add(privilege);
        privilege.getRoles().add(this);
    }

    public List<PrivilegeEntity> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<PrivilegeEntity> privileges) {
        this.privileges = privileges;
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

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
