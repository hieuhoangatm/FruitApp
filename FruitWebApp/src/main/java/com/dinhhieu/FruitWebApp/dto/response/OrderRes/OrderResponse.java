package com.dinhhieu.FruitWebApp.dto.response.OrderRes;

import com.dinhhieu.FruitWebApp.model.Customer;
import com.dinhhieu.FruitWebApp.model.OrderDetail;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderResponse {

    private Customer customer;

    private List<OrderDetail> orderDetails = new ArrayList<>();

    private double totalPrice;
}
