package com.demo_api.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "privilege")
public class PrivilegeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String name;

    @ManyToMany (mappedBy = "privileges")
//    @JsonIgnore
    @JsonIgnoreProperties(value = "privileges")
    private List<RoleEntity> roles = new ArrayList<>();

    public PrivilegeEntity(){
        this.id = null;
        this.name = null;
    }

    public PrivilegeEntity(Long id, String name, List<RoleEntity> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
    }

    public void addRole(RoleEntity roleEntity){
        this.roles.add(roleEntity);
        roleEntity.getPrivileges().add(this);
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

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roleEntities) {
        this.roles = roles;
    }
}
