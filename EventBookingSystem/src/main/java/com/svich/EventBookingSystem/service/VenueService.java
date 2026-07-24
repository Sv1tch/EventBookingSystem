package com.svich.EventBookingSystem.service;

import com.svich.EventBookingSystem.dto.venue.request.CreateVenueRequest;
import com.svich.EventBookingSystem.dto.venue.response.VenueResponse;

import java.util.List;

public interface VenueService {
    VenueResponse create(CreateVenueRequest request);
    List<VenueResponse> findAll();
    VenueResponse findById(Long venueId);
}
