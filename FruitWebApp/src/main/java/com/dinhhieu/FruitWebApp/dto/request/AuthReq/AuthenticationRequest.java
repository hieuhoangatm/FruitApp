package com.dinhhieu.FruitWebApp.dto.request.AuthReq;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthenticationRequest {
    private String email;
    private String password;
}
