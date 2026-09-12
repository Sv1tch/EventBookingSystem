package com.svich.EventBookingSystem.dto.review.response;

import com.svich.EventBookingSystem.dto.customer.response.CustomerSummaryResponse;
import com.svich.EventBookingSystem.dto.event.response.EventSummaryResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewResponse {

    private Long id;

    private int rating;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private CustomerSummaryResponse customer;

    private EventSummaryResponse event;
}
