package com.svich.EventBookingSystem.service.impl;

import com.svich.EventBookingSystem.dto.venue.request.CreateVenueRequest;
import com.svich.EventBookingSystem.dto.venue.request.UpdateVenueRequest;
import com.svich.EventBookingSystem.dto.venue.response.VenueResponse;
import com.svich.EventBookingSystem.entity.venue.Venue;
import com.svich.EventBookingSystem.exception.venue.VenueAlreadyExistsException;
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
        if(venueRepository.existsByNameAndCityAndAddress(request.getName(), request.getCity(), request.getAddress())){
            throw new VenueAlreadyExistsException("Venue with name " + request.getName() + ", city " + request.getCity() + ", address " + request.getAddress() + " already exists");
        }

        Venue venue = venueMapper.toEntity(request);

        Venue savedVenue = venueRepository.save(venue);

        return venueMapper.toResponse(savedVenue);
    }

    @Override
    public List<VenueResponse> findAll(){
        List<Venue> venues = venueRepository.findAll();

        return venues.stream().map(venueMapper::toResponse).toList();
    }

    @Override
    public VenueResponse findById(Long venueId){
        return null;
    }

    @Override
    public VenueResponse updateById(Long venueId, UpdateVenueRequest request){
        return null;
    }

    @Override
    public void deleteById(Long venueId){
    }
}
