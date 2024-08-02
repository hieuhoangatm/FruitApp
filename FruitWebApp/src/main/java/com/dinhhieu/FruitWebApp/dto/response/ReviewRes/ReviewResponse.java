package com.dinhhieu.FruitWebApp.dto.response.ReviewRes;

import com.dinhhieu.FruitWebApp.dto.response.CustomerRes.CustomerResponse;
import com.dinhhieu.FruitWebApp.dto.response.ProductRes.ProductResponse;
import com.dinhhieu.FruitWebApp.model.Customer;
import com.dinhhieu.FruitWebApp.model.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ReviewResponse {
    private long id;

    private String comment;

    private ProductResponse product;

    private CustomerResponse customer;
}
