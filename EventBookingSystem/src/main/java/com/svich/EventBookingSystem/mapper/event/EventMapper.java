package com.svich.EventBookingSystem.mapper.event;

import com.svich.EventBookingSystem.dto.category.response.CategorySummaryResponse;
import com.svich.EventBookingSystem.dto.event.request.CreateEventRequest;
import com.svich.EventBookingSystem.dto.event.request.UpdateEventRequest;
import com.svich.EventBookingSystem.dto.event.response.EventResponse;
import com.svich.EventBookingSystem.dto.venue.response.VenueSummaryResponse;
import com.svich.EventBookingSystem.entity.category.Category;
import com.svich.EventBookingSystem.entity.event.Event;
import com.svich.EventBookingSystem.entity.venue.Venue;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public Event toEntity(
            CreateEventRequest request,
            Category category,
            Venue venue
    ){
        Event event = new Event();

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setStartDateTime(request.getStartDateTime());
        event.setEndDateTime(request.getEndDateTime());
        event.setPrice(request.getPrice());
        event.setCapacity(request.getCapacity());
        event.setCategory(category);
        event.setVenue(venue);

        return event;
    }

    public EventResponse toResponse(
            Event event,
            CategorySummaryResponse categorySummaryResponse,
            VenueSummaryResponse venueSummaryResponse
    ){
        EventResponse response = new EventResponse();

        response.setId(event.getId());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setStartDateTime(event.getStartDateTime());
        response.setEndDateTime(event.getEndDateTime());
        response.setPrice(event.getPrice());
        response.setCapacity(event.getCapacity());
        response.setStatus(event.getStatus());
        response.setCategory(categorySummaryResponse);
        response.setVenue(venueSummaryResponse);

        return response;
    }

    public void updateEntity(
            UpdateEventRequest request,
            Event event,
            Category category,
            Venue venue
    ){
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setStartDateTime(request.getStartDateTime());
        event.setEndDateTime(request.getEndDateTime());
        event.setPrice(request.getPrice());
        event.setCapacity(request.getCapacity());
        event.setCategory(category);
        event.setVenue(venue);
    }
}
