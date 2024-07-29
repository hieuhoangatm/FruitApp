package com.dinhhieu.FruitWebApp.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VerifyTokenRequest {
    private String token;

}
