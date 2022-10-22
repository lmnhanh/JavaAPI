package com.demo_api.model;

public class ProductDetail {
    private final Long id;
    private final String size;
    private final String gender;
    private final String origin;
    private final String image;
    private final int stock;
    private final Long price;
    private final int status;

    public ProductDetail(Long id, String size, String gender, String origin, String image, int stock, Long price, int status) {
        this.id = id;
        this.size = size;
        this.gender = gender;
        this.origin = origin;
        this.image = image;
        this.stock = stock;
        this.price = price;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getSize() {
        return size;
    }

    public int getStock() {
        return stock;
    }

    public String getImage() {
        return image;
    }

    public Long getPrice() {
        return price;
    }

    public int getStatus() {
        return status;
    }

    public String getGender() {
        return gender;
    }

    public String getOrigin() {
        return origin;
    }
}