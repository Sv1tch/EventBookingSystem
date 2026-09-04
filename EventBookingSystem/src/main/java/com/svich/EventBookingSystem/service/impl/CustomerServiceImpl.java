package com.svich.EventBookingSystem.service.impl;

import com.svich.EventBookingSystem.dto.customer.request.CreateCustomerRequest;
import com.svich.EventBookingSystem.dto.customer.request.UpdateCustomerRequest;
import com.svich.EventBookingSystem.dto.customer.response.CustomerResponse;
import com.svich.EventBookingSystem.entity.customer.Customer;
import com.svich.EventBookingSystem.exception.customer.CustomerAlreadyExistsException;
import com.svich.EventBookingSystem.exception.customer.CustomerNotFoundException;
import com.svich.EventBookingSystem.mapper.customer.CustomerMapper;
import com.svich.EventBookingSystem.repository.customer.CustomerRepository;
import com.svich.EventBookingSystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse create(CreateCustomerRequest request){
        if(customerRepository.existsByEmail(request.getEmail())){
            throw new CustomerAlreadyExistsException("Customer with email " + request.getEmail() + " already exists");
        }

        Customer customer = customerMapper.toEntity(request);

        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    public CustomerResponse findById(Long customerId){
        return customerMapper.toResponse(findCustomerById(customerId));
    }

    @Override
    public List<CustomerResponse> findAll(){
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(customerMapper::toResponse).toList();
    }

    @Override
    public CustomerResponse updateById(Long customerId, UpdateCustomerRequest request){
        Customer customer = findCustomerById(customerId);

        if(!customer.getEmail().equals(request.getEmail()) && customerRepository.existsByEmail(request.getEmail())){
            throw new CustomerAlreadyExistsException("Customer with email " + request.getEmail() + " already exists");
        }

        customerMapper.updateEntity(request, customer);
        customer.setUpdatedAt(LocalDateTime.now());

        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    public void deleteById(Long customerId){
        Customer customer = findCustomerById(customerId);

        customerRepository.delete(customer);
    }

    private Customer findCustomerById(Long customerId){
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer with id " + customerId + " not found"));
    }
}
