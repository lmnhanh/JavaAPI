package com.demo_api.repository;

import com.demo_api.entity.ProductDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetailEntity, Long> {
    @Query(value = "SELECT d FROM ProductDetailEntity d WHERE d.product.id = :id")
    public List<ProductDetailEntity> findByProductId(@Param("id") Long id);
    @Query(value = "SELECT d FROM ProductDetailEntity d WHERE d.product.id = :id AND d.size LIKE :size")
    public List<ProductDetailEntity> findBySizeAndProductId(@Param("size") String size, @Param("id") Long id);
    @Query(value = "SELECT d FROM ProductDetailEntity d WHERE d.product.id = :id AND d.stock <= 0")
    public List<ProductDetailEntity> findOutStock(@Param("id") Long id);
    @Query(value = "SELECT d FROM ProductDetailEntity d WHERE d.product.id = :id AND d.stock > 0")
    public List<ProductDetailEntity> findInStock(@Param("id") Long id);
    @Query(value = "SELECT d FROM ProductDetailEntity d WHERE d.product.id = :id AND d.size LIKE :size AND d.stock <= 0")
    public List<ProductDetailEntity> findOutStockBySize(@Param("size") String size, @Param("id") Long id);
    @Query(value = "SELECT d FROM ProductDetailEntity d WHERE d.product.id = :id AND d.size LIKE :size AND d.stock > 0")
    public List<ProductDetailEntity> findInStockBySize(@Param("size") String size, @Param("id") Long id);
}