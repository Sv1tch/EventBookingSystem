package com.svich.EventBookingSystem.controller.venue;

import com.svich.EventBookingSystem.dto.venue.request.CreateVenueRequest;
import com.svich.EventBookingSystem.dto.venue.request.UpdateVenueRequest;
import com.svich.EventBookingSystem.dto.venue.response.VenueResponse;
import com.svich.EventBookingSystem.pagination.PageableFactory;
import com.svich.EventBookingSystem.service.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;
    private final PageableFactory factory;

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id",
            "name",
            "city",
            "capacity"
    );

    @PostMapping("")
    public ResponseEntity<VenueResponse> create(@Valid @RequestBody CreateVenueRequest request){
        VenueResponse response = venueService.create(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<Page<VenueResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){
        Pageable pageable = factory.create(page, size, sortBy, direction, ALLOWED_SORT_FIELDS);

        Page<VenueResponse> venues = venueService.findAll(pageable);

        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    @GetMapping("/{venueId}")
    public ResponseEntity<VenueResponse> findById(@PathVariable Long venueId){
        VenueResponse venue = venueService.findById(venueId);

        return new ResponseEntity<>(venue, HttpStatus.OK);
    }

    @PutMapping("/{venueId}")
    public ResponseEntity<VenueResponse> updateById(@PathVariable Long venueId, @Valid @RequestBody UpdateVenueRequest venueRequest){
        VenueResponse venue = venueService.updateById(venueId, venueRequest);

        return new ResponseEntity<>(venue, HttpStatus.OK);
    }

    @DeleteMapping("/{venueId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long venueId){
        venueService.deleteById(venueId);
    }
}
