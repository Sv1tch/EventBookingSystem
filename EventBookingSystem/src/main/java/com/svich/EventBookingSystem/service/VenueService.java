package com.svich.EventBookingSystem.service;

import com.svich.EventBookingSystem.dto.venue.request.CreateVenueRequest;
import com.svich.EventBookingSystem.dto.venue.request.UpdateVenueRequest;
import com.svich.EventBookingSystem.dto.venue.response.VenueResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VenueService {
    VenueResponse create(CreateVenueRequest request);
    Page<VenueResponse> findAll(Pageable pageable);
    VenueResponse findById(Long venueId);
    VenueResponse updateById(Long venueId, UpdateVenueRequest request);
    void deleteById(Long venueId);
}
