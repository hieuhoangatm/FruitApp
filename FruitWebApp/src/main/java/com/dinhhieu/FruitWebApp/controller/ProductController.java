package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.dto.ResponData;
import com.dinhhieu.FruitWebApp.model.Product;
import com.dinhhieu.FruitWebApp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("")
    ResponData<?> createProduct(@RequestBody Product product){
        return new ResponData<>(HttpStatus.CREATED.value(), "Create new product",this.productService.saveProduct(product));
    }

    @GetMapping("")
    ResponData<?> getAllProduct(){
        return new ResponData<>(HttpStatus.OK.value(), "get all product", this.productService.getAllProduct());
    }

    @GetMapping("/{id}")
    ResponData<?> getProductById(@PathVariable("id") long id){
        return new ResponData<>(HttpStatus.OK.value(), "get product with id = "+ id,this.productService.getProductById(id));
    }

    @PutMapping("")
    ResponData<?> updateProduct(@RequestBody Product product){
        this.productService.updateProduct(product);
        return new ResponData<>(HttpStatus.ACCEPTED.value(), "update product with id: "+product.getId());
    }

    @DeleteMapping("/{id}")
    ResponData<?> deleteProduct(@PathVariable("id") long id){
        this.productService.deleteProduct(id);
        return new ResponData<>(HttpStatus.NO_CONTENT.value(), "Delete product with id "+id);
    }

    @GetMapping("/get-all-product-with-page")
    ResponData<?> getAllProductWithPage(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return new ResponData<>(HttpStatus.OK.value(), "get all product with page", this.productService.getAllProductWithPage(pageNo,pageSize));
    }

    @GetMapping("/getPage-product-sortBy")
    ResponData<?> getProductSortBy(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                   @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                   @RequestParam(required = false) String... sorts){
        return new ResponData<>(HttpStatus.OK.value(), "get product page with sort", this.productService.getAllProductWithBySortMultiColumns(pageNo,pageSize,sorts));
    }

    @GetMapping("/search")
    ResponData<?> findProduct(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) Double priceMin,
                              @RequestParam(required = false) Double priceMax){

        return new ResponData<>(HttpStatus.OK.value(), "get search product ", this.productService.searchProduct(pageNo,pageSize,name,priceMin,priceMax));
    }
}
