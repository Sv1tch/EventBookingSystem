package com.svich.EventBookingSystem.service;

import com.svich.EventBookingSystem.dto.booking.request.CreateBookingRequest;
import com.svich.EventBookingSystem.dto.booking.request.UpdateBookingRequest;
import com.svich.EventBookingSystem.dto.booking.response.BookingResponse;
import com.svich.EventBookingSystem.staticData.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {

    BookingResponse create(CreateBookingRequest request);
    BookingResponse findById(Long bookingId);
    BookingResponse updateById(Long bookingId, UpdateBookingRequest request);
    void deleteById(Long bookingId);

    BookingResponse confirmBooking(Long bookingId);
    BookingResponse cancelBooking(Long bookingId);

    Page<BookingResponse> findBookings(
            BookingStatus status,
            Long eventId,
            Long customerId,
            Pageable pageable
    );

}
