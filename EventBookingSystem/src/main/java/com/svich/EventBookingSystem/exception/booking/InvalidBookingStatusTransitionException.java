package com.svich.EventBookingSystem.exception.booking;

import com.svich.EventBookingSystem.exception.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidBookingStatusTransitionException extends ApiException {
    public InvalidBookingStatusTransitionException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
