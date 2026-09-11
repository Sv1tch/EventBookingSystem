package com.svich.EventBookingSystem.service.impl;

import com.svich.EventBookingSystem.dto.venue.request.CreateVenueRequest;
import com.svich.EventBookingSystem.dto.venue.request.UpdateVenueRequest;
import com.svich.EventBookingSystem.dto.venue.response.VenueResponse;
import com.svich.EventBookingSystem.entity.venue.Venue;
import com.svich.EventBookingSystem.exception.venue.VenueAlreadyExistsException;
import com.svich.EventBookingSystem.exception.venue.VenueNotFoundException;
import com.svich.EventBookingSystem.mapper.venue.VenueMapper;
import com.svich.EventBookingSystem.repository.venue.VenueRepository;
import com.svich.EventBookingSystem.repository.venue.VenueSpecification;
import com.svich.EventBookingSystem.service.VenueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueMapper venueMapper;
    private final VenueRepository venueRepository;

    @Override
    public VenueResponse create(CreateVenueRequest request){

        log.info("Creating venue: name={}, city={}",
                request.getName(),
                request.getCity()
        );

        if(venueRepository.existsByNameAndCityAndAddress(request.getName(), request.getCity(), request.getAddress())){
            throw new VenueAlreadyExistsException("Venue with name " + request.getName() + ", city " + request.getCity() + ", address " + request.getAddress() + " already exists");
        }

        Venue venue = venueMapper.toEntity(request);

        Venue savedVenue = venueRepository.save(venue);

        log.info("Created venue: venueId={}, name={}, city={}",
                savedVenue.getId(),
                savedVenue.getName(),
                savedVenue.getCity()
        );

        return venueMapper.toResponse(savedVenue);
    }

    @Override
    public VenueResponse findById(Long venueId){
        return venueMapper.toResponse(findVenueById(venueId));
    }

    @Override
    public VenueResponse updateById(Long venueId, UpdateVenueRequest request){

        log.info("Updating venue: venueId={}, name={}, city={}",
                venueId,
                request.getName(),
                request.getCity()
        );

        Venue venue = findVenueById(venueId);

        if(uniqueDataChanged(venue, request) && venueRepository.existsByNameAndCityAndAddress(
                request.getName(),
                request.getCity(),
                request.getAddress()
                )){
            throw new VenueAlreadyExistsException("Venue with name " + request.getName() + ", city " + request.getCity() + ", address " + request.getAddress() + " already exists");
        }

        venueMapper.updateEntity(request, venue);

        Venue savedVenue = venueRepository.save(venue);

        log.info("Updated venue: venueId={}, name={}, city={}",
                savedVenue.getId(),
                savedVenue.getName(),
                savedVenue.getCity()
        );

        return venueMapper.toResponse(savedVenue);
    }

    @Override
    public void deleteById(Long venueId){
        Venue venue = findVenueById(venueId);

        log.info("Delete venue: venueId={}, name={}, city={}",
                venue.getId(),
                venue.getName(),
                venue.getCity()
        );

        venueRepository.delete(venue);

        log.info("Deleted venue: venueId={}, name={}, city={}",
                venue.getId(),
                venue.getName(),
                venue.getCity()
        );
    }

    private Venue findVenueById(Long venueId){
        return venueRepository.findById(venueId).orElseThrow(() -> new VenueNotFoundException("Venue with id %d not found".formatted(venueId)));
    }

    private boolean uniqueDataChanged(Venue venue, UpdateVenueRequest request){
        return !venue.getName().equals(request.getName())
                || !venue.getCity().equals(request.getCity())
                || !venue.getAddress().equals(request.getAddress());
    }

    // SEARCH FUTURES
    @Override
    public Page<VenueResponse> findVenues(
            String name,
            String city,
            Pageable pageable
    ){
        Specification<Venue> spec = (root, query, criteriaBuilder) -> null;

        if(name != null && !name.isBlank()){
            spec = spec.and(VenueSpecification.hasName(name));
        }
        if(city != null && !city.isBlank()){
            spec = spec.and(VenueSpecification.hasCity(city));
        }

        Page<Venue> venues = venueRepository.findAll(spec, pageable);

        return venues.map(venueMapper::toResponse);
    }
}
