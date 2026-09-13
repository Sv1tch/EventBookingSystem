package com.svich.EventBookingSystem.service;

import com.svich.EventBookingSystem.dto.review.request.CreateReviewRequest;
import com.svich.EventBookingSystem.dto.review.request.UpdateReviewRequest;
import com.svich.EventBookingSystem.dto.review.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    ReviewResponse create(CreateReviewRequest request);
    ReviewResponse findById(Long reviewId);
    ReviewResponse updateById(Long reviewId, UpdateReviewRequest request);
    void deleteById(Long reviewId);

    Page<ReviewResponse> findReviews(
            int rating,
            Long customerId,
            Long eventId,
            Pageable pageable
    );
}
