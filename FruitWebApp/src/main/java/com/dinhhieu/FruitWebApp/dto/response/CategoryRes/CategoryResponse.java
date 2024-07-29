package com.dinhhieu.FruitWebApp.dto.response.CategoryRes;

import com.dinhhieu.FruitWebApp.model.Product;
import lombok.*;
;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private long id;

    private String categoryName;

    private String urlImage;

    private String descriptionImage;

    private String type;

    private Date createdAt;
    private Date updatedAt;

    private List<Product> product;

}
