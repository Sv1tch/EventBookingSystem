package com.svich.EventBookingSystem.exception.venue;

import com.svich.EventBookingSystem.exception.ApiException;
import org.springframework.http.HttpStatus;

public class VenueAlreadyExistsException extends ApiException {
    public VenueAlreadyExistsException(String message){
        super(message, HttpStatus.CONFLICT);
    }
}
