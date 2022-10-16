package com.demo_api.controller;

import com.demo_api.assembler.RoleModelAssembler;
import com.demo_api.assembler.RolePagingAssembler;
import com.demo_api.Model.Role;
import com.demo_api.Entity.RoleEntity;
import com.demo_api.Entity.UserEntity;
import com.demo_api.repository.RoleRepository;
import com.demo_api.repository.UserRepository;
import com.demo_api.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/roles")
public class RolesController {
    @Autowired RoleModelAssembler assembler;
    @Autowired RolePagingAssembler pagingAssembler;
    @Autowired RoleRepository roleRepository;
    @Autowired PagedResourcesAssembler<RoleEntity> pagedResourcesAssembler;
    @Autowired UserRepository userRepository;
    @Autowired RoleService roleService;

    //Get one
    @GetMapping("/{id}")
    public  EntityModel<Role> getOne(@PathVariable Long id) {
        RoleEntity roleEntity = roleRepository.findById(id).orElse(new RoleEntity());
        return assembler.toModel(roleEntity);
    }

    //Get all
    @GetMapping()
    public CollectionModel<EntityModel<Role>> getAll(){
        List<EntityModel<Role>> roles = roleRepository.findAll().stream()
                .map(role -> assembler.toModel(role))
                .collect(Collectors.toList());
        return CollectionModel.of(roles, linkTo(methodOn(RolesController.class).getAll()).withSelfRel());
    }

    @GetMapping(value = "/paging")
    public ResponseEntity<PagedModel<EntityModel<Role>>> getAllAlbums(Pageable pageable)
    {
        Page<RoleEntity> roles = roleRepository.findAll(pageable);
        PagedModel<EntityModel<Role>> page = pagedResourcesAssembler.toModel(roles, assembler);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    //Get by privilege id
    @GetMapping("/by_pri/{id}")
    public CollectionModel<EntityModel<Role>> getByPrivilegeId(@PathVariable Long id){
        List<EntityModel<Role>> roles = roleRepository.findRolesByPrivilegeId(id).stream()
                .map(role -> assembler.toModel(role))
                .collect(Collectors.toList());
        return CollectionModel.of(roles, linkTo(methodOn(RolesController.class).getByPrivilegeId(id)).withSelfRel());
    }

    //Add new
    @PostMapping()
    public EntityModel<Role> addRole(@RequestBody RoleEntity newRoleEntity){
        return assembler.toModel(roleRepository.save(newRoleEntity));
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

        return assembler.toModel(roleEntity);
    }

    //Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        roleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
