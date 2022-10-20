package com.demo_api.model;

public class Cart {
    private final Long id;
    private final int quantity;
    private final int status;

    private final String message;

    public Cart(Long id, int quantity, int status, String message) {
        this.id = id;
        this.quantity = quantity;
        this.status = status;
        this.message = message;
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