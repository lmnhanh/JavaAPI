package com.demo_api.repository;

import com.demo_api.entity.ProductDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetailEntity, Long> {
    @Query(value = "SELECT d FROM ProductDetailEntity d WHERE d.product.id = :id")
    public List<ProductDetailEntity> findByProductId(@Param("id") Long id);
}