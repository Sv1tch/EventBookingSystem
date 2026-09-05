package com.svich.EventBookingSystem.mapper.booking;

import com.svich.EventBookingSystem.dto.booking.request.CreateBookingRequest;
import com.svich.EventBookingSystem.dto.booking.request.UpdateBookingRequest;
import com.svich.EventBookingSystem.dto.booking.response.BookingResponse;
import com.svich.EventBookingSystem.dto.customer.response.CustomerSummaryResponse;
import com.svich.EventBookingSystem.dto.event.response.EventSummaryResponse;
import com.svich.EventBookingSystem.entity.booking.Booking;
import com.svich.EventBookingSystem.entity.customer.Customer;
import com.svich.EventBookingSystem.entity.event.Event;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public Booking toEntity(
            CreateBookingRequest request,
            Event event,
            Customer customer
    ){
        Booking booking = new Booking();

        booking.setEvent(event);
        booking.setCustomer(customer);
        booking.setQuantity(request.getQuantity());

        return booking;
    }

    public BookingResponse toResponse(
            Booking booking,
            EventSummaryResponse event,
            CustomerSummaryResponse customer
    ){

        BookingResponse response = new BookingResponse();

        response.setId(booking.getId());
        response.setEvent(event);
        response.setCustomer(customer);
        response.setQuantity(booking.getQuantity());
        response.setTotalPrice(booking.getTotalPrice());
        response.setStatus(booking.getStatus());
        response.setCreatedAt(booking.getCreatedAt());
        response.setUpdatedAt(booking.getUpdatedAt());

        return response;
    }

    public void updateEntity(
            UpdateBookingRequest request,
            Booking booking
    ){
        booking.setQuantity(request.getQuantity());
    }
}
