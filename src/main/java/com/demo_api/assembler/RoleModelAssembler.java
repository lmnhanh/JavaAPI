package com.demo_api.assembler;

import com.demo_api.controller.PrivilegesController;
import com.demo_api.controller.RolesController;
import com.demo_api.controller.UsersController;
import com.demo_api.entity.RoleEntity;
import com.demo_api.model.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoleModelAssembler implements RepresentationModelAssembler<RoleEntity, EntityModel<Role>> {
    @Override
    public EntityModel<Role> toModel(RoleEntity entity) {
        return EntityModel.of(new Role(entity.getId(), entity.getName(), entity.getStatus()),
                linkTo(methodOn(RolesController.class).getOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(UsersController.class).getAll(entity.getId(),-1, Pageable.ofSize(5))).withRel("users"),
                linkTo(methodOn(PrivilegesController.class).getAll(entity.getId(), Pageable.ofSize(5))).withRel("privileges"));
    }
}
