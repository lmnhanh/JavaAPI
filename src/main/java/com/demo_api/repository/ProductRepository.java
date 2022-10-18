package com.demo_api.repository;

import com.demo_api.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    public Page<ProductEntity> findAll(Pageable pageable);

    public Page<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
    public Page<ProductEntity> findByStatusAndNameContainingIgnoreCase(int status, String name, Pageable pageable);
    public Page<ProductEntity> findByStatus(int status, Pageable pageable);
}