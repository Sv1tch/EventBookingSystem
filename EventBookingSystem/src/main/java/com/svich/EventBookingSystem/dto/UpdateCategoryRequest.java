package com.svich.EventBookingSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryRequest {

    @NotBlank(message = "Name can't be blank")
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;
}
