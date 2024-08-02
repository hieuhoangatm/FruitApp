package com.dinhhieu.FruitWebApp.dto.response.CustomerRes;

import com.dinhhieu.FruitWebApp.dto.response.RoleResponse;
import com.dinhhieu.FruitWebApp.model.Role;
import com.dinhhieu.FruitWebApp.validator.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private long id;

    private String firstName;

    private String lastName;

    private String address;

    private String email;

//    private String password;

    private String phoneNumber;

//    private Set<String> role;
    private Set<RoleResponse> role;

    private Date createdAt;

    private Date updatedAt;

    private String createdBy;

    private String updatedBy;

}
