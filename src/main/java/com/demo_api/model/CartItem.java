package com.demo_api.model;

import javax.persistence.Column;

public class CartItem {
    private  final Long id; //Order id
    private final String name;
    private final String size;
    private final String color;
    private final String material;
    private final int stock;
    private final int quantity;

    public CartItem() {
        this.id = null;
        this.name = null;
        this.size = null;
        this.color = null;
        this.quantity = 0;
        this.material = null;
        this.stock = 1;
    }

    public CartItem(Long id, String name, String size, String color, String material, int stock, int quantity) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.color = color;
        this.material = material;
        this.stock = stock;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getMaterial() {
        return material;
    }

    public int getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }
}
