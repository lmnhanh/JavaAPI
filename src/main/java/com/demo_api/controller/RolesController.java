package com.demo_api.controller;

import com.demo_api.assembler.PrivilegeModelAssembler;
import com.demo_api.assembler.RoleModelAssembler;
import com.demo_api.assembler.UserModelAssembler;
import com.demo_api.model.Privilege;
import com.demo_api.model.Role;
import com.demo_api.entity.RoleEntity;
import com.demo_api.entity.UserEntity;
import com.demo_api.model.User;
import com.demo_api.repository.RoleRepository;
import com.demo_api.repository.UserRepository;
import com.demo_api.service.PrivilegeService;
import com.demo_api.service.RoleService;
import com.demo_api.service.UserService;
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
    @Autowired
    RoleModelAssembler assembler;
    @Autowired
    PrivilegeModelAssembler privilegeModelAssembler;
    @Autowired
    PrivilegeService privilegeService;
    @Autowired
    PagedResourcesAssembler<RoleEntity> pagedResourcesAssembler;
    @Autowired
    RoleService roleService;
    @Autowired
    UserModelAssembler userAssembler;
    @Autowired
    UserService userService;

    //Get one
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Role>> getOne(@PathVariable Long id) {
        RoleEntity roleEntity = roleService.get(id);
        if(roleEntity.getId() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(assembler.toModel(roleEntity), HttpStatus.OK);
    }

    //Get all with paging
    //Test with: http://localhost:8060/roles?page=0&size=2&sort=id,asc
    //Test with: http://localhost:8060/roles?status=1
    //Test with: http://localhost:8060/roles?privilege=1
    @GetMapping()
    public ResponseEntity<PagedModel<EntityModel<Role>>> getAll(@RequestParam(defaultValue = "-1") int status,
                                                                @RequestParam(defaultValue = "-1") Long privilege,
                                                                Pageable pageable) {
        Page<RoleEntity> roles = roleService.getAll(status, privilege, pageable);
        PagedModel<EntityModel<Role>> page = pagedResourcesAssembler.toModel(roles, assembler);
        page.add(linkTo(methodOn(RolesController.class).getAll(status, privilege, pageable)).withSelfRel());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    //Add new
    //Test with: http://localhost:8060/roles
    //In request body:
    //To add role with privileges: {"name": "Sales","privileges": [{"id": 1}]}
    //To add role with no privileges: {"name": "Sales"}
    @PostMapping()
    public ResponseEntity<EntityModel<Role>> addRole(@RequestBody RoleEntity newRole){
        try{
            return new ResponseEntity<>(
                    assembler.toModel(roleService.save(newRole)),
                    HttpStatus.CREATED
            );
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Update
    //Test with: http://localhost:8060/roles/1/update
    //In request body:
    //To set role with no privileges: {"name": "Sales Manager"}
    //To set role with privileges: {"name": "Sales Manager", "privileges":[{"id":1}]}
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<EntityModel<Role>> updateRole(@RequestBody RoleEntity newRole, @PathVariable Long id) {
        RoleEntity role = roleService.get(id);
        if(role.getId() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            roleService.update(role, newRole);
            return new ResponseEntity<>(assembler.toModel(role), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Delete
    //Test with: http://localhost:8060/roles/1/delete
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        if(roleService.get(id).getId() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
