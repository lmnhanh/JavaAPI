package com.demo_api.model;

public class Cart {
    private final Long id;
    private final int quantity;
    private final Long price;
    private final int stock;

    private final String message;

    public Cart(Long id, int quantity, Long price, int stock, String message) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.stock = stock;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getStock() {
        return stock;
    }

    public String getMessage() {
        return message;
    }

    public Long getPrice() {
        return price;
    }
}