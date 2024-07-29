package com.dinhhieu.FruitWebApp.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VerifyTokenResponse {
    private boolean valid;
}
