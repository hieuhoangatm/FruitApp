package com.dinhhieu.FruitWebApp.repository;

import com.dinhhieu.FruitWebApp.dto.response.CustomerRes.CustomerResponse;
import com.dinhhieu.FruitWebApp.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

//    @Query("SELECT new com.dinhhieu.FruitWebApp.dto.response.CustomerRes( "
//            + "c.firstName, c.address, c.phoneNumber, cr.name, "
//            + "COUNT(DISTINCT p.productName), SUM(od.quantity), SUM(o.totalPrice)) "
//            + "FROM Customer c "
//            + "LEFT JOIN c.orders o "
//            + "LEFT JOIN c.roles cr "
//            + "LEFT JOIN o.orderDetails od "
//            + "LEFT JOIN od.product p "
//            + "GROUP BY c.firstName, c.address, c.phoneNumber, cr.name")
//    Page<CustomerResponse> customerOrderDetail(Pageable pageable);

}
