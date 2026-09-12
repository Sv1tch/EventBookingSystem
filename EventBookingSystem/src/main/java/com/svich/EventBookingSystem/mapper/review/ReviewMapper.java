package com.svich.EventBookingSystem.mapper.review;

import com.svich.EventBookingSystem.dto.customer.response.CustomerSummaryResponse;
import com.svich.EventBookingSystem.dto.event.response.EventSummaryResponse;
import com.svich.EventBookingSystem.dto.review.request.CreateReviewRequest;
import com.svich.EventBookingSystem.dto.review.request.UpdateReviewRequest;
import com.svich.EventBookingSystem.dto.review.response.ReviewResponse;
import com.svich.EventBookingSystem.entity.customer.Customer;
import com.svich.EventBookingSystem.entity.event.Event;
import com.svich.EventBookingSystem.entity.review.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review toEntity(
            CreateReviewRequest request,
            Customer customer,
            Event event
    ){
        Review review = new Review();

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCustomer(customer);
        review.setEvent(event);

        return review;
    }

    public ReviewResponse toResponse(
            Review review,
            CustomerSummaryResponse customer,
            EventSummaryResponse event
    ){
        ReviewResponse response = new ReviewResponse();

        response.setId(review.getId());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setCreatedAt(review.getCreatedAt());
        response.setUpdatedAt(review.getUpdatedAt());
        response.setCustomer(customer);
        response.setEvent(event);

        return response;
    }

    public void updateEntity(
            UpdateReviewRequest request,
            Review review
    ){
        review.setRating(request.getRating());
        review.setComment(request.getComment());
    }
}
