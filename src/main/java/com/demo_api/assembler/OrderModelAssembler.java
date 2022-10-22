package com.demo_api.assembler;

import com.demo_api.controller.CartsController;
import com.demo_api.controller.OrdersController;
import com.demo_api.entity.CartEntity;
import com.demo_api.entity.OrderEntity;
import com.demo_api.model.Order;
import com.demo_api.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<OrderEntity, EntityModel<Order>> {
    @Autowired
    ProductDetailService detailService;
    @Override
    public EntityModel<Order> toModel(OrderEntity entity) {
        if(entity.getId() == null) {
            return EntityModel.of(new Order(entity.getId(), 0L, entity.getCreated_at()));
        }
        Long total = 0L;
        for(CartEntity cart: entity.getCarts()){
            total+= cart.getQuantity() * cart.getDetail().getPrice();
        }
        return EntityModel.of(new Order(entity.getId(), total,entity.getCreated_at()),
                linkTo(methodOn(OrdersController.class).getOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(CartsController.class).getAll(entity.getUser().getId(), entity.getId())).withRel("carts")
        );
    }
}
