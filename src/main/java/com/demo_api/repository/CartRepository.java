package com.demo_api.repository;

import com.demo_api.entity.CartEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    @Query(value = "SELECT c FROM CartEntity c WHERE c.user.id = :id AND c.status = :status")
    public Page<CartEntity> findByStatusAndUserId(@Param("id") Long user, @Param("status") int status, Pageable pageable);

    @Query(value = "SELECT c FROM CartEntity c WHERE c.user.id = :id AND c.detail.id = :d_id AND c.status = 0")
    public CartEntity findByUserIdAndDetailId(@Param("id")Long user_id, @Param("d_id") Long detail_id);
}
