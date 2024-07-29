package com.dinhhieu.FruitWebApp.mapper;

import com.dinhhieu.FruitWebApp.dto.request.ProductReq.ProductCreateRequest;
import com.dinhhieu.FruitWebApp.dto.request.ProductReq.ProductUpdateRequest;
import com.dinhhieu.FruitWebApp.dto.response.ProductRes.ProductResponse;
import com.dinhhieu.FruitWebApp.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel ="Spring")
public interface ProductMapper {
    Product toProduct(ProductCreateRequest productCreateRequest);

    ProductResponse toProductResponse(Product product);
    void updateProduc(@MappingTarget Product product, ProductUpdateRequest productUpdateRequest);
}
