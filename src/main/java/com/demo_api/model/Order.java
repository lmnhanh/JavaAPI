package com.demo_api.model;

import java.util.Date;

public class Order {
    private final Long id;
    private final Date created_at;
    private final Long total;

    public Order(Long id,Long total, Date created_at) {
        this.id = id;
        this.total = total;
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Long getTotal() {
        return total;
    }
}