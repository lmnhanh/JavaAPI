package com.demo_api.assembler;

import com.demo_api.controller.PrivilegesController;
import com.demo_api.controller.RolesController;
import com.demo_api.controller.UsersController;
import com.demo_api.entity.RoleEntity;
import com.demo_api.model.Role;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoleModelAssembler implements RepresentationModelAssembler<RoleEntity, EntityModel<Role>> {
    @Override
    public EntityModel<Role> toModel(RoleEntity entity) {
        if(entity.getId() == null)
            return EntityModel.of(new Role(entity.getId(), entity.getName(), entity.getStatus()));
        return EntityModel.of(new Role(entity.getId(), entity.getName(), entity.getStatus()),
                linkTo(methodOn(RolesController.class).getOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(UsersController.class).getUsersByRolesId(entity.getId())).withRel("users"),
                linkTo(methodOn(PrivilegesController.class).getByRoleId(entity.getId())).withRel("privileges"));
    }
}
