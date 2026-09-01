package com.svich.EventBookingSystem.service.impl;

import com.svich.EventBookingSystem.dto.event.request.CreateEventRequest;
import com.svich.EventBookingSystem.dto.event.request.UpdateEventRequest;
import com.svich.EventBookingSystem.dto.event.response.EventResponse;
import com.svich.EventBookingSystem.entity.category.Category;
import com.svich.EventBookingSystem.entity.event.Event;
import com.svich.EventBookingSystem.entity.venue.Venue;
import com.svich.EventBookingSystem.exception.InvalidEventStatusTransitionException;
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
import com.svich.EventBookingSystem.staticData.EventStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

        event.setStatus(EventStatus.DRAFT);

        Event savedEvent = eventRepository.save(event);

        return eventMapper.toResponse(
                savedEvent,
                categoryMapper.toSummaryResponse(savedEvent.getCategory()),
                venueMapper.toSummaryResponse(savedEvent.getVenue())
        );
    }

    private Page<Event> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable);
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

    private EventResponse changeStatus(Event event, EventStatus targetStatus){
        EventStatus currentStatus = event.getStatus();
        boolean canTransit = false;

        if(targetStatus.equals(EventStatus.PUBLISHED)){
            canTransit = currentStatus.equals(EventStatus.DRAFT);
        }
        else if(targetStatus.equals(EventStatus.CANCELLED)) {
            canTransit = (currentStatus.equals(EventStatus.DRAFT) || currentStatus.equals(EventStatus.PUBLISHED));
        }
        else if(targetStatus.equals(EventStatus.COMPLETED)){
            canTransit = currentStatus.equals(EventStatus.PUBLISHED);
        }

        if(canTransit){
            event.setStatus(targetStatus);

            Event savedEvent = eventRepository.save(event);

            return eventMapper.toResponse(
                    savedEvent,
                    categoryMapper.toSummaryResponse(savedEvent.getCategory()),
                    venueMapper.toSummaryResponse(savedEvent.getVenue())
            );
        }

        throw new InvalidEventStatusTransitionException("Event with id " + event.getId() + " cannot be changed from " + currentStatus + " to " + targetStatus);
    }

    // STATUS CHANGING
    @Override
    public EventResponse publishEvent(Long eventId){
        Event event = findEventById(eventId);

        return changeStatus(event, EventStatus.PUBLISHED);
    }

    @Override
    public EventResponse cancelEvent(Long eventId){
        Event event = findEventById(eventId);

        return changeStatus(event, EventStatus.CANCELLED);
    }

    @Override
    public EventResponse completeEvent(Long eventId){
        Event event = findEventById(eventId);

        return changeStatus(event, EventStatus.COMPLETED);
    }

    // SEARCH FUTURES
    @Override
    public Page<EventResponse> findEvents(
            String title,
            EventStatus status,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            Pageable pageable
    ){
        Page<Event> events = (title == null || title.isBlank())
                ? (status == null
                    ? findAll(pageable)
                    : findByStatus(status, pageable)
                    )
                : (status != null
                    ? findByTitleAndStatus(title, status, pageable)
                    : findByTitle(title, pageable));

        return events.map(event ->
                eventMapper.toResponse(event, categoryMapper.toSummaryResponse(event.getCategory()), venueMapper.toSummaryResponse(event.getVenue())));
    }

    private Page<Event> findByTitle(String title, Pageable pageable){
        return eventRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    private Page<Event> findByStatus(EventStatus status, Pageable pageable){
        return eventRepository.findByStatus(status, pageable);
    }

    private Page<Event> findByTitleAndStatus(String title, EventStatus status, Pageable pageable){
        return eventRepository.findByTitleContainingIgnoreCaseAndStatus(title, status, pageable);
    }

    private Page<Event> findByStartDateTimeAfter(LocalDateTime startDateTime, Pageable pageable){
        return eventRepository.findByStartDateTimeAfter(startDateTime, pageable);
    }

    private Page<Event> findByEndDateTimeBefore(LocalDateTime endDateTime, Pageable pageable){
        return eventRepository.findByEndDateTimeBefore(endDateTime, pageable);
    }

    private Page<Event> findByStartDateTimeAndEndDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable){
        return eventRepository.findByStartDateTimeAndEndDateTimeBetween(startDateTime, endDateTime, pageable);
    }
}