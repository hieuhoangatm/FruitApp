package com.dinhhieu.FruitWebApp.dto.request.OrderReq;

import com.dinhhieu.FruitWebApp.model.Customer;
import com.dinhhieu.FruitWebApp.model.OrderDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderCreateRequest {
//    private Customer customer;
    private List<OrderDetail> orderDetails = new ArrayList<>();
}
