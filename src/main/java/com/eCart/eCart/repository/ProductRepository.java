package com.eCart.eCart.repository;

import com.eCart.eCart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    // --Searching Products in admin dashboard--
    List<Product> findAllByNameContaining(String title);
}
