package com.dinhhieu.FruitWebApp.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissonRequest {
    private String id;
    private String name;
    private String description;
}
