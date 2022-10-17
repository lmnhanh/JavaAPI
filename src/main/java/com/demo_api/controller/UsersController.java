package com.demo_api.controller;

import com.demo_api.assembler.UserModelAssembler;
import com.demo_api.entity.RoleEntity;
import com.demo_api.model.Role;
import com.demo_api.model.User;
import com.demo_api.entity.UserEntity;
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
@RequestMapping(value = "/users")
public class UsersController {
    @Autowired UserModelAssembler assembler;
    @Autowired PagedResourcesAssembler<UserEntity> pagedResourcesAssembler;
    @Autowired UserService userService;
    //Get one
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<User>> getOne(@PathVariable Long id) {
        UserEntity user = userService.get(id);
        if(user.getId() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assembler.toModel(user), HttpStatus.OK);
    }

    //Get all with paging
    //Test with: http://localhost:8060/users?page=0&size=2&sort=id,asc
    //page=0 -> trang 1, size=2 -> 2 user mỗi trang, sort=id,asc -> sắp xếp tăng dần theo id
    @GetMapping()
    public ResponseEntity<PagedModel<EntityModel<User>>> getAllWithPaging(Pageable pageable){
        Page<UserEntity> users = userService.getAll(pageable);
        PagedModel<EntityModel<User>> page = pagedResourcesAssembler.toModel(users, assembler);
        page.add(linkTo(methodOn(UsersController.class).getAllWithPaging(pageable)).withSelfRel());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping(value = "/by_role/{id}")
    public ResponseEntity<CollectionModel<EntityModel<User>>> getUsersByRolesId(@PathVariable Long id){
        List<EntityModel<User>> users = userService.getByRoleId(id).stream()
                .map(user -> assembler.toModel(user))
                .collect(Collectors.toList());
        return new ResponseEntity<>(
                CollectionModel.of(users, linkTo(methodOn(UsersController.class).getUsersByRolesId(id)).withSelfRel()),
                HttpStatus.OK
        );
    }

    //Add new
    //Test with: http://localhost:8060/users
    //In request body:
    //To add user with a role: {"name": "ABC", "password":"abc","role": [{"id": 1}]}
    //To add user with no role: {"name": "Sales", "password":"123"}
    @PostMapping()
    public ResponseEntity<EntityModel<User>> addUser(@RequestBody UserEntity newUser){
        try{
            return new ResponseEntity<>(
                    assembler.toModel(userService.save(newUser)),
                    HttpStatus.CREATED
            );
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //Update
    //Test with: http://localhost:8060/users/update/1
    //In request body:
    //To update a user with no role: {"name": "Admin", "password": "456"}
    //To update a user with role: {"name": "Admin", "password": "456", "role":{"id":1}}
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<EntityModel<User>> updateRole(@RequestBody UserEntity newUser, @PathVariable Long id) {
        UserEntity user = userService.get(id);
        if(user.getId() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.update(user, newUser);
        return new ResponseEntity<>(assembler.toModel(user), HttpStatus.OK);
    }

    //Delete
    //Test with: http://localhost:8060/users/delete/1
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        if(userService.get(id).getId() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
