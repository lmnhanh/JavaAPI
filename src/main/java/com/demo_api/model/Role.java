package com.demo_api.model;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class Role {
    private final Long id;
    private final String name;
    private final int status;
    public Role(Long id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = 1;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }
}