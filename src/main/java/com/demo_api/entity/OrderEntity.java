package com.demo_api.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Date created_at = new Date();

    @ManyToOne()
    private UserEntity user;

    @OneToMany(mappedBy = "order")
    private List<CartEntity> carts = new ArrayList<>();

    public OrderEntity() {
        this.id = null;
    }

    public OrderEntity(UserEntity user, List<CartEntity> carts) {
        this.user = user;
        this.carts = carts;
    }

    public OrderEntity(Long id, Date created_at, UserEntity user,List<CartEntity> carts) {
        this.id = id;
        this.created_at = created_at;
        this.user = user;
        this.carts = carts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<CartEntity> getCarts() {
        return carts;
    }

    public void setCarts(List<CartEntity> carts) {
        this.carts = carts;
    }

    public Date getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
