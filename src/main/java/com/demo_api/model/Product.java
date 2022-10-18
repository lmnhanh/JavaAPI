package com.demo_api.model;

import java.util.ArrayList;
import java.util.List;

public class Product{
    private final Long id;
    private final String name;
    private final int status;

    public Product(Long id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStatus(){
        return status;
    }
}