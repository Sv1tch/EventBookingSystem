package com.svich.EventBookingSystem.dto.venue.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VenueResponse {

    private Long id;

    private String name;

    private String city;

    private String address;

    private int capacity;
}
