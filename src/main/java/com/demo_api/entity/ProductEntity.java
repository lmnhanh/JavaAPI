package com.demo_api.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int status;

    @OneToMany(mappedBy = "product")
    private List<ProductDetailEntity> details = new ArrayList<>();

    public ProductEntity() {
        this.id = null;
        this.status = 1;
    }

    public ProductEntity(String name) {
        this.name = name;
        this.status = 1;
    }

    public ProductEntity(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public ProductEntity(Long id, String name, int status, List<ProductDetailEntity> details) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ProductDetailEntity> getDetails() {
        return details;
    }

    public void setDetails(List<ProductDetailEntity> details) {
        this.details = details;
    }
}
