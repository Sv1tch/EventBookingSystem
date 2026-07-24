package com.svich.EventBookingSystem.service.impl;

import com.svich.EventBookingSystem.dto.venue.request.CreateVenueRequest;
import com.svich.EventBookingSystem.dto.venue.response.VenueResponse;
import com.svich.EventBookingSystem.mapper.venue.VenueMapper;
import com.svich.EventBookingSystem.repository.venue.VenueRepository;
import com.svich.EventBookingSystem.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueMapper venueMapper;
    private final VenueRepository venueRepository;

    @Override
    public VenueResponse create(CreateVenueRequest request){
        return null;
    }

    @Override
    public List<VenueResponse> findAll(){
        return null;
    }

    @Override
    public VenueResponse findById(Long venueId){
        return null;
    }
}
