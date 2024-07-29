package com.dinhhieu.FruitWebApp.repository;

import com.dinhhieu.FruitWebApp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
