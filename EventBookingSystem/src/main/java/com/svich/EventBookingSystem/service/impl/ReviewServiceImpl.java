package com.svich.EventBookingSystem.service.impl;

import com.svich.EventBookingSystem.dto.review.request.CreateReviewRequest;
import com.svich.EventBookingSystem.dto.review.request.UpdateReviewRequest;
import com.svich.EventBookingSystem.dto.review.response.ReviewResponse;
import com.svich.EventBookingSystem.entity.customer.Customer;
import com.svich.EventBookingSystem.entity.event.Event;
import com.svich.EventBookingSystem.entity.review.Review;
import com.svich.EventBookingSystem.exception.customer.CustomerNotFoundException;
import com.svich.EventBookingSystem.exception.event.EventNotFoundException;
import com.svich.EventBookingSystem.exception.review.CustomerHasNoConfirmedBookingException;
import com.svich.EventBookingSystem.exception.review.ReviewAlreadyExistsException;
import com.svich.EventBookingSystem.exception.review.ReviewNotFoundException;
import com.svich.EventBookingSystem.mapper.customer.CustomerMapper;
import com.svich.EventBookingSystem.mapper.event.EventMapper;
import com.svich.EventBookingSystem.mapper.review.ReviewMapper;
import com.svich.EventBookingSystem.repository.booking.BookingRepository;
import com.svich.EventBookingSystem.repository.customer.CustomerRepository;
import com.svich.EventBookingSystem.repository.event.EventRepository;
import com.svich.EventBookingSystem.repository.review.ReviewRepository;
import com.svich.EventBookingSystem.repository.review.ReviewSpecification;
import com.svich.EventBookingSystem.service.ReviewService;
import com.svich.EventBookingSystem.staticData.BookingStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final CustomerMapper customerMapper;
    private final EventMapper eventMapper;

    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public ReviewResponse create(CreateReviewRequest request){
        log.info("Create review: customerId={}, eventId={}",
                request.getCustomerId(),
                request.getEventId()
        );

        Customer customer = findCustomerById(request.getCustomerId());
        Event event = findEventById(request.getEventId());

        if(reviewRepository.existsByCustomerIdAndEventId(customer.getId(), event.getId())){
            throw new ReviewAlreadyExistsException("Customer with id " + customer.getId() + " already left review on event with id " + event.getId());
        }
        if(!bookingRepository.existsByCustomerIdAndEventIdAndStatus(customer.getId(), event.getId(), BookingStatus.CONFIRMED)){
            throw new CustomerHasNoConfirmedBookingException("Customer with id " + customer.getId() + " does not have a confirmed booking for event with id " + event.getId());
        }


        Review review = reviewMapper.toEntity(request, customer, event);
        LocalDateTime currentTime = LocalDateTime.now();
        review.setCreatedAt(currentTime);
        review.setUpdatedAt(currentTime);

        Review savedReview = reviewRepository.save(review);

        log.info("Created review: reviewId={}",
                savedReview.getId()
        );

        return reviewMapper.toResponse(savedReview, customerMapper.toSummaryResponse(savedReview.getCustomer()), eventMapper.toSummaryResponse(savedReview.getEvent()));
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponse findById(Long reviewId){
        Review review = findReviewById(reviewId);

        return reviewMapper.toResponse(
                review,
                customerMapper.toSummaryResponse(review.getCustomer()),
                eventMapper.toSummaryResponse(review.getEvent()));
    }

    @Override
    @Transactional
    public ReviewResponse updateById(Long reviewId, UpdateReviewRequest request){

        log.info("Update review: reviewId={}",
                reviewId
        );

        Review review = findReviewById(reviewId);

        reviewMapper.updateEntity(request, review);
        review.setUpdatedAt(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);

        log.info("Updated review: reviewId={}",
                savedReview.getId()
        );

        return reviewMapper.toResponse(
                savedReview,
                customerMapper.toSummaryResponse(savedReview.getCustomer()),
                eventMapper.toSummaryResponse(savedReview.getEvent())
        );
    }

    @Override
    @Transactional
    public void deleteById(Long reviewId){
        Review review = findReviewById(reviewId);

        log.info("Delete review: reviewId={}", review.getId());

        reviewRepository.delete(review);

        log.info("Deleted review: reviewId={}", review.getId());
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

    //SEARCH FUTURES
    @Override
    @Transactional(readOnly = true)
    public Page<ReviewResponse> findReviews(
            Integer rating,
            Long customerId,
            Long eventId,
            Pageable pageable
    ){
        Specification<Review> spec = (root, query, criteriaBuilder) -> null;

        if(rating != null && rating >= 1 && rating <= 5){
            spec = spec.and(ReviewSpecification.hasRating(rating));
        }
        if(customerId != null){
            spec = spec.and(ReviewSpecification.hasCustomerId(customerId));
        }
        if(eventId != null){
            spec = spec.and(ReviewSpecification.hasEventId(eventId));
        }

        Page<Review> reviews = reviewRepository.findAll(spec, pageable);

        return reviews.map(review ->
                reviewMapper.toResponse(
                        review,
                        customerMapper.toSummaryResponse(review.getCustomer()),
                        eventMapper.toSummaryResponse(review.getEvent())
                )
        );
    }
}
