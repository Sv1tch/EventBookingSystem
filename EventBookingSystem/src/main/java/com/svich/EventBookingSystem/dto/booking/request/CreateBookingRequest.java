package com.svich.EventBookingSystem.dto.booking.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookingRequest {

    @NotNull(message = "Event id can't be null")
    private Long eventId;

    @NotNull(message = "Customer id can't be null")
    private Long customerId;

    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity;
}
