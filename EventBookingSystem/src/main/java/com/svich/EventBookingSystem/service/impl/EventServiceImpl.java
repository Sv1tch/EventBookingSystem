package com.svich.EventBookingSystem.service.impl;

import com.svich.EventBookingSystem.dto.event.request.CreateEventRequest;
import com.svich.EventBookingSystem.dto.event.request.UpdateEventRequest;
import com.svich.EventBookingSystem.dto.event.response.EventResponse;
import com.svich.EventBookingSystem.entity.category.Category;
import com.svich.EventBookingSystem.entity.event.Event;
import com.svich.EventBookingSystem.entity.venue.Venue;
import com.svich.EventBookingSystem.exception.category.CategoryNotFoundException;
import com.svich.EventBookingSystem.exception.event.EventNotFoundException;
import com.svich.EventBookingSystem.exception.venue.VenueNotFoundException;
import com.svich.EventBookingSystem.mapper.category.CategoryMapper;
import com.svich.EventBookingSystem.mapper.event.EventMapper;
import com.svich.EventBookingSystem.mapper.venue.VenueMapper;
import com.svich.EventBookingSystem.repository.category.CategoryRepository;
import com.svich.EventBookingSystem.repository.event.EventRepository;
import com.svich.EventBookingSystem.repository.venue.VenueRepository;
import com.svich.EventBookingSystem.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;
    private final VenueMapper venueMapper;

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final VenueRepository venueRepository;

    @Override
    public EventResponse create(CreateEventRequest request) {
        Category category = findCategoryById(request.getCategoryId());
        Venue venue = findVenueById(request.getVenueId());

        Event event = eventMapper.toEntity(request, category, venue);

        Event savedEvent = eventRepository.save(event);

        return eventMapper.toResponse(
                savedEvent,
                categoryMapper.toSummaryResponse(savedEvent.getCategory()),
                venueMapper.toSummaryResponse(savedEvent.getVenue())
        );
    }

    @Override
    public List<EventResponse> findAll() {
        List<Event> events = eventRepository.findAll();

        return events.stream()
                .map(event -> eventMapper.toResponse(
                        event,
                        categoryMapper.toSummaryResponse(event.getCategory()),
                        venueMapper.toSummaryResponse(event.getVenue())
                ))
                .toList();
    }

    @Override
    public EventResponse findById(Long eventId) {
        Event event = findEventById(eventId);

        return eventMapper.toResponse(
                event,
                categoryMapper.toSummaryResponse(event.getCategory()),
                venueMapper.toSummaryResponse(event.getVenue())
        );
    }

    @Override
    public EventResponse updateById(Long eventId, UpdateEventRequest request) {
        Event event = findEventById(eventId);
        Category category = findCategoryById(request.getCategoryId());
        Venue venue = findVenueById(request.getVenueId());

        eventMapper.updateEntity(request, event, category, venue);

        Event savedEvent = eventRepository.save(event);

        return eventMapper.toResponse(
                savedEvent,
                categoryMapper.toSummaryResponse(savedEvent.getCategory()),
                venueMapper.toSummaryResponse(savedEvent.getVenue())
        );
    }

    @Override
    public void deleteById(Long eventId) {
        Event event = findEventById(eventId);

        eventRepository.delete(event);
    }

    private Event findEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new EventNotFoundException(
                                "Event with id %d not found".formatted(eventId)
                        )
                );
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category with id %d not found".formatted(categoryId)
                        )
                );
    }

    private Venue findVenueById(Long venueId) {
        return venueRepository.findById(venueId)
                .orElseThrow(() ->
                        new VenueNotFoundException(
                                "Venue with id %d not found".formatted(venueId)
                        )
                );
    }

    @Override
    public EventResponse publishEvent(Long eventId){
        return null;
    }

    @Override
    public EventResponse cancelEvent(Long eventId){
        return null;
    }

    @Override
    public EventResponse finishEvent(Long eventId){
        return null;
    }
}