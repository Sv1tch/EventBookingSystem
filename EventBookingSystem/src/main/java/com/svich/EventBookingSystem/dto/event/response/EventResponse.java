package com.svich.EventBookingSystem.dto.event.response;

import com.svich.EventBookingSystem.dto.category.response.CategorySummaryResponse;
import com.svich.EventBookingSystem.dto.venue.response.VenueSummaryResponse;
import com.svich.EventBookingSystem.staticData.EventStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventResponse {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private BigDecimal price;

    private int capacity;

    private EventStatus status;

    private CategorySummaryResponse category;

    private VenueSummaryResponse venue;
}
