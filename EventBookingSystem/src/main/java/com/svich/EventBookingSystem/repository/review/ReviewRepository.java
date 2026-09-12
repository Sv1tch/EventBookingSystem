package com.svich.EventBookingSystem.repository.review;

import com.svich.EventBookingSystem.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByCustomerIdAndEventId(Long customerId, Long eventId);
}
