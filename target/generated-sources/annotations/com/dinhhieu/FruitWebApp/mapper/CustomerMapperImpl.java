package com.dinhhieu.FruitWebApp.mapper;

import com.dinhhieu.FruitWebApp.dto.request.CustomerReq.CustomerCreateRequest;
import com.dinhhieu.FruitWebApp.dto.request.CustomerReq.CustomerUpdateRequest;
import com.dinhhieu.FruitWebApp.dto.response.CustomerRes.CustomerResponse;
import com.dinhhieu.FruitWebApp.model.Customer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-27T11:23:43+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer toCustomer(CustomerCreateRequest customerCreateRequest) {
        if ( customerCreateRequest == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.firstName( customerCreateRequest.getFirstName() );
        customer.lastName( customerCreateRequest.getLastName() );
        customer.address( customerCreateRequest.getAddress() );
        customer.email( customerCreateRequest.getEmail() );
        customer.password( customerCreateRequest.getPassword() );
        customer.phoneNumber( customerCreateRequest.getPhoneNumber() );

        return customer.build();
    }

    @Override
    public CustomerResponse toCustomerResponse(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerResponse.CustomerResponseBuilder customerResponse = CustomerResponse.builder();

        customerResponse.id( customer.getId() );
        customerResponse.firstName( customer.getFirstName() );
        customerResponse.lastName( customer.getLastName() );
        customerResponse.address( customer.getAddress() );
        customerResponse.email( customer.getEmail() );
        customerResponse.phoneNumber( customer.getPhoneNumber() );
        customerResponse.createdAt( customer.getCreatedAt() );
        customerResponse.updatedAt( customer.getUpdatedAt() );
        customerResponse.createdBy( customer.getCreatedBy() );
        customerResponse.updatedBy( customer.getUpdatedBy() );

        return customerResponse.build();
    }

    @Override
    public void updateCustomer(Customer customer, CustomerUpdateRequest customerUpdateRequest) {
        if ( customerUpdateRequest == null ) {
            return;
        }

        customer.setFirstName( customerUpdateRequest.getFirstName() );
        customer.setLastName( customerUpdateRequest.getLastName() );
        customer.setAddress( customerUpdateRequest.getAddress() );
        customer.setEmail( customerUpdateRequest.getEmail() );
        customer.setPhoneNumber( customerUpdateRequest.getPhoneNumber() );
    }
}
