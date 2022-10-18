package com.demo_api.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_detail")
public class ProductDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String size;
    private String color;
    private String material;
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
    private List<OrderEntity> orders = new ArrayList<>();

    public ProductDetailEntity() {
        this.id = null;
        this.stock = -1;
        this.price = -1L;
        this.status = 1;
    }

    public ProductDetailEntity(String size) {
        this.size = size;
        this.color = null;
        this.material = null;
        this.stock = -1;
        this.price = -1L;
        this.status = 1;
    }

    public ProductDetailEntity(ProductEntity product) {
        this.size = null;
        this.color = null;
        this.material = null;
        this.stock = -1;
        this.price = -1L;
        this.status = 1;
        this.product = product;
    }

    public ProductDetailEntity(String size, String color) {
        this.size = size;
        this.color = color;
        this.material = null;
        this.stock = -1;
        this.price = -1L;
        this.status = 1;
    }

    public ProductDetailEntity(String size, String color, String material) {
        this.size = size;
        this.color = color;
        this.material = material;
        this.stock = -1;
        this.price = -1L;
        this.status = 1;
    }

    public ProductDetailEntity(int stock) {
        this.size = null;
        this.color = null;
        this.material = null;
        this.stock = stock;
        this.price = -1L;
        this.status = 1;
    }

    public ProductDetailEntity(Long price) {
        this.size = null;
        this.color = null;
        this.material = null;
        this.stock = -1;
        this.price = price;
        this.status = 1;
    }

    public ProductDetailEntity(String size, String color, String material, String image, int stock, Long price, ProductEntity product) {
        this.size = size;
        this.color = color;
        this.material = material;
        this.image = image;
        this.stock = stock;
        this.price = price;
        this.status = 1;
        this.product = product;
    }

    public ProductDetailEntity(Long id, String size, String color, String material, String image, int stock, Long price, ProductEntity product, List<OrderEntity> orders) {
        this.id = id;
        this.size = size;
        this.color = color;
        this.material = material;
        this.image = image;
        this.stock = stock;
        this.price = price;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
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

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
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
