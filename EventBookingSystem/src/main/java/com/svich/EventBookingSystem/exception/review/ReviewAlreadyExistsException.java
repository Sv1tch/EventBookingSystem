package com.svich.EventBookingSystem.exception.review;

import com.svich.EventBookingSystem.exception.ApiException;
import org.springframework.http.HttpStatus;

public class ReviewAlreadyExistsException extends ApiException {
    public ReviewAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
