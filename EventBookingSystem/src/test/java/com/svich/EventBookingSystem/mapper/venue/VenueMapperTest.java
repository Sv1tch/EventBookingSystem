package com.svich.EventBookingSystem.mapper.venue;

import com.svich.EventBookingSystem.dto.venue.request.CreateVenueRequest;
import com.svich.EventBookingSystem.dto.venue.request.UpdateVenueRequest;
import com.svich.EventBookingSystem.dto.venue.response.VenueResponse;
import com.svich.EventBookingSystem.entity.venue.Venue;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class VenueMapperTest {

    private final VenueMapper venueMapper = new VenueMapper();

     // AAA Pattern
    @Test
    void shouldMapCreateRequestToEntity(){

        //Arrange
        CreateVenueRequest request = new CreateVenueRequest();

        request.setName("Arena Lviv");
        request.setCity("Lviv");
        request.setAddress("Stryiska 199");
        request.setCapacity(3500);

        //Act
        Venue venue = venueMapper.toEntity(request);

        //Assert
        assertThat(venue.getName()).isEqualTo("Arena Lviv");
        assertThat(venue.getCity()).isEqualTo("Lviv");
        assertThat(venue.getAddress()).isEqualTo("Stryiska 199");
        assertThat(venue.getCapacity()).isEqualTo(3500);
    }

    @Test
    void shouldMapEntityToResponse(){

        //Arrange
        Venue venue = new Venue();

        venue.setName("Arena Lviv");
        venue.setCity("Lviv");
        venue.setAddress("Stryiska 199");
        venue.setCapacity(3500);
        venue.setId(67L);

        //Act
        VenueResponse response = venueMapper.toResponse(venue);

        //Assert
        assertThat(response.getName()).isEqualTo("Arena Lviv");
        assertThat(response.getCity()).isEqualTo("Lviv");
        assertThat(response.getAddress()).isEqualTo("Stryiska 199");
        assertThat(response.getCapacity()).isEqualTo(3500);
        assertThat(response.getId()).isEqualTo(67L);
    }

    @Test
    void shouldUpdateEntity(){

        //Arrange
        Venue venue = new Venue();

        venue.setName("Arena Lviv");
        venue.setCity("Lviv");
        venue.setAddress("Stryiska 199");
        venue.setCapacity(3500);
        venue.setId(67L);

        UpdateVenueRequest request = new UpdateVenueRequest();

        request.setName("Olimpijskyi");
        request.setCity("Kyiv");
        request.setAddress("Palac Sportu");
        request.setCapacity(10000);

        //Act
        venueMapper.updateEntity(request, venue);

        //Assert
        assertThat(venue.getName()).isEqualTo("Olimpijskyi");
        assertThat(venue.getCity()).isEqualTo("Kyiv");
        assertThat(venue.getAddress()).isEqualTo("Palac Sportu");
        assertThat(venue.getCapacity()).isEqualTo(10000);
        assertThat(venue.getId()).isEqualTo(67L);
    }
}
