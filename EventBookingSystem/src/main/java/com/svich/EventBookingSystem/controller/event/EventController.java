package com.svich.EventBookingSystem.controller.event;

import com.svich.EventBookingSystem.dto.event.request.CreateEventRequest;
import com.svich.EventBookingSystem.dto.event.request.UpdateEventRequest;
import com.svich.EventBookingSystem.dto.event.response.EventResponse;
import com.svich.EventBookingSystem.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("")
    public ResponseEntity<EventResponse> create(@Valid @RequestBody CreateEventRequest request){
        EventResponse response = eventService.create(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<EventResponse>> findAll(){
        List<EventResponse> events = eventService.findAll();

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
    public ResponseEntity<EventResponse> finishEvent(@PathVariable Long eventId){
        EventResponse event = eventService.finishEvent(eventId);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }
}
