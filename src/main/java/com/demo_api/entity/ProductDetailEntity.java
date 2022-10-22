package com.demo_api.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_details")
public class ProductDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String size;
    private String origin;
    private String gender;
    private String image;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private int status;

    @ManyToOne()
    private ProductEntity product;

    @OneToMany
    @JoinColumn(name = "detail_id")
    private List<CartEntity> orders = new ArrayList<>();

    public ProductDetailEntity() {
        this.id = null;
        this.stock = 0;
        this.price = 0L;
        this.status = 1;
    }

    public ProductDetailEntity(String size, String origin, String gender, String image, int stock, Long price) {
        this.id = null;
        this.size = size;
        this.origin = origin;
        this.gender = gender;
        this.image = image;
        this.stock = stock;
        this.price = price;
    }

    public ProductDetailEntity(Long id, String size, String origin, String gender, String image, int stock, Long price, int status, ProductEntity product, List<CartEntity> orders) {
        this.id = id;
        this.size = size;
        this.origin = origin;
        this.gender = gender;
        this.image = image;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.product = product;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public List<CartEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<CartEntity> orders) {
        this.orders = orders;
    }

    public Long getPrice() {
        return price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
