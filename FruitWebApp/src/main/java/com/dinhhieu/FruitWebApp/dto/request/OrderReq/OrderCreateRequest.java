package com.dinhhieu.FruitWebApp.dto.request.OrderReq;

import com.dinhhieu.FruitWebApp.model.Customer;
import com.dinhhieu.FruitWebApp.model.OrderDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class OrderCreateRequest {
    private Customer customer;
    private List<OrderDetail> orderDetails = new ArrayList<>();
}
