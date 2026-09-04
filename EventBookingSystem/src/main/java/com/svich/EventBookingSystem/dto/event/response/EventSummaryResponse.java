package com.svich.EventBookingSystem.dto.event.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventSummaryResponse {

    private Long id;

    private String title;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;
}
