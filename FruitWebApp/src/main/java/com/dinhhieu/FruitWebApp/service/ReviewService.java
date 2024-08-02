package com.dinhhieu.FruitWebApp.service;

import com.dinhhieu.FruitWebApp.dto.response.ReviewRes.ReviewResponse;
import com.dinhhieu.FruitWebApp.mapper.ReviewMapper;
import com.dinhhieu.FruitWebApp.model.Customer;
import com.dinhhieu.FruitWebApp.model.Product;
import com.dinhhieu.FruitWebApp.model.Review;
import com.dinhhieu.FruitWebApp.repository.CustomerRepository;
import com.dinhhieu.FruitWebApp.repository.ProductRepository;
import com.dinhhieu.FruitWebApp.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    private final ReviewMapper reviewMapper;

    public ReviewResponse addReview(long productId, String comment){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String email = securityContext.getAuthentication().getName();

        Customer customer = this.customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Not found customer authenticated"));

        Product product =  this.productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Not found product with id "+ productId));
        Review review = Review.builder().
                comment(comment).customer(customer).
                product(product).
                build();

        return reviewMapper.toReviewResponse(this.reviewRepository.save(review));
    }

    public List<ReviewResponse> getAllReview(){
        List<Review> reviews = this.reviewRepository.findAll();
        return reviews.stream().map(this.reviewMapper::toReviewResponse).toList();
    }

    public void deleteReview(long reviewId){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String email = securityContext.getAuthentication().getName();
        Customer customer = this.customerRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Not found customer authenticate with service deleted"));

        Review review = this.reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Not found review with id "+ reviewId));

        if (customer.getId()!= review.getCustomer().getId()){
            throw new RuntimeException("You must have permission to delete this review");
        }

        this.reviewRepository.deleteById(reviewId);
    }

    public ReviewResponse getReviewById(long reviewId){
        Review review = this.reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("not found review with id "+reviewId));
        return this.reviewMapper.toReviewResponse(review);
    }

    public ReviewResponse updateReview(long reviewId, String comment){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String email = securityContext.getAuthentication().getName();
        Customer customer = this.customerRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Not found customer authenticated with service update"));

        Review review = this.reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Not found review with id "+ reviewId));

        if (customer.getId()!= review.getCustomer().getId()){
            throw new RuntimeException("You must  have permission to update this review");
        }

        review.setComment(comment);

        this.reviewRepository.save(review);
        return this.reviewMapper.toReviewResponse(review);

    }
}
