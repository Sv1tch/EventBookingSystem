package com.svich.EventBookingSystem.repository.event;

import com.svich.EventBookingSystem.entity.event.Event;
import com.svich.EventBookingSystem.staticData.EventStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class EventSpecification {

    public static Specification<Event> hasTitle(String title){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );
    }

    public static Specification<Event> hasStatus(EventStatus status){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("status"),
                        status
                );
    }

    public static Specification<Event> startsFrom(LocalDateTime startDateTime){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(
                        root.get("startDateTime"),
                        startDateTime
                );
    }

    public static Specification<Event> endsBefore(LocalDateTime endDateTime){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(
                        root.get("endDateTime"),
                        endDateTime
                );
    }

    public static Specification<Event> hasCategory(Long categoryId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("category").get("id"),
                        categoryId
                );
    }

    public static Specification<Event> hasVenue(Long venueId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("venue").get("id"),
                        venueId
                );
    }

    public static Specification<Event> hasOrganizer(String organizer){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("organizer")),
                        "%" + organizer.toLowerCase() + "%"
                );
    }
}
