package com.dinhhieu.FruitWebApp.service.impl;

import com.dinhhieu.FruitWebApp.dto.request.OrderReq.OrderCreateRequest;
import com.dinhhieu.FruitWebApp.dto.response.OrderRes.OrderResponse;
import com.dinhhieu.FruitWebApp.mapper.OrderMapper;
import com.dinhhieu.FruitWebApp.model.Customer;
import com.dinhhieu.FruitWebApp.model.Order;
import com.dinhhieu.FruitWebApp.model.OrderDetail;
import com.dinhhieu.FruitWebApp.model.Product;
import com.dinhhieu.FruitWebApp.repository.CustomerRepository;
import com.dinhhieu.FruitWebApp.repository.OrderRepository;
import com.dinhhieu.FruitWebApp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl {
    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest orderCreateRequest){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String email = securityContext.getAuthentication().getName();

        Customer customer = customerRepository.findByEmail(email).orElseThrow(()->new RuntimeException("customer not existed"));

        Order order = Order.builder().customer(customer)
                .build();

        List<OrderDetail> orderDetails = orderCreateRequest.getOrderDetails().stream().map(detailOrder->{
            Product product = productRepository.findById(detailOrder.getId()).orElseThrow(()->new RuntimeException("Product not found"));

            if(product.getQuantity() < detailOrder.getQuantity()){

                throw new RuntimeException("Not enough in stock");
            }

            product.setQuantity(product.getQuantity()-detailOrder.getQuantity());

            OrderDetail orderDetail = OrderDetail.builder().order(order).product(product).quantity(detailOrder.getQuantity()).build();

            order.setTotalPrice(order.getTotalPrice() + product.getPrice() * detailOrder.getQuantity());

            return orderDetail;
                }).toList();

        order.setOrderDetails(orderDetails);

        Order order_save = orderRepository.save(order);

        return orderMapper.toOrderResponse(order_save);
    }
}
