package com.demo_api.controller;

import com.demo_api.model.Role;
import com.demo_api.model.RoleEntity;
import com.demo_api.model.User;
import com.demo_api.model.UserEntity;
import com.demo_api.repository.RoleRepository;
import com.demo_api.repository.UserRepository;
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
@RequestMapping(value = "/users")
public class UsersController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    public UsersController(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }
    //Get one
    @GetMapping("/{id}")
    public EntityModel<User> getOne(@PathVariable Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(new UserEntity());
        User user = new User(userEntity.getId(), userEntity.getName(), userEntity.getPassword());
        if(user.getId() == null){
            return EntityModel.of(user);
        }
        return EntityModel.of(user,
                linkTo(methodOn(UsersController.class).getOne(id)).withSelfRel(),
                linkTo(methodOn(RolesController.class).getOne(userEntity.getRole().getId())).withRel("role"));
    }

    //Get all
    @GetMapping()
    public CollectionModel<EntityModel<User>> getAll(){
        List<EntityModel<User>> users = userRepository.findAll().stream()
                .map(user -> EntityModel.of(new User(user.getId(), user.getName(), user.getPassword()),
                        linkTo(methodOn(UsersController.class).getOne(user.getId())).withSelfRel(),
                        linkTo(methodOn(RolesController.class).getOne(user.getRole().getId())).withRel("role"))
                ).collect(Collectors.toList());
        return CollectionModel.of(users, linkTo(methodOn(UsersController.class).getAll()).withSelfRel());
    }

    @GetMapping(value = "/by_role/{id}")
    public CollectionModel<EntityModel<User>> getUsersByRolesId(@PathVariable Long id){
        List<EntityModel<User>> users = userRepository.findUsersByRoleId(id).stream()
                .map(user -> EntityModel.of(new User(user.getId(), user.getName(), user.getPassword()),
                        linkTo(methodOn(UsersController.class).getOne(user.getId())).withSelfRel(),
                        linkTo(methodOn(RolesController.class).getOne(user.getRole().getId())).withRel("role"))
                ).collect(Collectors.toList());
        return CollectionModel.of(users, linkTo(methodOn(UsersController.class).getUsersByRolesId(id)).withSelfRel());
    }
}
