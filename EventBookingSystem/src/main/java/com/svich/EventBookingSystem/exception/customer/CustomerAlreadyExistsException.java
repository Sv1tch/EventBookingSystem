package com.svich.EventBookingSystem.exception.customer;

import com.svich.EventBookingSystem.exception.ApiException;
import org.springframework.http.HttpStatus;

public class CustomerAlreadyExistsException extends ApiException {
    public CustomerAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
