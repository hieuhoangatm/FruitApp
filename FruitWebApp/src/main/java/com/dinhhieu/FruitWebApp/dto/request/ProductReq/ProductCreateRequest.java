package com.dinhhieu.FruitWebApp.dto.request.ProductReq;

import com.dinhhieu.FruitWebApp.model.Category;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class ProductCreateRequest {
    private String productName;

    private String description;

    private int quantity;

    private double price;

    private Category category;
}
