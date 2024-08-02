package com.dinhhieu.FruitWebApp.service;

import com.dinhhieu.FruitWebApp.dto.request.CustomerReq.CustomerCreateRequest;
import com.dinhhieu.FruitWebApp.dto.request.CustomerReq.CustomerUpdateRequest;
import com.dinhhieu.FruitWebApp.dto.response.CustomerRes.CustomerResponse;
import com.dinhhieu.FruitWebApp.model.Customer;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface CustomerService {
    CustomerResponse saveCustomer(CustomerCreateRequest customerCreateRequest);

    CustomerResponse updateCustomer(long id, CustomerUpdateRequest customerUpdateRequest);

    void deleteCustomer(long customerId);

    CustomerResponse getCustomerById(long customerId);


    List<CustomerResponse> getAllCustomer();


    List<Customer> findAllCustomerByCreateAt(String startDate, String endDate) throws ParseException;

    Page<Customer> getAllCustomerWithSortBy(int pageNo, int pageSize);


    List<Customer> searchCustomer(String firstName, String address, String email);

    Page<Customer> getAllCustomerWithSortByMultipleColumns(int pageNo, int pageSize, String... sorts);

    CustomerResponse getInfoThisCustomer();

    List<Customer> findCustomerByRole(String nameRole, String namePermission);
}
