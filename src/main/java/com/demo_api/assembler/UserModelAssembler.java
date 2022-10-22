package com.demo_api.assembler;

import com.demo_api.controller.OrdersController;
import com.demo_api.controller.RolesController;
import com.demo_api.controller.UsersController;
import com.demo_api.entity.UserEntity;

import com.demo_api.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserEntity, EntityModel<User>> {
    @Override
    public EntityModel<User> toModel(UserEntity entity) {
        if(entity.getRole() == null){
            return EntityModel.of(new User(entity.getId(), entity.getUsername(), entity.getEmail()),
                    linkTo(methodOn(UsersController.class).getOne(entity.getId())).withSelfRel()
            );
        }
        return EntityModel.of(new User(entity.getId(), entity.getUsername(), entity.getEmail()),
                linkTo(methodOn(UsersController.class).getOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(RolesController.class).getOne(entity.getRole().getId())).withRel("role"),
                linkTo(methodOn(OrdersController.class).getAll(entity.getId(), Pageable.ofSize(5))).withRel("orders")
        );
    }
}
