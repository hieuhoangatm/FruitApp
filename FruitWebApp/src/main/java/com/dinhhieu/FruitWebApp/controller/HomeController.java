package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.dto.ResponData;
import com.dinhhieu.FruitWebApp.model.Customer;
import com.dinhhieu.FruitWebApp.model.Role;
import com.dinhhieu.FruitWebApp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;
import java.util.Set;

@RestController("")
public class HomeController {
    @Autowired
    private CustomerRepository customerRepository;
    @GetMapping("")
    public ResponData<?> helloWeb(Principal principal){
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) principal;
        Map<String, Object> attributes = oAuth2AuthenticationToken.getPrincipal().getAttributes();

        String googleId = (String) attributes.get("sub");
        String email = (String) attributes.get("email");
        String firstName = (String) attributes.get("given_name");
        String lastname = (String) attributes.get("family_name");
        Role role = new Role();
        role.setName("USER");
        customerRepository.findCustomerByGoogleId(googleId).orElseGet(() -> {
            Customer customer = new Customer();
            customer.setGoogleId(googleId);
            customer.setEmail(email);
            customer.setFirstName(firstName);
            customer.setLastName(lastname);
            customer.setRoles(Set.of(role));
            customer.setAuthProvider("GOOGLE");
            return customerRepository.save(customer);
        });
        return new ResponData<>(HttpStatus.OK.value(),"Thông tin người dùng đăng nhập google: ", principal);
    }
}
