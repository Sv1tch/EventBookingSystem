package com.svich.EventBookingSystem.mapper.venue;

import com.svich.EventBookingSystem.dto.venue.request.CreateVenueRequest;
import com.svich.EventBookingSystem.dto.venue.request.UpdateVenueRequest;
import com.svich.EventBookingSystem.dto.venue.response.VenueResponse;
import com.svich.EventBookingSystem.dto.venue.response.VenueSummaryResponse;
import com.svich.EventBookingSystem.entity.venue.Venue;
import org.springframework.stereotype.Component;

@Component
public class VenueMapper {

    public Venue toEntity(CreateVenueRequest request){
        Venue venue = new Venue();

        venue.setName(request.getName());
        venue.setCity(request.getCity());
        venue.setAddress(request.getAddress());
        venue.setCapacity(request.getCapacity());

        return venue;
    }

    public VenueResponse toResponse(Venue venue){
        VenueResponse response = new VenueResponse();

        response.setId(venue.getId());
        response.setName(venue.getName());
        response.setCity(venue.getCity());
        response.setAddress(venue.getAddress());
        response.setCapacity(venue.getCapacity());

        return response;
    }

    public VenueSummaryResponse toSummaryResponse(Venue venue){
        VenueSummaryResponse response = new VenueSummaryResponse();

        response.setId(venue.getId());
        response.setName(venue.getName());
        response.setCity(venue.getCity());

        return response;
    }

    public void updateEntity(UpdateVenueRequest request, Venue venue){

        venue.setName(request.getName());
        venue.setCity(request.getCity());
        venue.setAddress(request.getAddress());
        venue.setCapacity(request.getCapacity());
    }
}
