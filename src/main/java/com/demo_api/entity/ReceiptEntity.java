package com.demo_api.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "receipt")
public class ReceiptEntity {
    @Id
    private Long id;

    @ManyToOne()
    private UserEntity user;

    @OneToMany(mappedBy = "receipt")
    private List<OrderEntity> orders = new ArrayList<>();

    public ReceiptEntity() {
        this.id = null;
    }

    public ReceiptEntity(Long id, UserEntity user, List<OrderEntity> orders) {
        this.id = id;
        this.user = user;
        this.orders = orders;
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

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }
}
