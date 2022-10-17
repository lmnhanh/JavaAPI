package com.demo_api.controller;

import com.demo_api.model.Privilege;
import com.demo_api.entity.PrivilegeEntity;
import com.demo_api.repository.RoleRepository;
import com.demo_api.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/privileges")
public class PrivilegesController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PrivilegeService privilegeService;

    public PrivilegesController(RoleRepository roleRepository, PrivilegeService privilegeService) {
        this.roleRepository = roleRepository;
        this.privilegeService = privilegeService;
    }

    //Get one
    @GetMapping("/{id}")
    public EntityModel<Privilege> getOne(@PathVariable Long id) {
        PrivilegeEntity privilege = privilegeService.get(id);
        EntityModel<Privilege> result = EntityModel.of(new Privilege(privilege.getId(), privilege.getName()));
        if (privilege.getId() == null){
            return result;
        }
        result.add(linkTo(methodOn(PrivilegesController.class).getOne(id)).withSelfRel());
        result.add(linkTo(methodOn(RolesController.class).getByPrivilegeId(id)).withRel("roles"));
        return result;
    }

    //Get all
    @GetMapping()
    public CollectionModel<EntityModel<Privilege>> getAll(){
        List<EntityModel<Privilege>> privileges = privilegeService.getAll().stream()
                .map(privilege ->
                        EntityModel.of(new Privilege(privilege.getId(), privilege.getName()),
                                linkTo(methodOn(PrivilegesController.class).getOne(privilege.getId())).withSelfRel(),
                                linkTo(methodOn(RolesController.class).getByPrivilegeId(privilege.getId())).withRel("roles"))
                ).collect(Collectors.toList());
        return CollectionModel.of(privileges, linkTo(methodOn(PrivilegesController.class).getAll()).withSelfRel());
    }

    //Get by role id
    @GetMapping(value = "/by_role/{id}")
    public CollectionModel<EntityModel<Privilege>> getByRoleId(@PathVariable Long id){
        List<EntityModel<Privilege>> privileges = privilegeService.getByRoleId(id).stream()
                .map(privilege ->
                        EntityModel.of(new Privilege(privilege.getId(), privilege.getName()),
                                linkTo(methodOn(PrivilegesController.class).getOne(privilege.getId())).withSelfRel(),
                                linkTo(methodOn(RolesController.class).getByPrivilegeId(privilege.getId())).withRel("roles"))
                ).collect(Collectors.toList());
        return CollectionModel.of(privileges, linkTo(methodOn(PrivilegesController.class).getByRoleId(id)).withSelfRel());
    }
}
