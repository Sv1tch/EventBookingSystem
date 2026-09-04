package com.svich.EventBookingSystem.service.event;

import com.svich.EventBookingSystem.dto.event.request.CreateEventRequest;
import com.svich.EventBookingSystem.dto.event.response.EventResponse;
import com.svich.EventBookingSystem.entity.category.Category;
import com.svich.EventBookingSystem.entity.venue.Venue;
import com.svich.EventBookingSystem.mapper.category.CategoryMapper;
import com.svich.EventBookingSystem.mapper.event.EventMapper;
import com.svich.EventBookingSystem.mapper.venue.VenueMapper;
import com.svich.EventBookingSystem.repository.category.CategoryRepository;
import com.svich.EventBookingSystem.repository.event.EventRepository;
import com.svich.EventBookingSystem.repository.venue.VenueRepository;
import com.svich.EventBookingSystem.service.impl.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest {
    @Mock
    private EventMapper eventMapper;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private VenueMapper venueMapper;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private EventServiceImpl eventServiceImpl;

    @Test
    void shouldCreateEventWithDraftStatus(){
        // Arrange
        CreateEventRequest request = new CreateEventRequest();

        request.setTitle("Atlas Weekend");
        request.setDescription("desc");
        request.setStartDateTime(LocalDateTime.of(2026, 9, 20, 10, 0, 0));
        request.setEndDateTime(LocalDateTime.of(2026, 9, 20, 13, 0, 0));
        request.setPrice(new BigDecimal("400.00"));
        request.setCapacity(5000);
        request.setCategoryId(1L);
        request.setVenueId(2L);

        Category category = new Category();
        category.setId(1L);

        Venue venue = new Venue();
        venue.setId(2L);

        // Act
        EventResponse event = eventServiceImpl.create(request);

        // Assert

        assertThat(event.getTitle()).isEqualTo("Atlas Weekend");
        assertThat(event.getDescription()).isEqualTo("desc");
        assertThat(event.getId()).isEqualTo(1L);
        assertThat(event.getStartDateTime()).isEqualTo(LocalDateTime.of(2026, 9, 20, 10, 0, 0));
        assertThat(event.getEndDateTime()).isEqualTo(LocalDateTime.of(2026, 9, 20, 13, 0, 0));
        assertThat(event.getPrice()).isEqualTo(new BigDecimal("400.00"));
        assertThat(event.getCapacity()).isEqualTo(5000);
        assertThat(event.getCategory().getId()).isEqualTo(1L);
        assertThat(event.getVenue().getId()).isEqualTo(2L);
    }
}
