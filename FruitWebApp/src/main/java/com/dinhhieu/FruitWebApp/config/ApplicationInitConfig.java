package com.dinhhieu.FruitWebApp.config;

import com.dinhhieu.FruitWebApp.enums.Role;
import com.dinhhieu.FruitWebApp.model.Customer;
import com.dinhhieu.FruitWebApp.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.TreeSet;

@Configuration
@Slf4j
public class ApplicationInitConfig {

    @Bean
    ApplicationRunner applicationRunner(CustomerRepository customerRepository){
        return args -> {
            if(customerRepository.findByEmail("admin@gmail.com").isEmpty()){
                TreeSet<String> roles = new TreeSet<>();
                roles.add(Role.ADMIN.name());

                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

                Customer customer = Customer.builder()
                        .firstName("admin")
                        .lastName("customer")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("123456"))
//                        .role(roles)
                        .build();

                customerRepository.save(customer);
                log.warn("admin account created default: admin@gmail.com with password: 123456");
            }
        };
    }
}
