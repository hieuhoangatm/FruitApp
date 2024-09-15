package com.dinhhieu.FruitWebApp.mapper;

import com.dinhhieu.FruitWebApp.dto.request.ProductReq.ProductCreateRequest;
import com.dinhhieu.FruitWebApp.dto.request.ProductReq.ProductUpdateRequest;
import com.dinhhieu.FruitWebApp.dto.response.ProductRes.ProductResponse;
import com.dinhhieu.FruitWebApp.model.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-27T11:23:43+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toProduct(ProductCreateRequest productCreateRequest) {
        if ( productCreateRequest == null ) {
            return null;
        }

        Product product = new Product();

        product.setProductName( productCreateRequest.getProductName() );
        product.setDescription( productCreateRequest.getDescription() );
        product.setQuantity( productCreateRequest.getQuantity() );
        product.setPrice( productCreateRequest.getPrice() );
        product.setCategory( productCreateRequest.getCategory() );

        return product;
    }

    @Override
    public ProductResponse toProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse productResponse = new ProductResponse();

        productResponse.setId( product.getId() );
        productResponse.setProductName( product.getProductName() );
        productResponse.setDescription( product.getDescription() );
        productResponse.setQuantity( product.getQuantity() );
        productResponse.setPrice( product.getPrice() );
        productResponse.setCategory( product.getCategory() );

        return productResponse;
    }

    @Override
    public void updateProduct(Product product, ProductUpdateRequest productUpdateRequest) {
        if ( productUpdateRequest == null ) {
            return;
        }
    }
}
