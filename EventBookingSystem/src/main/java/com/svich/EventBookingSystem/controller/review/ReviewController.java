package com.svich.EventBookingSystem.controller.review;

import com.svich.EventBookingSystem.dto.review.request.CreateReviewRequest;
import com.svich.EventBookingSystem.dto.review.request.UpdateReviewRequest;
import com.svich.EventBookingSystem.dto.review.response.ReviewResponse;
import com.svich.EventBookingSystem.pagination.PageableFactory;
import com.svich.EventBookingSystem.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final PageableFactory factory;

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id",
            "rating",
            "createdAt",
            "updatedAt"
    );

    @PostMapping("")
    public ResponseEntity<ReviewResponse> create(@Valid @RequestBody CreateReviewRequest request){
        ReviewResponse response = reviewService.create(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> findById(@PathVariable Long reviewId){
        ReviewResponse response = reviewService.findById(reviewId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<ReviewResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long eventId
    ){
        Pageable pageable = factory.create(page, size, sortBy, direction, ALLOWED_SORT_FIELDS);

        Page<ReviewResponse> responses = reviewService.findReviews(rating, customerId, eventId, pageable);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("{reviewId}")
    public ResponseEntity<ReviewResponse> updateById(@PathVariable Long reviewId, @Valid @RequestBody UpdateReviewRequest request){
        ReviewResponse response = reviewService.updateById(reviewId, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long reviewId){
        reviewService.deleteById(reviewId);
    }
}
