package com.svich.EventBookingSystem.exception.category;

import com.svich.EventBookingSystem.exception.ApiException;
import org.springframework.http.HttpStatus;

public class CategoryAlreadyExistsException extends ApiException {
    public CategoryAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
