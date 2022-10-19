package com.demo_api.repository;

import com.demo_api.entity.UserEntity;
import com.demo_api.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "SELECT u FROM UserEntity u WHERE u.role.id = :id")
    public Page<UserEntity> findByRoleId(@Param("id") Long id, Pageable pageable);
    public Page<UserEntity> findAll(Pageable pageable);
    public Page<UserEntity> findByStatus(int status, Pageable pageable);
    @Query(value = "SELECT u FROM UserEntity u WHERE u.role.id = :id AND u.status = :status")
    public Page<UserEntity> findByStatusAndRole(@Param("status") int status, @Param("id") Long role, Pageable pageable);
}