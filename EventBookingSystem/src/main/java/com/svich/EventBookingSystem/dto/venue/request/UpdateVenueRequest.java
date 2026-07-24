package com.svich.EventBookingSystem.dto.venue.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVenueRequest {

    @NotBlank(message = "Name can't be blank")
    @Size(
            min = 1,
            max = 150,
            message = "Venue name must be between 1 and 150 characters"
    )
    private String name;

    @NotBlank(message = "City can't be blank")
    @Size(
            min = 1,
            max = 100,
            message = "Venue city location must be between 1 and 100 characters"
    )
    private String city;

    @NotBlank(message = "Address can't be blank")
    @Size(
            min = 1,
            max = 255,
            message = "Venue address must be between 1 and 255 characters"
    )
    private String address;

    @NotNull(message = "Capacity can't be null")
    private int capacity;
}
