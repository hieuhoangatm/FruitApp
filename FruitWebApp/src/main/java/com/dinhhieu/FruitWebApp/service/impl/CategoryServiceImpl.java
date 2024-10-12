package com.dinhhieu.FruitWebApp.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dinhhieu.FruitWebApp.dto.request.CategoryReq.CategoryCreateRequest;
import com.dinhhieu.FruitWebApp.dto.request.CategoryReq.CategoryUpdateRequestDTO;
import com.dinhhieu.FruitWebApp.dto.response.CategoryRes.CategoryResponse;
import com.dinhhieu.FruitWebApp.exception.AppException;
import com.dinhhieu.FruitWebApp.exception.ErrorCode;
import com.dinhhieu.FruitWebApp.mapper.CategoryMapper;
import com.dinhhieu.FruitWebApp.model.Category;
import com.dinhhieu.FruitWebApp.repository.CategoryRepository;
import com.dinhhieu.FruitWebApp.service.CategoryService;
import com.dinhhieu.FruitWebApp.util.UploadImage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class
CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final UploadImage uploadImage;

    @Override
    public CategoryResponse savaCategory(CategoryCreateRequest categoryCreateRequest, MultipartFile multipartFile) {
        if(this.categoryRepository.existsByCategoryName(categoryCreateRequest.getCategoryName())){
            throw new AppException(ErrorCode.CATEGORY_NAME_EXISTED);
        }
        Category category = this.categoryMapper.toCategory(categoryCreateRequest);

        try {
            String fileUrl = uploadImage.uploadFileImage(multipartFile);
            category.setUrlImage(fileUrl);
        } catch (IOException e) {
//            throw new RuntimeException("Failed to upload image", e);
            throw new AppException(ErrorCode.FALE_TO_UPLOAD_FILE);
        }

        this.categoryRepository.save(category);
        return this.categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(long id, CategoryUpdateRequestDTO categoryRequest) {
//        Category category = getCategoryById(categoryRequest.getId());
//        category.setCategoryName(categoryRequest.getCategoryName());
//        category.setUrlImage(category.getUrlImage());
//        category.setDescriptionImage(category.getDescriptionImage());

//        Category category = categoryMapper.toCategory(categoryRequest);
        Category category = categoryRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        categoryMapper.updateCategory(category,categoryRequest);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(long id) {
        if(!this.categoryRepository.existsById(id)){
            throw  new RuntimeException("Not found id category to delete");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponse getCategoryById(long categoryId) {
//        return this.categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Category not found"));
//        return this.categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("Category not foundd"));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("category not found"));
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        return this.categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();
    }

    @Override
    public Category createCategory(Category category) {
        return null;
    }

    @Override
    public List<Category> findCategory(String categoryName, String startCreatedAt, String endCreateAt, String startUpdateAt, String endUpdateAt) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date startCreate = startCreatedAt != null ? sdf.parse(startCreatedAt) : sdf.parse("2024-07-22");
        Date endCreate = endCreateAt != null ? sdf.parse(endCreateAt) : new Date();

        Date startUpdate = startUpdateAt != null ? sdf.parse(startUpdateAt) : sdf.parse("2024-07-022");
        Date endUpdate = endUpdateAt != null ? sdf.parse(endUpdateAt) : new Date();

        String searchCategoryName = categoryName != null ? categoryName.toLowerCase() : "";

        return this.categoryRepository.searchCategory(searchCategoryName, startCreate, endCreate, startUpdate, endUpdate);
    }

    @Override
    public Page<Category> findAllCategoryWithPage(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        return this.categoryRepository.findAll(pageable);
    }

    @Override
    public Page<Category> getAllCategoryWithSortByMultipleColumns(int pageNo, int pageSize, String... sorts) {
        List<Sort.Order> orders = new ArrayList<>();
        if(sorts!=null){
            for(String sortBy : sorts){
                Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
                Matcher matcher = pattern.matcher(sortBy);
                if (matcher.find()){
                    if (matcher.group(3).equalsIgnoreCase("asc")) {
                        orders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                    } else {
                        orders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                    }
                }
            }
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(orders));
        return categoryRepository.findAll(pageable);
    }



//    @Override
//    public List<Category> findCategory(String categoryName) throws ParseException {
//        return this.categoryRepository.searchCategory(categoryName.toLowerCase());
//    }


}
