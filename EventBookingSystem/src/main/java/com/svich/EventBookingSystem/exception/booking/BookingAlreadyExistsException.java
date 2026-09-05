package com.svich.EventBookingSystem.exception.booking;

import com.svich.EventBookingSystem.exception.ApiException;
import org.springframework.http.HttpStatus;

public class BookingAlreadyExistsException extends ApiException {
    public BookingAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
