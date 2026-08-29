package com.svich.EventBookingSystem.repository.event;

import com.svich.EventBookingSystem.entity.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
