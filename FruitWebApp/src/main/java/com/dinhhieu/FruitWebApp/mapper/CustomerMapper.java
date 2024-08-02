package com.dinhhieu.FruitWebApp.mapper;

import com.dinhhieu.FruitWebApp.dto.request.CustomerReq.CustomerCreateRequest;
import com.dinhhieu.FruitWebApp.dto.request.CustomerReq.CustomerUpdateRequest;
import com.dinhhieu.FruitWebApp.dto.response.CustomerRes.CustomerResponse;
import com.dinhhieu.FruitWebApp.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerCreateRequest customerCreateRequest);


    CustomerResponse toCustomerResponse(Customer customer);

    @Mapping(target = "roles",ignore = true)
    void updateCustomer(@MappingTarget Customer customer, CustomerUpdateRequest customerUpdateRequest);
}
