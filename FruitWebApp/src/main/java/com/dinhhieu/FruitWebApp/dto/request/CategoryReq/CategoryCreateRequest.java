package com.dinhhieu.FruitWebApp.dto.request.CategoryReq;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryCreateRequest {
    @NotBlank(message = "categoryName must be not empty")
    private String categoryName;

    private String urlImage;

    private String type;

    private String descriptionImage;
}
