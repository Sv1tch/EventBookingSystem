package com.svich.EventBookingSystem.dto.event.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EventDateValidator.class)
public @interface ValidEventDates {

    String message() default "End date and time must be after start date and start time";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
