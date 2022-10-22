package com.demo_api.service;

import com.demo_api.entity.UserEntity;
import com.demo_api.model.MessageResponse;
import com.demo_api.repository.RoleRepository;
import com.demo_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository repository;
    @Autowired
    RoleRepository roleRepository;

    public UserEntity get(Long id){
        return repository.findById(id).orElse(new UserEntity());
    }
    public UserEntity getByUsername(String username){
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }

    public Page<UserEntity> getAll(Long role, int status, Pageable pageable){
        if(role == -1L && status == -1)
            return repository.findAll(pageable);
        if(role != -1L && status != -1)
            return repository.findByStatusAndRole(status, role, pageable);
        if(status != -1)
            return repository.findByStatus(status, pageable);
        return repository.findByRoleId(role, pageable);
    }
    public void addWithRoleUser(UserEntity user){
        user.setRole(roleRepository.findByNameIgnoreCase("CUSTOMER"));
        save(user);
    }

    public UserEntity addUserWithRole(UserEntity user){
        return save(user);
    }

    public UserEntity update(UserEntity oldUser, UserEntity newUser){
        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setRole(newUser.getRole());
        return repository.save(oldUser);
    }

    public void delete(Long id){
        if(repository.findById(id).isPresent()){
            UserEntity user = repository.findById(id).get();
            user.setStatus(0);
            repository.save(user);
        }
    }

    public boolean isUsernameExist(String username){
        return repository.existsByUsername(username);
    }

    public boolean isEmailExist(String email){
        return repository.existsByEmail(email);
    }
    UserEntity save(UserEntity user){
        return repository.save(user);
    }
}
