package com.svich.EventBookingSystem.service;

import com.svich.EventBookingSystem.dto.customer.request.CreateCustomerRequest;
import com.svich.EventBookingSystem.dto.customer.request.UpdateCustomerRequest;
import com.svich.EventBookingSystem.dto.customer.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse create(CreateCustomerRequest request);
    CustomerResponse findById(Long customerId);
    List<CustomerResponse> findAll();
    CustomerResponse updateById(Long customerId, UpdateCustomerRequest request);
    void deleteById(Long customerId);
}
