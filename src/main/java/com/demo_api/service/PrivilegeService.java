package com.demo_api.service;

import com.demo_api.Entity.PrivilegeEntity;
import com.demo_api.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {
    @Autowired
    PrivilegeRepository repository;

    public PrivilegeEntity get(Long id){
        return repository.findById(id).orElse(new PrivilegeEntity());
    }

    public List<PrivilegeEntity> getAll(){
        return  repository.findAll();
    }

    public List<PrivilegeEntity> getByRoleId(Long id){
        return repository.findPrivilegesByRoleId(id);
    }

    public PrivilegeEntity save(PrivilegeEntity privilege){
        return repository.save(privilege);
    }
}
