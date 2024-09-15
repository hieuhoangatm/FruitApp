package com.dinhhieu.FruitWebApp.service.impl;

import com.cloudinary.api.exceptions.ApiException;
import com.dinhhieu.FruitWebApp.dto.request.CustomerReq.CustomerCreateRequest;
import com.dinhhieu.FruitWebApp.dto.request.CustomerReq.CustomerUpdateRequest;
import com.dinhhieu.FruitWebApp.dto.response.CustomerRes.CustomerResponse;
import com.dinhhieu.FruitWebApp.dto.response.PageResponse;
import com.dinhhieu.FruitWebApp.exception.AppException;
import com.dinhhieu.FruitWebApp.exception.ErrorCode;
import com.dinhhieu.FruitWebApp.mapper.CustomerMapper;
import com.dinhhieu.FruitWebApp.model.Customer;
import com.dinhhieu.FruitWebApp.model.Role;
import com.dinhhieu.FruitWebApp.repository.CustomerRepository;
import com.dinhhieu.FruitWebApp.repository.RoleRepository;
import com.dinhhieu.FruitWebApp.repository.SearchRepository;
import com.dinhhieu.FruitWebApp.service.CustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    private final RoleRepository roleRepository;

    private final SearchRepository searchRepository;

//    private PasswordEncoder passwordEncoder;
    @Override
    public CustomerResponse saveCustomer(CustomerCreateRequest customerCreateRequest) {
        if(this.customerRepository.existsByEmail(customerCreateRequest.getEmail())){
           throw new AppException(ErrorCode.USER_EXISTED);
        }
        Customer customer = customerMapper.toCustomer(customerCreateRequest);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        customer.setPassword(passwordEncoder.encode(customerCreateRequest.getPassword()));

        //set role
//        TreeSet<String> roles = new TreeSet<>();
//        roles.add(Role.USER.name());
//        roles.add(Role.ADMIN.name());
//        customer.setRole(roles);

        //set theo database
//        List<com.dinhhieu.FruitWebApp.model.Role> roles = roleRepository.findAllById(customerCreateRequest.getRoles());
//        customer.setRoles(new HashSet<>(roles));

        List<Role> roles = roleRepository.findAllById(customerCreateRequest.getRole());
        customer.setRoles(new HashSet<>(roles));

        this.customerRepository.save(customer);
        return customerMapper.toCustomerResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(long id, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = this.customerRepository.findById(id).orElseThrow(()-> new RuntimeException("customer not found"));
        customerMapper.updateCustomer(customer,customerUpdateRequest);

//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        customer.setPassword(passwordEncoder.encode(customerUpdateRequest.getPassWord));

//        List<com.dinhhieu.FruitWebApp.model.Role> roles = roleRepository.findAllById(customerUpdateRequest.getRoles());
//        customer.setRoles(new HashSet<>(roles));

        List<Role> roles = roleRepository.findAllById(customerUpdateRequest.getRole());
        customer.setRoles(new HashSet<>(roles));

        return this.customerMapper.toCustomerResponse(this.customerRepository.save(customer));
    }


    @Override
    public void deleteCustomer(long customerId) {
        this.customerRepository.deleteById(customerId);
    }

    @Override
    public CustomerResponse getCustomerById(long customerId) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(()->new RuntimeException("Customer not found"));
        return customerMapper.toCustomerResponse(customer);
    }

    @Override
//    @PostAuthorize("returnObject.username == authentication.name")
//    @PreAuthorize("hasAuthority('SCOPE_CREATE_DATA')")
        public List<CustomerResponse> getAllCustomer() {
        log.info("get customer by role admin");
        return this.customerRepository.findAll().stream().map(customerMapper::toCustomerResponse).toList();

    }


    @Override
    public List<Customer> findAllCustomerByCreateAt(String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDay = sdf.parse(startDate);
        Date endDay = sdf.parse(endDate);
        return this.customerRepository.findCustomersByCreatedAtBetween(startDay,endDay);
    }

    @Override
    public Page<Customer> getAllCustomerWithSortBy(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return customerRepository.findAll(pageable);
    }

    @Override
    public List<Customer> searchCustomer(String name, String address, String email) {

         return customerRepository.search(name.toLowerCase(),address.toLowerCase(), email.toLowerCase());
    }

    @Override
    public Page<Customer> getAllCustomerWithSortByMultipleColumns(int pageNo, int pageSize, String... sorts) {
        List<Sort.Order> orders = new ArrayList<>();
        if(sorts!=null){
            for(String sortBy: sorts){
                Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
                Matcher matcher = pattern.matcher(sortBy);
                if(matcher.find()){
                    if(matcher.group(3).equalsIgnoreCase("asc")){
                        orders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                    }else{
                        orders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                    }
                }
            }
        }
        Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by(orders));
        return this.customerRepository.findAll(pageable);

    }

    public CustomerResponse getInfoThisCustomer(){
        //SecurityContext
        SecurityContext context = SecurityContextHolder.getContext();

        String email = context.getAuthentication().getName();

        Customer customer = customerRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("customer not existed"));

        return customerMapper.toCustomerResponse(customer);
    }


    @Override
    public List<Customer> findCustomerByRole(String nameRole, String namePermission) {
        return this.customerRepository.findCustomerByRole(nameRole,namePermission);
    }

    @Override
    public PageResponse<?> getALlCustomerWithSortByColumnAndSearch(int pageNo, int pageSize, String search, String sortBy) {
        return this.searchRepository.getALlCustomerWithSortByColumnAndSearch(pageNo, pageSize, search,sortBy);
    }

    @Override
    public PageResponse<?> findCustomerByCriteria(int pageNo, int pageSize, String sortBy, String... search) {
        return searchRepository.advanceSearchCustomer(pageNo,pageSize,sortBy,search);
    }
}
