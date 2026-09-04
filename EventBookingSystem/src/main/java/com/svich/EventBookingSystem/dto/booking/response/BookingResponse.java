package com.svich.EventBookingSystem.dto.booking.response;

import com.svich.EventBookingSystem.dto.customer.response.CustomerSummaryResponse;
import com.svich.EventBookingSystem.dto.event.response.EventSummaryResponse;
import com.svich.EventBookingSystem.staticData.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingResponse {

    private Long id;

    private EventSummaryResponse event;

    private CustomerSummaryResponse customer;

    private Integer quantity;

    private BigDecimal totalPrice;

    private BookingStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
