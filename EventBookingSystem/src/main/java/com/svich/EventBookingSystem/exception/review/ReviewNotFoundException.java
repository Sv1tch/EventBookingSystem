package com.svich.EventBookingSystem.exception.review;

import com.svich.EventBookingSystem.exception.ApiException;
import org.springframework.http.HttpStatus;

public class ReviewNotFoundException extends ApiException {
    public ReviewNotFoundException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
