package com.dinhhieu.FruitWebApp.dto.request.CategoryReq;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryUpdateRequestDTO {
    @NotBlank(message = "category must be not empty")
    private String categoryName;

    private String urlImage;

    private String descriptionImage;

}
