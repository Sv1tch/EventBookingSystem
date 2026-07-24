package com.svich.EventBookingSystem.repository.venue;

import com.svich.EventBookingSystem.entity.venue.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    boolean existsByNameAndCityAndAddress(String name, String city, String address);

    Optional<Venue> findByNameAndCityAndAddress(String name, String city, String address);
}
