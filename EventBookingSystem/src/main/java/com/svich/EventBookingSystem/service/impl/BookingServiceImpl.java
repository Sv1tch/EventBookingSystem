package com.svich.EventBookingSystem.service.impl;

import com.svich.EventBookingSystem.dto.booking.request.CreateBookingRequest;
import com.svich.EventBookingSystem.dto.booking.request.UpdateBookingRequest;
import com.svich.EventBookingSystem.dto.booking.response.BookingResponse;
import com.svich.EventBookingSystem.entity.booking.Booking;
import com.svich.EventBookingSystem.entity.customer.Customer;
import com.svich.EventBookingSystem.entity.event.Event;
import com.svich.EventBookingSystem.exception.booking.BookingNotFoundException;
import com.svich.EventBookingSystem.exception.booking.NotEnoughSeatsException;
import com.svich.EventBookingSystem.exception.customer.CustomerNotFoundException;
import com.svich.EventBookingSystem.exception.booking.InvalidEventStatusException;
import com.svich.EventBookingSystem.exception.event.EventNotFoundException;
import com.svich.EventBookingSystem.mapper.booking.BookingMapper;
import com.svich.EventBookingSystem.mapper.customer.CustomerMapper;
import com.svich.EventBookingSystem.mapper.event.EventMapper;
import com.svich.EventBookingSystem.repository.booking.BookingRepository;
import com.svich.EventBookingSystem.repository.customer.CustomerRepository;
import com.svich.EventBookingSystem.repository.event.EventRepository;
import com.svich.EventBookingSystem.service.BookingService;
import com.svich.EventBookingSystem.staticData.BookingStatus;
import com.svich.EventBookingSystem.staticData.EventStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingMapper bookingMapper;
    private final EventMapper eventMapper;
    private final CustomerMapper customerMapper;

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final CustomerRepository customerRepository;

    @Override
    public BookingResponse create(CreateBookingRequest request){
        Event event = findEventById(request.getEventId());
        Customer customer = findCustomerById(request.getCustomerId());

        if(event.getStatus() != EventStatus.PUBLISHED){
            throw new InvalidEventStatusException("Booking of the event with status " + event.getStatus() + " can't be completed");
        }

        Long bookedQuantity = bookingRepository.getBookedQuantity(
                event.getId(),
                List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
        );

        if(bookedQuantity + request.getQuantity() > event.getCapacity()){
            throw new NotEnoughSeatsException("Not enough free seats left");
        }

        Booking booking = bookingMapper.toEntity(request, event, customer);

        booking.setStatus(BookingStatus.PENDING);
        booking.setTotalPrice(event.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));

        LocalDateTime currentTime = LocalDateTime.now();
        booking.setCreatedAt(currentTime);
        booking.setUpdatedAt(currentTime);

        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapper.toResponse(
                savedBooking,
                eventMapper.toSummaryResponse(event),
                customerMapper.toSummaryResponse(customer)
        );
    }

    @Override
    public BookingResponse findById(Long bookingId){
        Booking booking = findBookingById(bookingId);

        return bookingMapper.toResponse(
                booking,
                eventMapper.toSummaryResponse(booking.getEvent()),
                customerMapper.toSummaryResponse(booking.getCustomer())
        );
    }

    @Override
    public List<BookingResponse> findAll(){
        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream().map(booking ->
                bookingMapper.toResponse(booking, eventMapper.toSummaryResponse(booking.getEvent()), customerMapper.toSummaryResponse(booking.getCustomer()))
        ).toList();
    }

    @Override
    public BookingResponse updateById(Long bookingId, UpdateBookingRequest request){
        Booking booking = findBookingById(bookingId);

        bookingMapper.updateEntity(request, booking);

        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapper.toResponse(
                savedBooking,
                eventMapper.toSummaryResponse(booking.getEvent()),
                customerMapper.toSummaryResponse(booking.getCustomer())
        );
    }

    @Override
    public void deleteById(Long bookingId){
        Booking booking = findBookingById(bookingId);

        bookingRepository.delete(booking);
    }

    private Booking findBookingById(Long bookingId){
        return bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new BookingNotFoundException(
                                "Booking with id " + bookingId + " not found"
                        )
                );
    }

    private Event findEventById(Long eventId){
        return eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new EventNotFoundException(
                                "Event with id " + eventId + " not found"
                        )
                );
    }

    private Customer findCustomerById(Long customerId){
        return customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer with id " + customerId + " not found"
                        )
                );
    }
}
