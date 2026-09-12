package com.svich.EventBookingSystem.dto.review.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateReviewRequest {

    @Min(value = 1, message = "Rating can't be less than 1")
    @Max(value = 5, message = "Rating can't be greater than 5")
    private int rating;

    @Size(
            max = 500,
            message = "Comment cannot exceed 500 characters"
    )
    private String comment;
}
