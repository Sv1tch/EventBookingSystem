package com.svich.EventBookingSystem.dto.event.validation;

import com.svich.EventBookingSystem.dto.event.request.EventDateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EventDateValidator implements ConstraintValidator<ValidEventDates, EventDateRequest> {

    @Override
    public boolean isValid(
            EventDateRequest request,
            ConstraintValidatorContext context
    ){
        if(request == null){
            return true;
        }

        if(request.getStartDateTime() == null || request.getEndDateTime() == null){
            return true;
        }

        return request.getEndDateTime().isAfter(request.getStartDateTime());
    }
}
