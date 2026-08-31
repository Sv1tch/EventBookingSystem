package com.svich.EventBookingSystem.pagination;

import com.svich.EventBookingSystem.exception.pagination.InvalidPaginationParameterException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PageableFactory {
    private static final Set<String> ALLOWED_SORT_FIELDS= Set.of(
            "title",
            "price",
            "startDateTime",
            "endDateTime",
            "capacity"
    );

    public Pageable create(int page, int size, String sortBy, String direction){

        //Validation
        if(page < 0){
            throw new InvalidPaginationParameterException("Page must be greater than or equal to 0");
        }

        if(size <= 0){
            throw new InvalidPaginationParameterException("Size must be greater than 0");
        }

        if(!direction.equalsIgnoreCase("desc") && !direction.equalsIgnoreCase("asc")){
            throw new InvalidPaginationParameterException("Direction must be either 'asc' or 'desc'");
        }

        if(!ALLOWED_SORT_FIELDS.contains(sortBy)){
            throw new InvalidPaginationParameterException("Invalid sort field: " + sortBy);
        }

        //Create sort

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        //Create pageable
        return PageRequest.of(page, size, sort);
    }
}
