package com.demo_api.Model;

import org.springframework.hateoas.RepresentationModel;

public class Role extends RepresentationModel<Role> {
    private final Long id;
    private final String name;

    public Role(){
        this.id = null;
        this.name = null;
    }
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}