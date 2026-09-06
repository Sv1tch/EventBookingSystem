package com.svich.EventBookingSystem.exception.event;

import com.svich.EventBookingSystem.exception.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidEventStatusException extends ApiException {
    public InvalidEventStatusException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
