package com.dinhhieu.FruitWebApp.dto.response.AuthRes;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthenticationResponse {
    boolean authenticated;
    String token;
}
