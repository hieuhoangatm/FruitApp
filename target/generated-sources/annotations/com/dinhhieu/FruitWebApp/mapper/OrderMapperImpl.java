package com.dinhhieu.FruitWebApp.mapper;

import com.dinhhieu.FruitWebApp.dto.request.OrderReq.OrderCreateRequest;
import com.dinhhieu.FruitWebApp.dto.response.OrderRes.OrderResponse;
import com.dinhhieu.FruitWebApp.model.Order;
import com.dinhhieu.FruitWebApp.model.OrderDetail;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-23T17:06:23+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderResponse toOrderResponse(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setCustomer( order.getCustomer() );
        List<OrderDetail> list = order.getOrderDetails();
        if ( list != null ) {
            orderResponse.setOrderDetails( new ArrayList<OrderDetail>( list ) );
        }
        orderResponse.setTotalPrice( order.getTotalPrice() );

        return orderResponse;
    }

    @Override
    public Order toOrder(OrderCreateRequest orderCreateRequest) {
        if ( orderCreateRequest == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        List<OrderDetail> list = orderCreateRequest.getOrderDetails();
        if ( list != null ) {
            order.orderDetails( new ArrayList<OrderDetail>( list ) );
        }

        return order.build();
    }
}
