package com.demo_api.assembler;

import com.demo_api.controller.PrivilegesController;
import com.demo_api.controller.RolesController;
import com.demo_api.entity.PrivilegeEntity;
import com.demo_api.model.Privilege;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PrivilegeModelAssembler implements RepresentationModelAssembler<PrivilegeEntity, EntityModel<Privilege>> {
    @Override
    public EntityModel<Privilege> toModel(PrivilegeEntity entity) {
        return EntityModel.of(new Privilege(entity.getId(), entity.getName()),
                linkTo(methodOn(PrivilegesController.class).getOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(RolesController.class).getAll(-1, entity.getId(), Pageable.ofSize(5))).withRel("roles")
        );
    }
}
