package com.demo_api.controller;

import com.demo_api.model.Role;
import com.demo_api.model.RoleEntity;
import com.demo_api.model.UserEntity;
import com.demo_api.repository.RoleRepository;
import com.demo_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/roles")
public class RolesController {
    @Autowired RoleRepository roleRepository;
    @Autowired UserRepository userRepository;

    public RolesController(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }
    //Get one
    @GetMapping("/{id}")
    public EntityModel<Role> getOne(@PathVariable Long id) {
        RoleEntity roleEntity = roleRepository.findById(id).orElse(new RoleEntity());
        Role role = new Role(roleEntity.getId(), roleEntity.getName());
        return EntityModel.of(role,
                linkTo(methodOn(RolesController.class).getOne(id)).withSelfRel(),
                linkTo(methodOn(UsersController.class).getUsersByRolesId(id)).withRel("users"));
    }

    //Get all
    @GetMapping()
    public CollectionModel<EntityModel<Role>> getAll(){
        List<EntityModel<Role>> roles = roleRepository.findAll().stream()
                .map(role -> EntityModel.of(new Role(role.getId(), role.getName()),
                        linkTo(methodOn(RolesController.class).getOne(role.getId())).withSelfRel(),
                        linkTo(methodOn(UsersController.class).getUsersByRolesId(role.getId())).withRel("users"))
                ).collect(Collectors.toList());
        return CollectionModel.of(roles, linkTo(methodOn(RolesController.class).getAll()).withSelfRel());
    }

    //Get by privilege id
    @GetMapping("/by_pri/{id}")
    public CollectionModel<EntityModel<Role>> getByPrivilegeId(@PathVariable Long id){
        List<EntityModel<Role>> roles = roleRepository.findRolesByPrivilegeId(id).stream()
                .map(role -> EntityModel.of(new Role(role.getId(), role.getName()),
                        linkTo(methodOn(RolesController.class).getOne(role.getId())).withSelfRel(),
                        linkTo(methodOn(UsersController.class).getUsersByRolesId(role.getId())).withRel("users"))
                ).collect(Collectors.toList());
        return CollectionModel.of(roles, linkTo(methodOn(RolesController.class).getByPrivilegeId(id)).withSelfRel());
    }

    //Add new
    @PostMapping()
    public EntityModel<Role> addRole(@RequestBody RoleEntity newRoleEntity){
        roleRepository.save(newRoleEntity);
        return EntityModel.of(new Role(newRoleEntity.getId(), newRoleEntity.getName()),
                linkTo(methodOn(RolesController.class).getOne(newRoleEntity.getId())).withSelfRel(),
                linkTo(methodOn(UsersController.class).getUsersByRolesId(newRoleEntity.getId())).withRel("users"));
    }

    //Update
    @PutMapping(value = "/update/{id}")
    public EntityModel<Role> updateRole(@RequestBody RoleEntity newRoleEntity, @PathVariable Long id) {
        RoleEntity roleEntity = roleRepository.findById(id).map(role -> {
            role.setName(newRoleEntity.getName());
            role.setPrivileges(newRoleEntity.getPrivileges());
            role.setUsers(newRoleEntity.getUsers());
            for(UserEntity user: newRoleEntity.getUsers()){
                user.setRole(role);
                userRepository.save(user);
            }
            return roleRepository.save(role);
        }).orElseGet(() -> {
            newRoleEntity.setId(id);
            return roleRepository.save(newRoleEntity);
        });

        return EntityModel.of(new Role(roleEntity.getId(), roleEntity.getName()),
                linkTo(methodOn(RolesController.class).getOne(id)).withSelfRel(),
                linkTo(methodOn(UsersController.class).getUsersByRolesId(id)).withRel("users"));
    }

//    //Delete
//    @DeleteMapping("/delete/{id}")
//    public void deleteEmployee(@PathVariable Long id) {
//        roleRepository.deleteById(id);
//        return;
//    }
}
