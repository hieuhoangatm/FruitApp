package com.dinhhieu.FruitWebApp.dto.request.CustomerReq;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerUpdateRequest {
    private String firstName;

    private String lastName;

    private String address;

    private String email;

    private String phoneNumber;

    private List<String> roles;

}
