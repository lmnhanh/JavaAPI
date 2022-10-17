package com.demo_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.bytebuddy.utility.nullability.NeverNull;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullFields;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;

    @ManyToOne()
    @Nullable
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    public UserEntity(){
        this.id = null;
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
