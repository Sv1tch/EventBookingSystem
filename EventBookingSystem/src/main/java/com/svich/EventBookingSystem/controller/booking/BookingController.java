package com.svich.EventBookingSystem.controller.booking;

import com.svich.EventBookingSystem.dto.booking.request.CreateBookingRequest;
import com.svich.EventBookingSystem.dto.booking.request.UpdateBookingRequest;
import com.svich.EventBookingSystem.dto.booking.response.BookingResponse;
import com.svich.EventBookingSystem.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("")
    public ResponseEntity<BookingResponse> create(@Valid @RequestBody CreateBookingRequest request){
        BookingResponse response = bookingService.create(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> findById(@PathVariable Long bookingId){
        BookingResponse response = bookingService.findById(bookingId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<BookingResponse>> findAll(){
        List<BookingResponse> responses = bookingService.findAll();

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> updateById(@PathVariable Long bookingId, @Valid @RequestBody UpdateBookingRequest request){
        BookingResponse response = bookingService.updateById(bookingId, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long bookingId){
        bookingService.deleteById(bookingId);
    }

    @PatchMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingResponse> confirmBooking(@PathVariable Long bookingId){
        BookingResponse response = bookingService.confirmBooking(bookingId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long bookingId){
        BookingResponse response = bookingService.cancelBooking(bookingId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
