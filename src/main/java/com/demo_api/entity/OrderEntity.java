package com.demo_api.entity;

import javax.persistence.*;

@Entity
@Table(name = "order")
public class OrderEntity {
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
    @JoinColumn(name = "receipt_id")
    private ReceiptEntity receipt;

    public OrderEntity() {
        this.id = null;
        this.quantity = 0;
    }

    public OrderEntity(int quantity, int status) {
        this.quantity = quantity;
        this.status = status;
    }

    public OrderEntity(Long id, int quantity, int status, ProductDetailEntity detail, UserEntity user, ReceiptEntity receipt) {
        this.id = id;
        this.quantity = quantity;
        this.status = status;
        this.detail = detail;
        this.user = user;
        this.receipt = receipt;
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

    public ReceiptEntity getReceipt() {
        return receipt;
    }

    public void setReceipt(ReceiptEntity receipt) {
        this.receipt = receipt;
    }
}
