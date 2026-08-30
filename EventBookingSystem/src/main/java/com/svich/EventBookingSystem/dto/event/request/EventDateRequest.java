package com.svich.EventBookingSystem.dto.event.request;

import java.time.LocalDateTime;

public interface EventDateRequest {

    LocalDateTime getStartDateTime();

    LocalDateTime getEndDateTime();
}
