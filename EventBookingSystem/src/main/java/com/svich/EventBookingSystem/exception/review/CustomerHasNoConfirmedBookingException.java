package com.svich.EventBookingSystem.exception.review;

import com.svich.EventBookingSystem.exception.ApiException;
import org.springframework.http.HttpStatus;

public class CustomerHasNoConfirmedBookingException extends ApiException {
    public CustomerHasNoConfirmedBookingException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
