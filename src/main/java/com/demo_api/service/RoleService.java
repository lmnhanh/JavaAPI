package com.demo_api.service;

import com.demo_api.model.RoleEntity;
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
}
