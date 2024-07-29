package com.dinhhieu.FruitWebApp.service;

import com.dinhhieu.FruitWebApp.dto.request.CategoryReq.CategoryCreateRequest;
import com.dinhhieu.FruitWebApp.dto.request.CategoryReq.CategoryUpdateRequestDTO;
import com.dinhhieu.FruitWebApp.dto.response.CategoryRes.CategoryResponse;
import com.dinhhieu.FruitWebApp.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface CategoryService {
    CategoryResponse savaCategory(CategoryCreateRequest categoryCreateRequest, MultipartFile multipartFile);

    CategoryResponse updateCategory(long id, CategoryUpdateRequestDTO categoryUpdateRequestDTO);

    void deleteCategory(long id);

    CategoryResponse getCategoryById(long categoryid);

    List<CategoryResponse> getAllCategory();

    Category createCategory(Category category);

    List<Category> findCategory(String categoryName, String startCreateAt, String endCreateAt, String startUpdateAt, String endUpdateAt) throws ParseException;
//    List<Category> findCategory(String categoryName) throws ParseException;

    Page<Category> findAllCategoryWithPage(int pageNo, int pageSize, String  sortField, String sortDirection);

    // String... sorts = List<String> sorts
    Page<Category> getAllCategoryWithSortByMultipleColumns(int pageNo, int pageSize, String... sorts);

}
