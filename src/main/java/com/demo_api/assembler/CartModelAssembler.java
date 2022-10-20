package com.demo_api.assembler;

import com.demo_api.controller.CartsController;
import com.demo_api.entity.CartEntity;
import com.demo_api.model.Cart;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CartModelAssembler implements RepresentationModelAssembler<CartEntity, EntityModel<Cart>> {
    @Override
    public EntityModel<Cart> toModel(CartEntity entity) {
        return EntityModel.of(new Cart(entity.getId(), entity.getQuantity(), entity.getStatus(), null),
                linkTo(methodOn(CartsController.class).getOne(entity.getId())).withSelfRel());
    }
}
