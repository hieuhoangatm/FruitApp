package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.dto.ResponData;
import com.dinhhieu.FruitWebApp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{id}")
    public ResponData<?> addReview(@PathVariable("id") long id, @RequestParam(required = false) String comment){
        return new ResponData<>(HttpStatus.OK.value(), "get add review product", this.reviewService.addReview(id,comment));
    }

    @GetMapping("")
    public ResponData<?> getAllReview(){
        return new ResponData<>(HttpStatus.OK.value(), "get all review",this.reviewService.getAllReview());
    }

    @DeleteMapping("/{id}")
    public ResponData<?> deleteReview(@PathVariable("id") long id){
        this.reviewService.deleteReview(id);
        return new ResponData<>(HttpStatus.NO_CONTENT.value(), "deleted review successfully");
    }

    @GetMapping("/{id}")
    public ResponData<?> getReviewWithId(@PathVariable("id") long id){
        return new ResponData<>(HttpStatus.OK.value(), "get review with id "+id, this.reviewService.getReviewById(id));
    }

    @PutMapping("/{id}")
    public ResponData<?> updateReview(@PathVariable("id") long id, @RequestParam(required = false) String comment){
        return new ResponData<>(HttpStatus.OK.value(), "update review with "+id, this.reviewService.updateReview(id,comment));
    }
}
