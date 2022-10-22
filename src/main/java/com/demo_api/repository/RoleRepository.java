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

    public RoleEntity findByNameIgnoreCase(String name);
    public Page<RoleEntity> findAll(Pageable pageable);
    public Page<RoleEntity> findByStatus(int status, Pageable pageable);
    @Query("SELECT r FROM RoleEntity r JOIN r.privileges p WHERE p.id = :id")
    public Page<RoleEntity> findByPrivilegeId(@Param("id") Long id, Pageable pageable);
    @Query("SELECT r FROM RoleEntity r JOIN r.privileges p WHERE p.id = :id AND r.status= :status")
    public Page<RoleEntity> findByStatusAndPrivilegeId(@Param("status") int status, @Param("id") Long id, Pageable pageable);

    @Query("SELECT r FROM RoleEntity r JOIN r.privileges p WHERE p.id = :id")
    public List<RoleEntity> findRolesByPrivilegeId(@Param("id") Long id);
}
