package com.svich.EventBookingSystem.dto.customer.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCustomerRequest {

    @NotBlank(message = "First name can't be blank")
    @Size(
            min = 2,
            max = 100,
            message = "Customer first name must be between 2 and 100 characters"
    )
    private String firstName;

    @NotBlank(message = "Last name can't be blank")
    @Size(
            min = 2,
            max = 100,
            message = "Customer last name must be between 2 and 100 characters"
    )
    private String lastName;

    @NotBlank(message = "Email can't be blank")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Phone number can't be blank")
    private String phoneNumber;

}
