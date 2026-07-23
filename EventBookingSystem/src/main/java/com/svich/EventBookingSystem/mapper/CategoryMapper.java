package com.svich.EventBookingSystem.mapper;


import com.svich.EventBookingSystem.dto.CategoryResponse;
import com.svich.EventBookingSystem.dto.CreateCategoryRequest;
import com.svich.EventBookingSystem.dto.UpdateCategoryRequest;
import com.svich.EventBookingSystem.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CreateCategoryRequest request){
        Category category = new Category();

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return category;
    }

    public CategoryResponse toResponse(Category category){
        CategoryResponse response = new CategoryResponse();

        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());

        return response;
    }

    public void updateEntity(UpdateCategoryRequest request, Category category){
        category.setName(request.getName());
        category.setDescription(request.getDescription());
    }
}
