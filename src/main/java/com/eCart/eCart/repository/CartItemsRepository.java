package com.eCart.eCart.repository;

import com.eCart.eCart.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems,Long> {

    Optional<CartItems> findByProductIdAndOrderIdAndUserId(Long productId, Long id, Long userId);
}
