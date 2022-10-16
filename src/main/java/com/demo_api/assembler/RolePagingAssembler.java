package com.demo_api.assembler;

import com.demo_api.controller.RolesController;
import com.demo_api.entity.RoleEntity;
import com.demo_api.model.Role;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RolePagingAssembler extends RepresentationModelAssemblerSupport<RoleEntity, Role> {

    public RolePagingAssembler() {
        super(RolesController.class, Role.class);
    }

    public RolePagingAssembler(Class<?> controllerClass, Class<Role> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public Role toModel(RoleEntity entity) {
        return new Role(entity.getId(), entity.getName());
    }
}
