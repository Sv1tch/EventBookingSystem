package com.svich.EventBookingSystem.exception.pagination;

public class InvalidPaginationParameterException extends IllegalArgumentException {
    public InvalidPaginationParameterException(String message) {
        super(message);
    }
}
