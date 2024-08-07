package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.dto.ResponData;
import com.dinhhieu.FruitWebApp.dto.request.CategoryReq.CategoryCreateRequest;
import com.dinhhieu.FruitWebApp.dto.request.CategoryReq.CategoryUpdateRequestDTO;
import com.dinhhieu.FruitWebApp.dto.response.CategoryRes.CategoryResponse;
import com.dinhhieu.FruitWebApp.service.CategoryService;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@Api(value = "File Conversion Controller", description = "API for converting files")
public class CategoryController {
    private final CategoryService categoryService;
//    @PostMapping("")
//    public ResponData<?> createCategory(@Valid @RequestBody CategoryCreateRequest categoryCreateRequest){
//        return new ResponData<>(HttpStatus.CREATED.value(), "Create category", this.categoryService.savaCategory(categoryCreateRequest));
//    }

    @PostMapping("")
    public ResponData<?> createCategory(@Valid @ModelAttribute CategoryCreateRequest categoryCreateRequest, @RequestParam("file") MultipartFile file){
        CategoryResponse savedCategory = categoryService.savaCategory(categoryCreateRequest, file);
        return new ResponData<>(HttpStatus.CREATED.value(), "Create category", savedCategory);
    }

    @GetMapping("")
    public ResponData<?> getAllCategory(){
        return new ResponData<>(HttpStatus.OK.value(), "Get all category", this.categoryService.getAllCategory());
    }

    @GetMapping("/{id}")
    public ResponData<?> getCategoryById(@PathVariable("id") long id){
        return new ResponData<>(HttpStatus.OK.value(), "Get category by id: "+ id, this.categoryService.getCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponData<?> updateCategory(@PathVariable("id") long id, @RequestBody CategoryUpdateRequestDTO categoryUpdateRequestDTO){
        return new ResponData<>(HttpStatus.ACCEPTED.value(), "updated category with id = "+ id, this.categoryService.updateCategory(id, categoryUpdateRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponData<?> deleteCategoryById(@PathVariable long id){
        this.categoryService.deleteCategory(id);
        return new ResponData<>(HttpStatus.NO_CONTENT.value(), "deleted category id : "+id+" successfully");
    }

    @GetMapping("/search")
    public ResponData<?> searchCategory(@RequestParam(required = false) String categoryName,
                                 @RequestParam(required = false) String startCreateAt,
                                 @RequestParam(required = false) String endCreateAt,
                                 @RequestParam(required = false) String startUpdateAt,
                                 @RequestParam(required = false) String endUpdateAt
                                 ){
        try {
//            String startCreate = startCreateAt != null ? "2024-07-22";
//
//            String endCreate = endCreateAt != null ?  (new Date()).toString();
//
//            String startUpdate = startUpdateAt != null ? "2024-07-022";
//            String endUpdate = endUpdateAt != null ?  (new Date()).toString();
//
//            String searchCategoryName = categoryName != null ? categoryName.toLowerCase() : "";
            return new ResponData<>(HttpStatus.OK.value(), "get search category", this.categoryService.findCategory(categoryName,startCreateAt,endCreateAt,startUpdateAt, endUpdateAt));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
//    @GetMapping("/search")
//    ResponData<?> searchCategory(@RequestParam(required = false) String categoryName){
//        try {
//            return new ResponData<>(HttpStatus.OK.value(), "get search category", this.categoryService.findCategory(categoryName));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @GetMapping("/get-page-category")
    public ResponData<?> getPageCategory(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "sortField", defaultValue = "id") String sortField,
                                         @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection){
        return new ResponData<>(HttpStatus.OK.value(), "get page category", this.categoryService.findAllCategoryWithPage(pageNo,pageSize,sortField,sortDirection));
    }

    @GetMapping("/get-category-by-sort-multi")
    public ResponData<?> getAllCategoryBySortMulti(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                   @RequestParam(required = false) String... sorts){
        return new ResponData<>(HttpStatus.OK.value(), "get all category by sort multy", this.categoryService.getAllCategoryWithSortByMultipleColumns(pageNo,pageSize,sorts));
    }
}

