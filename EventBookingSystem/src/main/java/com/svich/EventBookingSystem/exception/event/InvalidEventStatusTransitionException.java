package com.svich.EventBookingSystem.exception.event;

import com.svich.EventBookingSystem.exception.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidEventStatusTransitionException extends ApiException {
    public InvalidEventStatusTransitionException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
