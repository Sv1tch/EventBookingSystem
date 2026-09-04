package com.svich.EventBookingSystem.dto.customer.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerSummaryResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;
}
