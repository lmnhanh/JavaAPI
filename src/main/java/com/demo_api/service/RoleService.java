package com.demo_api.service;

import com.demo_api.entity.RoleEntity;
import com.demo_api.entity.UserEntity;
import com.demo_api.model.Role;
import com.demo_api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService{
    @Autowired RoleRepository repository;
    @Autowired UserService userService;

    public RoleEntity get(Long id){
        return repository.findById(id).orElse(new RoleEntity());
    }

    public Page<RoleEntity> getAll(int status, Pageable pageable){
        if(status == -1)
            return repository.findAll(pageable);
        return repository.findByStatus(status,pageable);
    }

    public List<RoleEntity> getByPrivilegeId(Long id){
        return repository.findRolesByPrivilegeId(id);
    }

    public RoleEntity update(RoleEntity oldRole, RoleEntity newRole){
        oldRole.setName(newRole.getName());
        oldRole.setStatus(newRole.getStatus());
        oldRole.setPrivileges(newRole.getPrivileges());
        return repository.save(oldRole);
    }

    public void delete(Long id){
        if(repository.findById(id).isPresent()){
            RoleEntity role = repository.findById(id).get();
            for(UserEntity user : role.getUsers()){
                user.setRole(null);
                userService.save(user);
            }
            role.setUsers(null);
            role.setStatus(0);
            repository.save(role);
        }
    }

    public RoleEntity save(RoleEntity role){
        return repository.save(role);
    }

    public Role toDto(RoleEntity role){
        return new Role(role.getId(), role.getName(), role.getStatus());
    }
}
