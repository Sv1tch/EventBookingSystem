package com.svich.EventBookingSystem.service;

import com.svich.EventBookingSystem.dto.event.request.CreateEventRequest;
import com.svich.EventBookingSystem.dto.event.request.UpdateEventRequest;
import com.svich.EventBookingSystem.dto.event.response.EventResponse;
import com.svich.EventBookingSystem.staticData.EventStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface EventService {
    EventResponse create(CreateEventRequest request);
    EventResponse findById(Long eventId);
    EventResponse updateById(Long eventId, UpdateEventRequest request);
    void deleteById(Long eventId);

    EventResponse publishEvent(Long eventId);
    EventResponse cancelEvent(Long eventId);
    EventResponse completeEvent(Long eventId);

    Page<EventResponse> findEvents(
            String title,
            EventStatus status,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            Pageable pageable
    );
}
