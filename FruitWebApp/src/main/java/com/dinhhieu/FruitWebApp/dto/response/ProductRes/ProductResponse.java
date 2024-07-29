package com.dinhhieu.FruitWebApp.dto.response.ProductRes;

import com.dinhhieu.FruitWebApp.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private long id;

    private String productName;
    private String description;

    private int quantity;

    private double price;

    private Category category;

}
