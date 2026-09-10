package com.svich.EventBookingSystem.controller.customer;

import com.svich.EventBookingSystem.dto.customer.request.CreateCustomerRequest;
import com.svich.EventBookingSystem.dto.customer.request.UpdateCustomerRequest;
import com.svich.EventBookingSystem.dto.customer.response.CustomerResponse;
import com.svich.EventBookingSystem.pagination.PageableFactory;
import com.svich.EventBookingSystem.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final PageableFactory factory;

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id",
            "firstName",
            "lastName",
            "email",
            "createdAt"
    );

    @PostMapping("")
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CreateCustomerRequest request){
        CustomerResponse response = customerService.create(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long customerId){
        CustomerResponse response = customerService.findById(customerId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<CustomerResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){
        Pageable pageable = factory.create(page, size, sortBy, direction, ALLOWED_SORT_FIELDS);

        Page<CustomerResponse> responses = customerService.findAll(pageable);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateById(@PathVariable Long customerId, @Valid @RequestBody UpdateCustomerRequest request){
        CustomerResponse response = customerService.updateById(customerId, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long customerId){
        customerService.deleteById(customerId);
    }
}
