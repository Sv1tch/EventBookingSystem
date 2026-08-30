package com.svich.EventBookingSystem.exception;

public class InvalidEventStatusTransitionException extends RuntimeException {
    public InvalidEventStatusTransitionException(String message) {
        super(message);
    }
}
