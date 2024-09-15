package com.dinhhieu.FruitWebApp.mapper;

import com.dinhhieu.FruitWebApp.dto.request.CategoryReq.CategoryCreateRequest;
import com.dinhhieu.FruitWebApp.dto.request.CategoryReq.CategoryUpdateRequestDTO;
import com.dinhhieu.FruitWebApp.dto.response.CategoryRes.CategoryResponse;
import com.dinhhieu.FruitWebApp.model.Category;
import com.dinhhieu.FruitWebApp.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-09T09:48:54+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toCategory(CategoryCreateRequest categoryCreateRequest) {
        if ( categoryCreateRequest == null ) {
            return null;
        }

        Category category = new Category();

        category.setCategoryName( categoryCreateRequest.getCategoryName() );
        category.setUrlImage( categoryCreateRequest.getUrlImage() );
        category.setDescriptionImage( categoryCreateRequest.getDescriptionImage() );
        category.setType( categoryCreateRequest.getType() );

        return category;
    }

    @Override
    public CategoryResponse toCategoryResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse.CategoryResponseBuilder categoryResponse = CategoryResponse.builder();

        categoryResponse.id( category.getId() );
        categoryResponse.categoryName( category.getCategoryName() );
        categoryResponse.urlImage( category.getUrlImage() );
        categoryResponse.descriptionImage( category.getDescriptionImage() );
        categoryResponse.type( category.getType() );
        categoryResponse.createdAt( category.getCreatedAt() );
        categoryResponse.updatedAt( category.getUpdatedAt() );
        List<Product> list = category.getProduct();
        if ( list != null ) {
            categoryResponse.product( new ArrayList<Product>( list ) );
        }

        return categoryResponse.build();
    }

    @Override
    public void updateCategory(Category category, CategoryUpdateRequestDTO categoryUpdateRequestDTO) {
        if ( categoryUpdateRequestDTO == null ) {
            return;
        }

        category.setCategoryName( categoryUpdateRequestDTO.getCategoryName() );
        category.setUrlImage( categoryUpdateRequestDTO.getUrlImage() );
        category.setDescriptionImage( categoryUpdateRequestDTO.getDescriptionImage() );
    }
}
