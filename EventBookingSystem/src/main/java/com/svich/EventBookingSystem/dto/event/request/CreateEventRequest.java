package com.svich.EventBookingSystem.dto.event.request;

import com.svich.EventBookingSystem.dto.event.validation.ValidEventDates;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ValidEventDates
public class CreateEventRequest implements EventDateRequest {

    @NotBlank(message = "Title can't be blank")
    @Size(
            min = 1,
            max = 150,
            message = "Event title must be between 1 and 150 characters"
    )
    private String title;

    @Size(
            max = 500,
            message = "Event description can't exceed 500 characters"
    )
    private String description;

    @NotNull(message = "Start date and time can't be null")
    @Future(message = "Start date and time must be in the future")
    private LocalDateTime startDateTime;

    @NotNull(message = "End date and time can't be null")
    @Future(message = "End date and time must be in the future")
    private LocalDateTime endDateTime;

    @NotNull(message = "Price can't be null")
    @PositiveOrZero(message = "Price can't be negative")
    private BigDecimal price;

    @Positive(message = "Capacity must be greater than zero")
    private int capacity;

    @NotNull(message = "Category id can't be null")
    private Long categoryId;

    @NotNull(message = "Venue id can't be null")
    private Long venueId;
}
