package com.svich.EventBookingSystem.service;

import com.svich.EventBookingSystem.dto.customer.request.CreateCustomerRequest;
import com.svich.EventBookingSystem.dto.customer.request.UpdateCustomerRequest;
import com.svich.EventBookingSystem.dto.customer.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    CustomerResponse create(CreateCustomerRequest request);
    CustomerResponse findById(Long customerId);
    CustomerResponse updateById(Long customerId, UpdateCustomerRequest request);
    void deleteById(Long customerId);

    Page<CustomerResponse> findCustomers(
            String firstName,
            String lastName,
            String email,
            Pageable pageable
    );
}
