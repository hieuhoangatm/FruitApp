package com.dinhhieu.FruitWebApp.service.impl;

import com.dinhhieu.FruitWebApp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl {
    private final OrderRepository orderRepository;


}
