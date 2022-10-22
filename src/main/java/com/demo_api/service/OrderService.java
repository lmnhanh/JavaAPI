package com.demo_api.service;

import com.demo_api.entity.OrderEntity;
import com.demo_api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {
    @Autowired
    OrderRepository repository;

    public OrderEntity get(Long id){
        return repository.findById(id).orElse(new OrderEntity());
    }

    public Page<OrderEntity> getAll(Long user, Date start, Date end, Pageable pageable){
        //Chưa hoàn thành
        return repository.findAll(pageable);
    };

    public Page<OrderEntity> getAll(Long user, Pageable pageable){
        return repository.findByUserId(user, pageable);
    };

    public OrderEntity save(OrderEntity order){
        return  repository.save(order);
    }
}
