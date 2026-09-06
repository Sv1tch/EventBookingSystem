package com.svich.EventBookingSystem.service;

import com.svich.EventBookingSystem.dto.booking.request.CreateBookingRequest;
import com.svich.EventBookingSystem.dto.booking.request.UpdateBookingRequest;
import com.svich.EventBookingSystem.dto.booking.response.BookingResponse;

import java.util.List;

public interface BookingService {

    BookingResponse create(CreateBookingRequest request);
    BookingResponse findById(Long bookingId);
    List<BookingResponse> findAll();
    BookingResponse updateById(Long bookingId, UpdateBookingRequest request);
    void deleteById(Long bookingId);

    BookingResponse confirmBooking(Long bookingId);
    BookingResponse cancelBooking(Long bookingId);

}
