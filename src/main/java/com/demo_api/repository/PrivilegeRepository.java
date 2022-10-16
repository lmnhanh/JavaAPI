package com.demo_api.repository;

import com.demo_api.Entity.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity,Long> {
    @Query("SELECT p FROM PrivilegeEntity p JOIN p.roles r WHERE r.id = :id")
    public List<PrivilegeEntity> findPrivilegesByRoleId(@Param("id") Long id);
}
