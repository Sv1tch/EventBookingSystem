package com.svich.EventBookingSystem.service;

import com.svich.EventBookingSystem.dto.event.request.CreateEventRequest;
import com.svich.EventBookingSystem.dto.event.request.UpdateEventRequest;
import com.svich.EventBookingSystem.dto.event.response.EventResponse;

import java.util.List;

public interface EventService {
    EventResponse create(CreateEventRequest request);
    List<EventResponse> findAll();
    EventResponse findById(Long eventId);
    EventResponse updateById(Long eventId, UpdateEventRequest request);
    void deleteById(Long eventId);
}
