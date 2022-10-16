package com.demo_api.service;

import com.demo_api.entity.RoleEntity;
import com.demo_api.model.Role;
import com.demo_api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService{
    @Autowired RoleRepository repository;

    public List<RoleEntity> getAll(){
        return repository.findAll();
    }

    public List<RoleEntity> getByPrivilegeId(Long id){
        return repository.findRolesByPrivilegeId(id);
    }

    public Role toDto(RoleEntity role){
        return new Role(role.getId(), role.getName());
    }
}
