package com.svich.EventBookingSystem.repository.booking;

import com.svich.EventBookingSystem.entity.booking.Booking;
import com.svich.EventBookingSystem.staticData.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> , JpaSpecificationExecutor<Booking> {

    @Query("""
            SELECT COALESCE(SUM(b.quantity), 0)
            FROM Booking b
            WHERE b.event.id = :eventId
            AND b.status IN :statuses
            """)
    Long getBookedQuantity(
            @Param("eventId") Long eventId,
            @Param("statuses") Collection<BookingStatus> statuses
    );
}
