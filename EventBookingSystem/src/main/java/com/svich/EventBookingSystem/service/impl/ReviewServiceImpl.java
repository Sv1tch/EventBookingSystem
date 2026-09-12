package com.svich.EventBookingSystem.service.impl;

import com.svich.EventBookingSystem.dto.review.request.CreateReviewRequest;
import com.svich.EventBookingSystem.dto.review.request.UpdateReviewRequest;
import com.svich.EventBookingSystem.dto.review.response.ReviewResponse;
import com.svich.EventBookingSystem.entity.customer.Customer;
import com.svich.EventBookingSystem.entity.event.Event;
import com.svich.EventBookingSystem.entity.review.Review;
import com.svich.EventBookingSystem.exception.customer.CustomerNotFoundException;
import com.svich.EventBookingSystem.exception.event.EventNotFoundException;
import com.svich.EventBookingSystem.exception.review.ReviewAlreadyExistsException;
import com.svich.EventBookingSystem.exception.review.ReviewNotFoundException;
import com.svich.EventBookingSystem.mapper.customer.CustomerMapper;
import com.svich.EventBookingSystem.mapper.event.EventMapper;
import com.svich.EventBookingSystem.mapper.review.ReviewMapper;
import com.svich.EventBookingSystem.repository.customer.CustomerRepository;
import com.svich.EventBookingSystem.repository.event.EventRepository;
import com.svich.EventBookingSystem.repository.review.ReviewRepository;
import com.svich.EventBookingSystem.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final CustomerMapper customerMapper;
    private final EventMapper eventMapper;

    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;

    @Override
    public ReviewResponse create(CreateReviewRequest request){
        Customer customer = findCustomerById(request.getCustomerId());
        Event event = findEventById(request.getEventId());

        if(reviewRepository.existsByCustomerIdAndEventId(customer.getId(), event.getId())){
            throw new ReviewAlreadyExistsException("Customer with id " + customer.getId() + " already left review on event with id " + event.getId());
        }

        Review review = reviewMapper.toEntity(request, customer, event);
        LocalDateTime currentTime = LocalDateTime.now();
        review.setCreatedAt(currentTime);
        review.setUpdatedAt(currentTime);

        Review savedReview = reviewRepository.save(review);

        return reviewMapper.toResponse(savedReview, customerMapper.toSummaryResponse(savedReview.getCustomer()), eventMapper.toSummaryResponse(savedReview.getEvent()));
    }

    @Override
    public ReviewResponse findById(Long reviewId){
        return null;
    }

    @Override
    public List<ReviewResponse> findAll(){
        return null;
    }

    @Override
    public ReviewResponse updateById(Long reviewId, UpdateReviewRequest request){
        return null;
    }

    @Override
    public void deleteById(Long reviewId){

    }

    private Customer findCustomerById(Long customerId){
        return customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer with id " + customerId + " not found"
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

    private Review findReviewById(Long reviewId){
        return reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ReviewNotFoundException(
                                "Review with id " + reviewId + " not found"
                        )
                );
    }
}
