package com.svich.EventBookingSystem.service.impl;

import com.svich.EventBookingSystem.dto.booking.request.CreateBookingRequest;
import com.svich.EventBookingSystem.dto.booking.request.UpdateBookingRequest;
import com.svich.EventBookingSystem.dto.booking.response.BookingResponse;
import com.svich.EventBookingSystem.entity.booking.Booking;
import com.svich.EventBookingSystem.entity.customer.Customer;
import com.svich.EventBookingSystem.entity.event.Event;
import com.svich.EventBookingSystem.exception.booking.BookingNotFoundException;
import com.svich.EventBookingSystem.exception.booking.InvalidBookingStatusException;
import com.svich.EventBookingSystem.exception.booking.InvalidBookingStatusTransitionException;
import com.svich.EventBookingSystem.exception.booking.NotEnoughSeatsException;
import com.svich.EventBookingSystem.exception.customer.CustomerNotFoundException;
import com.svich.EventBookingSystem.exception.event.InvalidEventStatusException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
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
    @Transactional
    public BookingResponse create(CreateBookingRequest request){
        log.info("Create booking: eventId={}, customerId={}, quantity={}",
                request.getEventId(),
                request.getCustomerId(),
                request.getQuantity()
        );

        Event event = findEventWithLockById(request.getEventId());
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

        log.info("Created booking: bookingId={}, eventId={}, quantity={}, totalPrice={}",
                savedBooking.getId(),
                savedBooking.getEvent().getId(),
                savedBooking.getQuantity(),
                savedBooking.getTotalPrice()
        );

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
    public Page<BookingResponse> findAll(Pageable pageable){
        Page<Booking> bookings = bookingRepository.findAll(pageable);

        return bookings.map(booking ->
                bookingMapper.toResponse(booking, eventMapper.toSummaryResponse(booking.getEvent()), customerMapper.toSummaryResponse(booking.getCustomer()))
        );
    }

    @Override
    @Transactional
    public BookingResponse updateById(Long bookingId, UpdateBookingRequest request){
        Booking booking = findBookingById(bookingId);

        log.info("Update booking: bookingId={}, prevQuantity={}, newQuantity={}",
                bookingId,
                booking.getQuantity(),
                request.getQuantity()
        );

        Event event = findEventWithLockById(booking.getEvent().getId());

        canUpdate(event.getStatus(), booking.getStatus());


        Long bookedQuantity = bookingRepository.getBookedQuantity(
                event.getId(),
                List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
        );

        if(bookedQuantity - booking.getQuantity() + request.getQuantity() > event.getCapacity()){
            throw new NotEnoughSeatsException("Not enough free seats left");
        }

        bookingMapper.updateEntity(request, booking);
        booking.setTotalPrice(event.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        booking.setUpdatedAt(LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);

        log.info("Updated booking: bookingId={}, newQuantity={}, newTotalPrice={}",
                savedBooking.getId(),
                savedBooking.getQuantity(),
                savedBooking.getTotalPrice()
        );

        return bookingMapper.toResponse(
                savedBooking,
                eventMapper.toSummaryResponse(booking.getEvent()),
                customerMapper.toSummaryResponse(booking.getCustomer())
        );
    }

    @Override
    @Transactional
    public void deleteById(Long bookingId){
        Booking booking = findBookingById(bookingId);

        Event event = findEventWithLockById(booking.getEvent().getId());

        log.info("Delete booking: bookingId={}, eventId={}",
                booking.getId(),
                booking.getEvent().getId()
        );

        bookingRepository.delete(booking);

        log.info("Deleted booking: bookingId={}, eventId={}",
                booking.getId(),
                booking.getEvent().getId()
        );
    }

    private Booking findBookingById(Long bookingId){
        return bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new BookingNotFoundException(
                                "Booking with id " + bookingId + " not found"
                        )
                );
    }

    private Event findEventWithLockById(Long eventId){
        return eventRepository.findWithLockById(eventId)
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

    private void canUpdate(EventStatus eventStatus, BookingStatus bookingStatus){
        if(eventStatus != EventStatus.PUBLISHED){
            throw new InvalidEventStatusException("Booking of the event with status " + eventStatus + " can't be completed");
        }

        if(bookingStatus == BookingStatus.CANCELLED){
            throw new InvalidBookingStatusException("Booking with status " + bookingStatus + " can't be updated");
        }
    }

    // STATUS CHANGING
    private BookingResponse changeStatus(Booking booking, BookingStatus targetStatus){
        BookingStatus currentStatus = booking.getStatus();
        boolean canTransit = false;

        if(targetStatus.equals(BookingStatus.CONFIRMED)){
            canTransit = (currentStatus.equals(BookingStatus.PENDING));
        }
        else if(targetStatus.equals(BookingStatus.CANCELLED)){
            canTransit = (currentStatus.equals(BookingStatus.PENDING) || currentStatus.equals(BookingStatus.CONFIRMED));
        }

        if(canTransit){
            booking.setStatus(targetStatus);
            booking.setUpdatedAt(LocalDateTime.now());

            Booking savedBooking = bookingRepository.save(booking);

            log.info("Booking status changed: bookingId={}, previousStatus={}, newStatus={}",
                    booking.getId(),
                    currentStatus,
                    targetStatus
            );

            return bookingMapper.toResponse(
                    savedBooking,
                    eventMapper.toSummaryResponse(booking.getEvent()),
                    customerMapper.toSummaryResponse(booking.getCustomer())
            );
        }

        throw new InvalidBookingStatusTransitionException("Booking with id " + booking.getId() + " cannot be changed from " + currentStatus + " to " + targetStatus);
    }

    @Override
    @Transactional
    public BookingResponse confirmBooking(Long bookingId){
        Booking booking = findBookingById(bookingId);

        return changeStatus(booking, BookingStatus.CONFIRMED);
    }

    @Override
    @Transactional
    public BookingResponse cancelBooking(Long bookingId){
        Booking booking = findBookingById(bookingId);

        Event event = findEventWithLockById(booking.getEvent().getId());

        return changeStatus(booking, BookingStatus.CANCELLED);
    }
}
