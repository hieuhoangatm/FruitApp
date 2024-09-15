package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.dto.ResponData;
import com.dinhhieu.FruitWebApp.dto.request.CustomerReq.CustomerCreateRequest;
import com.dinhhieu.FruitWebApp.dto.request.CustomerReq.CustomerUpdateRequest;
import com.dinhhieu.FruitWebApp.dto.response.CustomerRes.CustomerResponse;
import com.dinhhieu.FruitWebApp.model.Customer;
import com.dinhhieu.FruitWebApp.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/customer")
    public ResponData<?> createCustomer(@Valid @RequestBody CustomerCreateRequest customerCreateRequest){
        return new ResponData<>(HttpStatus.CREATED.value(), "create customer", this.customerService.saveCustomer(customerCreateRequest));
    }

    @GetMapping("/customer/{id}")
    public ResponData<?> getCustomerById(@PathVariable long id){
        return new ResponData<>(HttpStatus.OK.value(), "get user by "+ id, this.customerService.getCustomerById(id));
    }

    @GetMapping("/customer")
    public ResponData<List<CustomerResponse>> getAllCustomer() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("info auth email: " + authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info("auth verify: "+ grantedAuthority.getAuthority()));
        log.info(authentication.toString());
        log.info("tét"+ authentication.getAuthorities().toString());
        return  new ResponData<>(HttpStatus.OK.value(), "get all customer", this.customerService.getAllCustomer());
    }

    @GetMapping("customer/get-customer-by-createAtandUpdateAt")
    public ResponData<?> getAllCustomerByCreateAt(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws ParseException {
        return new ResponData<>(HttpStatus.OK.value(), "gell all customer by sort", this.customerService.findAllCustomerByCreateAt(startDate,endDate));
    }

    @GetMapping("/get-customer-sortBy")
    public ResponData<?> getCustomerSortBy(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize ){
        return new ResponData<>(HttpStatus.OK.value(), "get customer data", this.customerService.getAllCustomerWithSortBy(pageNo,pageSize));
    }

    @GetMapping("/customer/search")
    public ResponData<?> getSearchCustomer(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) String address,
                                           @RequestParam(required = false) String email){
//        name = name != null && !name.isEmpty() ? name.toLowerCase() : null;
//        email = email != null && !email.isEmpty() ? email.toLowerCase() : null;
//        address = address != null && !address.isEmpty() ? address.toLowerCase() : null;
        return new ResponData<>(HttpStatus.OK.value(), "get search customer", this.customerService.searchCustomer(name,address,email));
    }

    @GetMapping("/customer/get-all-customer-with-by-sort-multiply")
    public ResponData<?> getAllCustomerWithBySortMulti(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                       @RequestParam(required = false) String... sorts
    ){
        return new ResponData<>(HttpStatus.OK.value(), "get all customer with by sort multi", this.customerService.getAllCustomerWithSortByMultipleColumns(pageNo,pageSize,sorts));
    }

    @PutMapping("/customer/{id}")
    public ResponData<?> updateCustomer(@PathVariable("id") long id, @RequestBody CustomerUpdateRequest customerUpdateRequest){
        return new ResponData<>(HttpStatus.ACCEPTED.value(), "Update customer",this.customerService.updateCustomer(id,customerUpdateRequest));
    }

    @DeleteMapping("/customer/{id}")
    public ResponData<?> deleteCustomer(@PathVariable("id") long id){
        this.customerService.deleteCustomer(id);
        return new ResponData<>(HttpStatus.NO_CONTENT.value(), "deleted customer sucessfully");
    }

    @GetMapping("/customer/getInfo")
    public ResponData<?> getInfoCustomer(){
        return new ResponData<>(HttpStatus.OK.value(),"get info user by id", this.customerService.getInfoThisCustomer());
    }

    @GetMapping("/customer/searchRole")
    public ResponData<?> findCustomerRole(@RequestParam("roleName") String roleName,
                                          @RequestParam("permissionName") String permissionName){
        return new ResponData<>(HttpStatus.OK.value(), "get search role", this.customerService.findCustomerByRole(roleName,permissionName));
    }

    @GetMapping("/customer/findCustomerWithSortByColumnAndSearch")
    public ResponData<?> customerWithSortByColumnAndSearch(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                           @RequestParam(defaultValue = "20", required = false) int pageSize,
                                                           @RequestParam(required = false) String search,
                                                           @RequestParam(required = false) String sortBy
                                                           ){
        return new ResponData<>(HttpStatus.OK.value(), "get customer with sort by column and search customer query", this.customerService.getALlCustomerWithSortByColumnAndSearch(pageNo,pageSize,search,sortBy));
    }
    @GetMapping("/customer/searchCriteria")
    public ResponData<?> findCustomerByCriteria(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                @RequestParam(defaultValue = "20", required = false) int pageSize,
                                                @RequestParam(required = false) String sortBy,
                                                @RequestParam(required = false) String... search
                                                ){
        return new ResponData<>(HttpStatus.OK.value(), "get search customer by criteria", this.customerService.findCustomerByCriteria(pageNo,pageSize,sortBy,search));
    }




}
