package com.demo_api.service;

import com.demo_api.entity.RoleEntity;
import com.demo_api.entity.UserEntity;
import com.demo_api.model.User;
import com.demo_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public UserEntity get(Long id){
        return repository.findById(id).orElse(new UserEntity());
    }
    public List<UserEntity> getByRoleId(Long id){
        return repository.findUsersByRoleId(id);
    }

    public List<UserEntity> getAll(){
        return repository.findAll();
    }

    public Page<UserEntity> getAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public UserEntity update(UserEntity oldUser, UserEntity newUser){
        oldUser.setName(newUser.getName());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setRole(newUser.getRole());
        return repository.save(oldUser);
    }

    public void delete(Long id){
        if(repository.findById(id).isPresent()){
            UserEntity user = repository.findById(id).get();
            repository.delete(user);
        }
    }
    public UserEntity save(UserEntity user){
        return repository.save(user);
    }

    public User toDto(UserEntity user){
        return new User(user.getId(), user.getName(), user.getPassword());
    }
}
