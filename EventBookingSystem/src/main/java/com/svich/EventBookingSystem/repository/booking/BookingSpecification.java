package com.svich.EventBookingSystem.repository.booking;

import com.svich.EventBookingSystem.entity.booking.Booking;
import com.svich.EventBookingSystem.staticData.BookingStatus;
import org.springframework.data.jpa.domain.Specification;

public class BookingSpecification {

    public static Specification<Booking> hasStatus(BookingStatus status){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("status"),
                        status
                );
    }

    public static Specification<Booking> hasEventId(Long eventId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("event").get("id"),
                        eventId
                );
    }

    public static Specification<Booking> hasCustomerId(Long customerId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("customer").get("id"),
                        customerId
                );
    }
}
