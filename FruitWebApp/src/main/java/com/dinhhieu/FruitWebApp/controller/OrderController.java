package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.dto.ResponData;
import com.dinhhieu.FruitWebApp.dto.request.OrderReq.OrderCreateRequest;
import com.dinhhieu.FruitWebApp.service.OrderService;
import com.dinhhieu.FruitWebApp.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
   @Autowired
    private OrderServiceImpl orderService;

   @PostMapping("/order")
    public ResponData<?> order(@RequestBody OrderCreateRequest orderCreateRequest){
       return new ResponData<>(HttpStatus.OK.value(), "order_place", this.orderService.createOrder(orderCreateRequest));
   }
}
