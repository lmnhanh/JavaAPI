package com.demo_api.repository;

import com.demo_api.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    public Page<RoleEntity> findAll(Pageable pageable);
    public Page<RoleEntity> findByStatus(int status, Pageable pageable);
    @Query("SELECT r FROM RoleEntity r JOIN r.privileges p where p.id = :id")
    public List<RoleEntity> findRolesByPrivilegeId(@Param("id") Long id);
}
