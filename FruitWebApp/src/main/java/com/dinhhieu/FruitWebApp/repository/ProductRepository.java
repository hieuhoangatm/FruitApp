package com.dinhhieu.FruitWebApp.repository;

import com.dinhhieu.FruitWebApp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product p " +
            "WHERE (:productName IS NULL OR LOWER(p.productName) LIKE %:productName%) " +
            "AND (:priceMin IS NULL OR p.price >= :priceMin) " +
            "AND (:priceMax IS NULL OR p.price <= :priceMax)")
    Page<Product> search(String productName, Double priceMin, Double priceMax, Pageable pageable);
}
