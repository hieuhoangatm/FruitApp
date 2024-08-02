package com.dinhhieu.FruitWebApp.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RefreshTokenRequest {
    private String token;
}
