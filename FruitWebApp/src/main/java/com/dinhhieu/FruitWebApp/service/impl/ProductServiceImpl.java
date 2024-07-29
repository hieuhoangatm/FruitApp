package com.dinhhieu.FruitWebApp.service.impl;

import com.dinhhieu.FruitWebApp.model.Product;
import com.dinhhieu.FruitWebApp.repository.ProductRepository;
import com.dinhhieu.FruitWebApp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public void updateProduct(Product productRequest) {
        Product product = getProductById(productRequest.getId());
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setCategory(productRequest.getCategory());
    }

    @Override
    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(long id) {
        return this.productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
    }

    @Override
    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }
}
