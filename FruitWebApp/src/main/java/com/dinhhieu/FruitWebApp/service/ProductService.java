package com.dinhhieu.FruitWebApp.service;

import com.dinhhieu.FruitWebApp.model.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(long id);

    Product getProductById(long id);

    List<Product> getAllProduct();
}
