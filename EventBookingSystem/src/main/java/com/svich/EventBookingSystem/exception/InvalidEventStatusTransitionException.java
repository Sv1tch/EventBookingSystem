package com.svich.EventBookingSystem.exception;

import org.springframework.http.HttpStatus;

public class InvalidEventStatusTransitionException extends ApiException {
    public InvalidEventStatusTransitionException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
