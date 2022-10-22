package com.demo_api.assembler;

import com.demo_api.controller.CartsController;
import com.demo_api.controller.OrdersController;
import com.demo_api.controller.ProductDetailsController;
import com.demo_api.controller.UsersController;
import com.demo_api.entity.CartEntity;
import com.demo_api.model.Cart;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CartModelAssembler implements RepresentationModelAssembler<CartEntity, EntityModel<Cart>> {
    @Override
    public EntityModel<Cart> toModel(CartEntity entity) {
        if(entity.getId() == null){
            return EntityModel.of(new Cart(entity.getId(), entity.getQuantity(), 0L, 0, "ERROR"));
        }
        if(entity.getOrder() == null)
            return EntityModel.of(new Cart(entity.getId(), entity.getQuantity(), entity.getDetail().getPrice(), entity.getDetail().getStock(), "OK"),
                    linkTo(methodOn(CartsController.class).getOne(entity.getId())).withSelfRel(),
                    linkTo(methodOn(UsersController.class).getOne(entity.getUser().getId())).withRel("user"),
                    linkTo(methodOn(ProductDetailsController.class).getOne(entity.getDetail().getId())).withRel("detail")
            );
        return EntityModel.of(new Cart(entity.getId(), entity.getQuantity(), entity.getDetail().getPrice(), entity.getDetail().getStock(), "OK"),
                linkTo(methodOn(CartsController.class).getOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(UsersController.class).getOne(entity.getUser().getId())).withRel("user"),
                linkTo(methodOn(ProductDetailsController.class).getOne(entity.getDetail().getId())).withRel("detail"),
                linkTo(methodOn(OrdersController.class).getOne(entity.getOrder().getId())).withRel("order")
        );
    }
}
