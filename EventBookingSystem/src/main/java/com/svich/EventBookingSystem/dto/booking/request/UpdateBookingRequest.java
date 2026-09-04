package com.svich.EventBookingSystem.dto.booking.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookingRequest {

    @NotNull(message = "Quantity can't be null")
    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;
}
