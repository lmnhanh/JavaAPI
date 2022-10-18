package com.demo_api.model;

public class Order {
    private final Long id;
    private final int quantity;
    private final int status;

    public Order(Long id, int quantity, int status) {
        this.id = id;
        this.quantity = quantity;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getStatus() {
        return status;
    }
}