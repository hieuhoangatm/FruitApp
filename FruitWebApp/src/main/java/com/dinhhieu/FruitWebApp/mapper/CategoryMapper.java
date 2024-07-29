package com.dinhhieu.FruitWebApp.mapper;

import com.dinhhieu.FruitWebApp.dto.request.CategoryReq.CategoryCreateRequest;
import com.dinhhieu.FruitWebApp.dto.request.CategoryReq.CategoryUpdateRequestDTO;
import com.dinhhieu.FruitWebApp.dto.response.CategoryRes.CategoryResponse;
import com.dinhhieu.FruitWebApp.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryCreateRequest categoryCreateRequest);

    CategoryResponse toCategoryResponse(Category category);

    void updateCategory(@MappingTarget Category category, CategoryUpdateRequestDTO categoryUpdateRequestDTO);

}
