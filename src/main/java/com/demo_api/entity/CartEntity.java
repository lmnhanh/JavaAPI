package com.demo_api.entity;

import javax.persistence.*;

@Entity
@Table(name = "carts")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    private int status;

    @ManyToOne
    @JoinColumn(name = "detail_id")
    private ProductDetailEntity detail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
//    @JoinColumn(name = "order_id")
    private OrderEntity order;

    public CartEntity() {
        this.id = null;
        this.quantity = 0;
    }

    public CartEntity(int quantity) {
        this.id = null;
        this.quantity = quantity;
    }

    public CartEntity(UserEntity user, ProductDetailEntity detail, int quantity) {
        this.detail = detail;
        this.user = user;
        this.order = null;
        this.quantity = quantity;
        this.status = 0;
    }

    public CartEntity(Long id, int quantity, ProductDetailEntity detail, UserEntity user, OrderEntity order) {
        this.id = id;
        this.quantity = quantity;
        this.status = 0;
        this.detail = detail;
        this.user = user;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ProductDetailEntity getDetail() {
        return detail;
    }

    public void setDetail(ProductDetailEntity detail) {
        this.detail = detail;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
