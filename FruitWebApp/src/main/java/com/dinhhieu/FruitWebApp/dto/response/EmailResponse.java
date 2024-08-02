package com.dinhhieu.FruitWebApp.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailResponse {
    private String to;
    private String subject;
    private String text;
    private String status;
}
