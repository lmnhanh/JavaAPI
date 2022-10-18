package com.demo_api.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

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
    @Column(nullable = false)
    private int status;

    @ManyToOne()
    @Nullable
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @OneToMany(mappedBy = "user")
    private List<ReceiptEntity> receipts;

    @OneToMany(mappedBy = "user")
    private List<OrderEntity> orders;

    public UserEntity(){
        this.id = null;
        this.status = 1;
    }

    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
        this.status = 1;
        this.role = null;
    }

    public UserEntity(String name, String password, int status) {
        this.name = name;
        this.password = password;
        this.status = status;
        this.role = null;
    }

    public UserEntity(String name, String password, RoleEntity role, int status) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ReceiptEntity> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<ReceiptEntity> receipts) {
        this.receipts = receipts;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }
}
