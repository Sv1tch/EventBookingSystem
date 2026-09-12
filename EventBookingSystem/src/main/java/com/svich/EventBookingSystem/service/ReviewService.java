package com.svich.EventBookingSystem.service;

import com.svich.EventBookingSystem.dto.review.request.CreateReviewRequest;
import com.svich.EventBookingSystem.dto.review.request.UpdateReviewRequest;
import com.svich.EventBookingSystem.dto.review.response.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse create(CreateReviewRequest request);
    ReviewResponse findById(Long reviewId);
    List<ReviewResponse> findAll();
    ReviewResponse updateById(Long reviewId, UpdateReviewRequest request);
    void deleteById(Long reviewId);
}
