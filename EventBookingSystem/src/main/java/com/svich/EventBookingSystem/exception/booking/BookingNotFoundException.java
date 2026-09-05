package com.svich.EventBookingSystem.exception.booking;

import com.svich.EventBookingSystem.exception.ApiException;
import org.springframework.http.HttpStatus;

public class BookingNotFoundException extends ApiException {
    public BookingNotFoundException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
