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
    //Lấy tất cả user: http://localhost:8060/users?page=0&size=2&sort=id,asc
    //Lấy tất cả user có role_id là 1: ""Test with: http://localhost:8060/users?role=1
    //Lấy tất cả user còn đang hoạt động (status = 1) có role_id là 2 và phân 2 user mỗi trang: http://localhost:8060/users?role=2&status=1&size=2
    @GetMapping()
    public ResponseEntity<PagedModel<EntityModel<User>>> getAll(@RequestParam(defaultValue = "-1") Long role, @RequestParam(defaultValue = "-1") int status, Pageable pageable){
        Page<UserEntity> users = userService.getAll(role, status, pageable);
        PagedModel<EntityModel<User>> page = pagedResourcesAssembler.toModel(users, assembler);
        page.add(linkTo(methodOn(UsersController.class).getAll(role, status, pageable)).withSelfRel());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    //Add new
    //Test with: http://localhost:8060/users
    //In request body:
    //Thêm user với role_id "1" (role phải có trước đó): {"name": "ABC", "password":"abc","role": [{"id": 1}]}
    //Thêm user không có role nào (test): {"name": "Sales", "password":"123"}
    @PostMapping()
    public ResponseEntity<EntityModel<User>> addUser(@RequestBody UserEntity newUser){
        try{
            return new ResponseEntity<>(
                    assembler.toModel(userService.addUserWithRole(newUser)),
                    HttpStatus.CREATED
            );
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Update cho user có id là "1": http://localhost:8060/users/1/update
    //In request body:
    //Gán user không có role: {"name": "Admin", "password": "456"}
    //Gán cho user 1 role mới : {"name": "Admin", "password": "456", "role":{"id":1}}
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<EntityModel<User>> updateUser(@RequestBody UserEntity newUser, @PathVariable Long id) {
        UserEntity user = userService.get(id);
        if(user.getId() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            userService.update(user, newUser);
            return new ResponseEntity<>(assembler.toModel(user), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Delete
    //Test with: http://localhost:8060/users/1/delete
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if(userService.get(id).getId() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
