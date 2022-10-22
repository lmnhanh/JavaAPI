package com.demo_api.repository;

import com.demo_api.entity.OrderEntity;
import com.demo_api.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query(value = "SELECT o FROM OrderEntity o WHERE o.user.id = :id")
    public Page<OrderEntity> findByUserId(@Param("id") Long id, Pageable pageable);
    @Query(value = "SELECT o FROM OrderEntity o WHERE o.user.id = :id AND o.created_at >= :start")
    public Page<OrderEntity> findByUserIdAfter(@Param("id") Long id, @Param("start") Date start, Pageable pageable);
    @Query(value = "SELECT o FROM OrderEntity o WHERE o.user.id = :id AND o.created_at <= :end")
    public Page<OrderEntity> findByUserIdBefore(@Param("id") Long id, @Param("end") Date end, Pageable pageable);
    @Query(value = "SELECT o FROM OrderEntity o WHERE o.user.id = :id AND o.created_at BETWEEN :start AND :end")
    public Page<OrderEntity> findByUserIdBetween(@Param("id") Long id, @Param("start") Date start, @Param("end") Date end, Pageable pageable);
}