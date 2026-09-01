package com.svich.EventBookingSystem.repository.event;

import com.svich.EventBookingSystem.entity.event.Event;
import com.svich.EventBookingSystem.staticData.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;


@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByTitleContainingIgnoreCase(
            String title,
            Pageable pageable
    );

    Page<Event> findByStatus(
            EventStatus status,
            Pageable pageable
    );

    Page<Event> findByTitleContainingIgnoreCaseAndStatus(
            String title,
            EventStatus status,
            Pageable pageable
    );

    Page<Event> findByStartDateTimeAfter(
            LocalDateTime startDateTime,
            Pageable pageable
    );

    Page<Event> findByEndDateTimeBefore(
            LocalDateTime endDateTime,
            Pageable pageable
    );

    Page<Event> findByStartDateTimeAndEndDateTimeBetween(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            Pageable pageable
    );
}
