package com.svich.EventBookingSystem.repository.review;

import com.svich.EventBookingSystem.entity.review.Review;
import org.springframework.data.jpa.domain.Specification;

public class ReviewSpecification {

    public static Specification<Review> hasRating(Integer rating){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("rating"),
                        rating
                );
    }

    public static Specification<Review> hasCustomerId(Long customerId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("customer").get("id"),
                        customerId
                );
    }

    public static Specification<Review> hasEventId(Long eventId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("event").get("id"),
                        eventId
                );
    }
}
