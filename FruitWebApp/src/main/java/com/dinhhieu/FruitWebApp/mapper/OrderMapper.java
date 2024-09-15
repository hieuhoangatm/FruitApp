package com.dinhhieu.FruitWebApp.mapper;

import com.dinhhieu.FruitWebApp.dto.request.OrderReq.OrderCreateRequest;
import com.dinhhieu.FruitWebApp.dto.response.OrderRes.OrderResponse;
import com.dinhhieu.FruitWebApp.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toOrderResponse(Order order);

    Order toOrder(OrderCreateRequest orderCreateRequest);
}
