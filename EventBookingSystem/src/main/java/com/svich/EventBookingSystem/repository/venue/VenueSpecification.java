package com.svich.EventBookingSystem.repository.venue;

import com.svich.EventBookingSystem.entity.venue.Venue;
import org.springframework.data.jpa.domain.Specification;

public class VenueSpecification {

    public static Specification<Venue> hasName(String name){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );
    }

    public static Specification<Venue> hasCity(String city){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("city")),
                        "%" + city.toLowerCase() + "%"
                );
    }
}
