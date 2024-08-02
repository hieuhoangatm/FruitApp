package com.dinhhieu.FruitWebApp.service;

import com.dinhhieu.FruitWebApp.dto.response.ProductRes.ProductResponse;
import com.dinhhieu.FruitWebApp.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(long id);

    Product getProductById(long id);

    List<Product> getAllProduct();

    Page<ProductResponse> getAllProductWithPage(int pageNo, int pageSize);

    Page<ProductResponse> getAllProductWithBySortMultiColumns(int pageNo, int pageSize, String... sorts);

    Page<ProductResponse> searchProduct(int pageNo, int pageSize, String name, Double priceMin, Double priceMax);
}
