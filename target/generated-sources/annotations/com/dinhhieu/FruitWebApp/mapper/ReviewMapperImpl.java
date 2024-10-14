package com.dinhhieu.FruitWebApp.mapper;

import com.dinhhieu.FruitWebApp.dto.response.CustomerRes.CustomerResponse;
import com.dinhhieu.FruitWebApp.dto.response.ProductRes.ProductResponse;
import com.dinhhieu.FruitWebApp.dto.response.ReviewRes.ReviewResponse;
import com.dinhhieu.FruitWebApp.model.Customer;
import com.dinhhieu.FruitWebApp.model.Product;
import com.dinhhieu.FruitWebApp.model.Review;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-14T14:07:42+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewResponse toReviewResponse(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewResponse.ReviewResponseBuilder reviewResponse = ReviewResponse.builder();

        reviewResponse.id( review.getId() );
        reviewResponse.comment( review.getComment() );
        reviewResponse.product( productToProductResponse( review.getProduct() ) );
        reviewResponse.customer( customerToCustomerResponse( review.getCustomer() ) );

        return reviewResponse.build();
    }

    protected ProductResponse productToProductResponse(Product product) {
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

    protected CustomerResponse customerToCustomerResponse(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerResponse.CustomerResponseBuilder customerResponse = CustomerResponse.builder();

        customerResponse.id( customer.getId() );
        customerResponse.firstName( customer.getFirstName() );
        customerResponse.lastName( customer.getLastName() );
        customerResponse.address( customer.getAddress() );
        customerResponse.email( customer.getEmail() );
        customerResponse.phoneNumber( customer.getPhoneNumber() );
        customerResponse.createdAt( customer.getCreatedAt() );
        customerResponse.updatedAt( customer.getUpdatedAt() );
        customerResponse.createdBy( customer.getCreatedBy() );
        customerResponse.updatedBy( customer.getUpdatedBy() );

        return customerResponse.build();
    }
}
