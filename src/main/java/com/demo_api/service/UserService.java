package com.demo_api.service;

import com.demo_api.entity.UserEntity;
import com.demo_api.repository.PrivilegeRepository;
import com.demo_api.repository.RoleRepository;
import com.demo_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PrivilegeRepository privilegeRepository;

    public List<UserEntity> getByRoleId(Long id){
        return userRepository.findUsersByRoleId(id);
    }
}
