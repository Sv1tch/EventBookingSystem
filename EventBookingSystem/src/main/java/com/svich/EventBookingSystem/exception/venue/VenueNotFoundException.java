package com.svich.EventBookingSystem.exception.venue;

import com.svich.EventBookingSystem.exception.ApiException;
import org.springframework.http.HttpStatus;

public class VenueNotFoundException extends ApiException {
    public VenueNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
