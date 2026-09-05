package com.svich.EventBookingSystem.exception.booking;

import com.svich.EventBookingSystem.exception.ApiException;
import org.springframework.http.HttpStatus;

public class NotEnoughSeatsException extends ApiException {
    public NotEnoughSeatsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
