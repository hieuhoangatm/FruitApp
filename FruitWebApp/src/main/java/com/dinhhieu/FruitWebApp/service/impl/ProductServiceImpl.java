package com.dinhhieu.FruitWebApp.service.impl;

import com.dinhhieu.FruitWebApp.dto.response.ProductRes.ProductResponse;
import com.dinhhieu.FruitWebApp.exception.AppException;
import com.dinhhieu.FruitWebApp.exception.ErrorCode;
import com.dinhhieu.FruitWebApp.mapper.ProductMapper;
import com.dinhhieu.FruitWebApp.model.Order;
import com.dinhhieu.FruitWebApp.model.Product;
import com.dinhhieu.FruitWebApp.repository.ProductRepository;
import com.dinhhieu.FruitWebApp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

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
        return this.productRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Override
    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }

    @Override
    public Page<ProductResponse> getAllProductWithPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::toProductResponse);
    }

    @Override
    public Page<ProductResponse> getAllProductWithBySortMultiColumns(int pageNo, int pageSize, String... sorts) {
        List<Sort.Order> orders = new ArrayList<>();
        if(sorts!=null){
            for(String sortBy : sorts){
                Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
                Matcher matcher = pattern.matcher(sortBy);

                if (matcher.find()){
                    if(matcher.group(3).equalsIgnoreCase("asc")){
                        orders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                    }else{
                        orders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                    }
                }
            }
        }
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(orders));

        Page<Product> products = productRepository.findAll(pageable);

        return products.map(productMapper::toProductResponse);
    }

    @Override
    public Page<ProductResponse> searchProduct(int pageNo, int pageSize, String name, Double priceMin, Double priceMax) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id"));
        Page<Product> products = this.productRepository.search(name,priceMin,priceMax,pageable);
        return products.map(productMapper::toProductResponse);
    }


}
