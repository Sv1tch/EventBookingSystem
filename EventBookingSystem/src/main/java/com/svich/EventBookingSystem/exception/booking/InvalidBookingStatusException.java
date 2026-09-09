package com.svich.EventBookingSystem.exception.booking;

import com.svich.EventBookingSystem.exception.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidBookingStatusException extends ApiException {
    public InvalidBookingStatusException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
