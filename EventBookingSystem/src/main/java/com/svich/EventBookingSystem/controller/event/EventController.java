package com.svich.EventBookingSystem.controller.event;

import com.svich.EventBookingSystem.dto.event.request.CreateEventRequest;
import com.svich.EventBookingSystem.dto.event.request.UpdateEventRequest;
import com.svich.EventBookingSystem.dto.event.response.EventResponse;
import com.svich.EventBookingSystem.pagination.PageableFactory;
import com.svich.EventBookingSystem.service.EventService;
import com.svich.EventBookingSystem.staticData.EventStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final PageableFactory factory;

    @PostMapping("")
    public ResponseEntity<EventResponse> create(@Valid @RequestBody CreateEventRequest request){
        EventResponse response = eventService.create(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<Page<EventResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) EventStatus status
            ){

        Pageable pageable = factory.create(page, size, sortBy, direction);

        Page<EventResponse> events = (title == null || title.isBlank())
                ? eventService.findAll(pageable)
                : eventService.findByTitle(title, pageable);

        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> findById(@PathVariable Long eventId){
        EventResponse event = eventService.findById(eventId);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventResponse> updateById(@Valid @RequestBody UpdateEventRequest request, @PathVariable Long eventId){
        EventResponse event = eventService.updateById(eventId, request);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long eventId){
        eventService.deleteById(eventId);
    }

    @PostMapping("/{eventId}/publish")
    public ResponseEntity<EventResponse> publishEvent(@PathVariable Long eventId){
        EventResponse event = eventService.publishEvent(eventId);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping("/{eventId}/cancel")
    public ResponseEntity<EventResponse> cancelEvent(@PathVariable Long eventId){
        EventResponse event = eventService.cancelEvent(eventId);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping("/{eventId}/finish")
    public ResponseEntity<EventResponse> completeEvent(@PathVariable Long eventId){
        EventResponse event = eventService.completeEvent(eventId);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }
}
