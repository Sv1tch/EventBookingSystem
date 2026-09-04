package com.svich.EventBookingSystem.exception.customer;

import com.svich.EventBookingSystem.exception.ApiException;
import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends ApiException {
    public CustomerNotFoundException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
