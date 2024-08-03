package com.dinhhieu.FruitWebApp.dto.request.CustomerReq;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateRequest {
    @NotBlank(message = "firstName must be not empty")
    private String firstName;

    @NotBlank(message = "lastName must be not empty")
    private String lastName;

    private String address;

    @NotBlank(message = "email must be not empty")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private List<String> roles;

    private String phoneNumber;
}
